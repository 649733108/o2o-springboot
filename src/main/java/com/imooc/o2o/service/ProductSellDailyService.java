package com.imooc.o2o.service;
/*
 * Created by wxn
 * 2018/12/1 23:03
 */


import com.imooc.o2o.entity.ProductSellDaily;

import java.util.Date;
import java.util.List;

public interface ProductSellDailyService {

	/**
	 * 每日定时对所有店铺的商品销量进行统计
	 */
	void dailyCalculate();

	List<ProductSellDaily> listProductSellDaily(ProductSellDaily productSellDailyCondition, Date beginTime,Date endTime);
}
