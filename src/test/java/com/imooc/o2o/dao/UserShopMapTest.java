package com.imooc.o2o.dao;
/*
 * Created by wxn
 * 2018/11/28 23:17
 */

import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.UserShopMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserShopMapTest {

	@Autowired
	UserShopMapDao userShopMapDao;

	@Test
	public void testInsertUserShop(){
		UserShopMap userShopMap = new UserShopMap();
		PersonInfo user = new PersonInfo();
		Shop shop = new Shop();

		user.setUserId(8L);
		user.setName("李翔");
		userShopMap.setUser(user);

		shop.setShopId(29L);
		shop.setShopName("好好学习");
		userShopMap.setShop(shop);

		userShopMap.setPoint(888);
		userShopMap.setCreateTime(new Date());

		userShopMapDao.insertUserShopMap(userShopMap);

	}

	@Test
	public void testQuery(){
		UserShopMap condition = new UserShopMap();
		PersonInfo user = new PersonInfo();
		Shop shop = new Shop();

		user.setUserId(121L);
		shop.setShopId(29L);

		condition.setUser(user);
		condition.setShop(shop);

		userShopMapDao.queryUserShopMapList(condition,0,5);

		System.out.println(userShopMapDao.queryUserShopMapCount(condition));

		userShopMapDao.queryUserShopMap(12L,29L);

	}

	@Test
	public void testUpdate(){
		UserShopMap userShopMap = new UserShopMap();
		PersonInfo user = new PersonInfo();
		Shop shop = new Shop();

		userShopMap.setUserShopId(1);

		user.setUserId(12L);
		userShopMap.setUser(user);

		shop.setShopId(29L);
		userShopMap.setShop(shop);

		userShopMap.setPoint(555);

		userShopMapDao.updateUserShopMapPoint(userShopMap);
	}

}
