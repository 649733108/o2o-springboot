package com.imooc.o2o.dao;
/*
 * Created by wxn
 * 2018/11/28 21:41
 */

import com.imooc.o2o.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductSellDailyTest {

	@Autowired
	ProductSellDailyDao productSellDailyDao;

	@Test
	public void testInsert(){
		productSellDailyDao.insertProductSellDaily();
	}

	@Test
	public void query(){
		Date now = new Date();
		ProductSellDaily productSellDaily = new ProductSellDaily();
		productSellDaily.setCreateTime(now);
		List<ProductSellDaily>productSellDailies = productSellDailyDao.queryProductSellDailyList(productSellDaily,now,now);


	}
}
