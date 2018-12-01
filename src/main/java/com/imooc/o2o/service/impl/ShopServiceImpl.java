package com.imooc.o2o.service.impl;
/*
 * Created by wxn
 * 2018/8/21 11:28
 */


import com.imooc.o2o.dao.ShopAuthMapDao;
import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopAuthMap;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exception.ShopOperationException;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageCalculator;
import com.imooc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDao shopDao;
	@Autowired
	private ShopAuthMapDao shopAuthMapDao;

	@Override
	public Shop getByShopId(long shopId) {
		return shopDao.queryByShopId(shopId);
	}

	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {

		int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
		List<Shop> shopList = shopDao.queryShopList(shopCondition,rowIndex,pageSize);
		int count = shopDao.queryShopCount(shopCondition);
		ShopExecution se = new ShopExecution();
		if (shopList != null) {
			se.setShopList(shopList);
			se.setCount(count);
			se.setState(ShopStateEnum.SUCCESS.getState());
		}
		else {
			se.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return se;

	}

	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, ImageHolder shopImg) throws ShopOperationException {
		//空值判断
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			//给店铺信息赋初始值
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			//添加店铺信息
			int effectedNum = shopDao.insertShop(shop);
			if (effectedNum <= 0) {
				//ShopOperationException事务才会回滚
				throw new ShopOperationException("店铺创建失败");
			} else {
				if (shopImg.getImage() != null) {
					// 存储图片
					try {
						addShopImg(shop, shopImg);
					} catch (Exception e) {
						throw new ShopOperationException("addShopImg error :" + e.getMessage());
					}
					//更新店铺图片的地址
					effectedNum = shopDao.updateShop(shop);
					if (effectedNum <= 0) {
						throw new ShopOperationException("更新图片地址失败");
					}
					//设置店主的认证信息
					ShopAuthMap shopAuthMap = new ShopAuthMap();
					shopAuthMap.setTitle("老板");
					shopAuthMap.setTitleFlag(0);
					shopAuthMap.setEmployee(shop.getOwner());
					shopAuthMap.setShop(shop);
					shopAuthMap.setEnableStatus(1);
					shopAuthMap.setCreateTime(new Date());
					shopAuthMap.setLastEditTime(new Date());
					shopAuthMap.setName(shop.getShopName());
					shopAuthMapDao.insertShopAuthMap(shopAuthMap);
				}
			}
		} catch (Exception e) {
			throw new ShopOperationException("addShop error:" + e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK);
	}

	@Override
	public ShopExecution modifyShop(Shop shop, ImageHolder shopImg) throws ShopOperationException {

		if (shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		} else {
			try {
				//1 判断是否需要处理图片
				if (shopImg.getImage() != null && shopImg.getImageName() != null && !"".equals(shopImg.getImageName().trim())) {
					Shop tempShop = shopDao.queryByShopId(shop.getShopId());
					if (tempShop.getShopImg() != null) {
						ImageUtil.deleteFileOrPath(tempShop.getShopImg());
					}
					addShopImg(shop, shopImg);
				}
				//2 更新店铺信息
				shop.setLastEditTime(new Date());
				int effectedNum = shopDao.updateShop(shop);
				if (effectedNum<=0){
					return new ShopExecution(ShopStateEnum.INNER_ERROR);
				}else {
					return new ShopExecution(ShopStateEnum.SUCCESS,shop);
				}
			}catch (Exception e){
				throw new ShopOperationException("modify shop error! : "+ e.getMessage());
			}
		}
	}

	/**
	 * 添加店铺图片
	 */
	private void addShopImg(Shop shop, ImageHolder shopImg) {

		//获取shop图片目录的相对值路径
		String dest = PathUtil.getShopImagePath(shop.getShopId());

		String shopImgAddr = ImageUtil.generateThumbnail(shopImg, dest);
		shop.setShopImg(shopImgAddr);
	}
}
