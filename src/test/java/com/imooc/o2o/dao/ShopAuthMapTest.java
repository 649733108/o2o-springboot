package com.imooc.o2o.dao;
/*
 * Created by wxn
 * 2018/11/28 23:17
 */

import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopAuthMap;
import com.imooc.o2o.entity.UserShopMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopAuthMapTest {

	@Autowired
	ShopAuthMapDao shopAuthMapDao;

	@Test
	public void insert(){
		ShopAuthMap shopAuthMap1 = new ShopAuthMap();
		PersonInfo employee = new PersonInfo();
		employee.setUserId(12L);
		shopAuthMap1.setEmployee(employee);
		Shop shop = new Shop();
		shop.setShopId(29L);
		shopAuthMap1.setShop(shop);
		shopAuthMap1.setTitle("CEO");
		shopAuthMap1.setTitleFlag(1);
		shopAuthMap1.setCreateTime(new Date());
		shopAuthMap1.setLastEditTime(new Date());
		shopAuthMap1.setEnableStatus(1);
		shopAuthMapDao.insertShopAuthMap(shopAuthMap1);

		ShopAuthMap shopAuthMap2 = new ShopAuthMap();
		PersonInfo employee2 = new PersonInfo();
		employee2.setUserId(8L);
		shopAuthMap2.setEmployee(employee2);
		Shop shop2 = new Shop();
		shop2.setShopId(29L);
		shopAuthMap2.setShop(shop2);
		shopAuthMap2.setTitle("打工仔");
		shopAuthMap2.setTitleFlag(2);
		shopAuthMap2.setCreateTime(new Date());
		shopAuthMap2.setLastEditTime(new Date());
		shopAuthMap2.setEnableStatus(1);
		shopAuthMapDao.insertShopAuthMap(shopAuthMap2);

	}

	@Test
	public void testQuery(){
		List<ShopAuthMap>shopAuthMaps = shopAuthMapDao.queryShopAuthMapListByShopId(29L,0,5);

		System.out.println(shopAuthMapDao.queryShopAuthCountByShopId(29));

		ShopAuthMap shopAuthMap = shopAuthMapDao.queryShopAuthMapById(28);
	}

	@Test
	public void testUpdate(){
		List<ShopAuthMap>shopAuthMaps = shopAuthMapDao.queryShopAuthMapListByShopId(29L,0,5);
		shopAuthMaps.get(0).setTitleFlag(3);
		shopAuthMaps.get(0).setTitle("老板");
		shopAuthMaps.get(0).setLastEditTime(new Date());

		shopAuthMapDao.updateShopAuthMap(shopAuthMaps.get(0));
	}

	@Test
	public void testDelete(){

		shopAuthMapDao.deleteShopAuthMap(28);
	}


}
