package com.imooc.o2o.dao;
/*
 * Created by wxn
 * 2018/11/28 20:38
 */


import com.imooc.o2o.entity.ProductSellDaily;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ProductSellDailyDao {

	List<ProductSellDaily> queryProductSellDailyList(
			@Param("productSellDailyCondition")ProductSellDaily productSellDailyCondition,
			@Param("beginTime")Date beginTime,
			@Param("endTime")Date endTime);

	int insertProductSellDaily();

}
