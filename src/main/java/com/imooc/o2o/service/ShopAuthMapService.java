package com.imooc.o2o.service;
/*
 * Created by wxn
 * 2018/11/29 17:13
 */


import com.imooc.o2o.dto.ShopAuthMapExecution;
import com.imooc.o2o.entity.ShopAuthMap;
import com.imooc.o2o.exception.ShopAuthMapException;

public interface ShopAuthMapService {
	/**
	 * 根据店铺id分页显示授权信息
	 */
	ShopAuthMapExecution listShopAuthMapByShopId(Long shopId,Integer pageIndex , Integer pageSize);

	/**
	 * 根据shopAuthId返回对应的授权信息
	 */
	ShopAuthMap getShopAuthMapById(Long shopAuthId);

	/**
	 * 添加授权信息
	 */
	ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap)throws ShopAuthMapException;

	/**
	 * 更新授权信息 职位 状态等
	 */
	ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap)throws ShopAuthMapException;
}
