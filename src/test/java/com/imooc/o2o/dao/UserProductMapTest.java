package com.imooc.o2o.dao;
/*
 * Created by wxn
 * 2018/11/28 21:41
 */

import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.UserProductMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserProductMapTest {

	@Autowired
	UserProductMapDao userProductMapDao;

	@Test
	public void testInsertUserProductMap(){
		UserProductMap userProductMap = new UserProductMap();
		userProductMap.setCreateTime(new Date());
		userProductMap.setPoint(50);

		PersonInfo user = new PersonInfo();
		PersonInfo operator = new PersonInfo();
		Product product = new Product();
		Shop shop = new Shop();

		user.setUserId(8L);
		user.setName("李翔");
		userProductMap.setUser(user);

		operator.setUserId(8L);
		operator.setName("李翔");
		userProductMap.setOperator(operator);

		shop.setShopId(20L);
		shop.setShopName("香喷喷奶茶店");
		userProductMap.setShop(shop);

		product.setProductId(15L);
		product.setProductName("鲜榨西瓜汁");
		userProductMap.setProduct(product);

		userProductMapDao.insertUserProductMap(userProductMap);

	}

	@Test
	public void testQueryUserProductMap(){
		UserProductMap condition = new UserProductMap();
		PersonInfo user = new PersonInfo();
		user.setUserId(12L);
		condition.setUser(user);

		List<UserProductMap> userProductMaps = userProductMapDao.queryUserProductMapList(condition,0,5);
		int count = userProductMapDao.queryUserProductMapCount(condition);
	}
}
