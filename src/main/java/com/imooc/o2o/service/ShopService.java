package com.imooc.o2o.service;
/*
 * Created by wxn
 * 2018/8/21 11:26
 */


import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.exception.ShopOperationException;

public interface ShopService {

	/**
	 * 通过店铺Id获取店铺信息
	 */
	Shop getByShopId(long shopId);

	/**
	 * 查询店铺列表
	 * 分页查询
	 */
	ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);

	/**
	 * 店铺注册 包括对图片的处理
	 */
	ShopExecution addShop(Shop shop, ImageHolder shopImg)throws ShopOperationException;

	/**
	 * 更新店铺信息 包括对图片的处理
	 */
	ShopExecution modifyShop(Shop shop, ImageHolder shopImg)throws ShopOperationException;


}
