package com.imooc.o2o.dao;
/*
 * Created by wxn
 * 2018/8/19 16:59
 */


import com.imooc.o2o.entity.Award;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.UserAwardMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAwardMapDaoTest {

	@Autowired
	private UserAwardMapDao userAwardMapDao;

	@Test
	public void testInsertUserAwardMap(){
		UserAwardMap userAwardMap = new UserAwardMap();
		userAwardMap.setCreateTime(new Date());
		userAwardMap.setPoint(59);
		userAwardMap.setUsedStatus(1);

		PersonInfo user = new PersonInfo();
		PersonInfo operator = new PersonInfo();
		Shop shop = new Shop();
		Award award = new Award();

		user.setUserId(12L);
		user.setName("最讨厌证明了");
		userAwardMap.setUser(user);

		operator.setUserId(12L);
		operator.setName("最讨厌证明了");
		userAwardMap.setOperator(operator);

		shop.setShopId(29L);
		shop.setShopName("一起学编程");
		userAwardMap.setShop(shop);

		award.setAwardId(3);
		award.setAwardName("jiangpin");
		userAwardMap.setAward(award);

		userAwardMapDao.insertUserAwardMap(userAwardMap);

	}

	@Test
	public void testQueryUserAwardById(){
		long userAwardId=1L;

		UserAwardMap userAwardMap = userAwardMapDao.queryUserAwardMapById(userAwardId);

		System.out.println(userAwardMap.getUser().getName());
	}

	@Test
	public void testQueryUserAwardMapList(){
		UserAwardMap condition = new UserAwardMap();
		PersonInfo personInfo = new PersonInfo();
		personInfo.setName("证明");
		condition.setUser(personInfo);

		List<UserAwardMap> userAwardMaps = userAwardMapDao.queryUserAwardMapList(condition,0,5);
		System.out.println(userAwardMaps.size());
	}

	@Test
	public void testQueryUserAwardCount(){
		UserAwardMap condition = new UserAwardMap();
		PersonInfo personInfo = new PersonInfo();
		personInfo.setName("证明");
		condition.setUser(personInfo);

		System.out.println(userAwardMapDao.queryUserAwardMapCount(condition));
	}

	@Test
	public void testUpdate(){
		UserAwardMap userAwardMap = new UserAwardMap();
		userAwardMap.setUserAwardId(1L);
		userAwardMap.setPoint(999);
		userAwardMap.setUsedStatus(0);

		PersonInfo user = new PersonInfo();
		PersonInfo operator = new PersonInfo();
		Shop shop = new Shop();
		Award award = new Award();

		user.setUserId(12L);
		user.setName("最讨厌证明了");
		userAwardMap.setUser(user);

		operator.setUserId(12L);
		operator.setName("最讨厌证明了");
		userAwardMap.setOperator(operator);

		shop.setShopId(29L);
		shop.setShopName("一起学编程");
		userAwardMap.setShop(shop);

		award.setAwardId(3);
		award.setAwardName("jiangpin");
		userAwardMap.setAward(award);

		userAwardMapDao.updateUserAwardMap(userAwardMap);
	}


}
