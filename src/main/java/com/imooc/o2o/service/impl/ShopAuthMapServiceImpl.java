package com.imooc.o2o.service.impl;
/*
 * Created by wxn
 * 2018/11/29 17:17
 */


import com.imooc.o2o.dao.ShopAuthMapDao;
import com.imooc.o2o.dto.ShopAuthMapExecution;
import com.imooc.o2o.entity.ShopAuthMap;
import com.imooc.o2o.enums.ShopAuthMapStateEnum;
import com.imooc.o2o.exception.ShopAuthMapException;
import com.imooc.o2o.service.ShopAuthMapService;
import com.imooc.o2o.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ShopAuthMapServiceImpl implements ShopAuthMapService {

	@Autowired
	private ShopAuthMapDao shopAuthMapDao;

	@Override
	public ShopAuthMapExecution listShopAuthMapByShopId(Long shopId, Integer pageIndex, Integer pageSize) {

		if (shopId != null && pageIndex != null && pageSize != null) {
			int beginIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);

			List<ShopAuthMap> shopAuthMapList = shopAuthMapDao.queryShopAuthMapListByShopId(shopId, beginIndex, pageSize);
			int count = shopAuthMapDao.queryShopAuthCountByShopId(shopId);

			ShopAuthMapExecution se = new ShopAuthMapExecution();
			se.setShopAuthMapList(shopAuthMapList);
			se.setCount(count);
			return se;
		} else {
			return null;
		}
	}

	@Override
	public ShopAuthMap getShopAuthMapById(Long shopAuthId) {

		if (shopAuthId > 0) {
			return shopAuthMapDao.queryShopAuthMapById(shopAuthId);
		} else {
			return null;
		}
	}

	@Override
	public ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapException {

		if (shopAuthMap != null && shopAuthMap.getShop() != null && shopAuthMap.getShop().getShopId() != null
				&& shopAuthMap.getEmployee() != null && shopAuthMap.getEmployee().getUserId() != null) {
			shopAuthMap.setCreateTime(new Date());
			shopAuthMap.setLastEditTime(new Date());
			shopAuthMap.setEnableStatus(1);

			try {
				int effectNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
				if (effectNum<=0){
					throw new ShopAuthMapException("添加失败");
				}
				return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS,shopAuthMap);
			}catch (Exception e){
				throw new ShopAuthMapException("添加失败："+e.getMessage());
			}
		}else {
			throw new ShopAuthMapException(ShopAuthMapStateEnum.FAIL.getStateInfo());
		}

	}

	@Override
	public ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapException {

		if (shopAuthMap!=null && shopAuthMap.getShopAuthId()!=null){
			try {
				shopAuthMap.setLastEditTime(new Date());
				int effectNum = shopAuthMapDao.updateShopAuthMap(shopAuthMap);
				if (effectNum<=0){
					return new ShopAuthMapExecution(ShopAuthMapStateEnum.FAIL);
				}else {
					return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS,shopAuthMap);
				}
			}catch (Exception e){
				throw new ShopAuthMapException(ShopAuthMapStateEnum.FAIL.getStateInfo()+e.getMessage());
			}
		}else {
			throw new ShopAuthMapException(ShopAuthMapStateEnum.FAIL.getStateInfo());
		}
	}
}
