package com.imooc.o2o.service.impl;
/*
 * Created by wxn
 * 2018/12/1 23:03
 */


import com.imooc.o2o.dao.ProductSellDailyDao;
import com.imooc.o2o.entity.ProductSellDaily;
import com.imooc.o2o.service.ProductSellDailyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductSellDailyServiceImpl implements ProductSellDailyService {

	private static final Logger log = LoggerFactory.getLogger(ProductSellDailyServiceImpl.class);

	@Autowired
	private ProductSellDailyDao productSellDailyDao;

	@Override
	public void dailyCalculate() {
		log.info("Quartz Running");

		productSellDailyDao.insertProductSellDaily();

	}

	@Override
	public List<ProductSellDaily> listProductSellDaily(ProductSellDaily productSellDailyCondition, Date beginTime, Date endTime) {
		return productSellDailyDao.queryProductSellDailyList(productSellDailyCondition,beginTime,endTime);
	}
}
