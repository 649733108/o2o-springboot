package com.imooc.o2o.dao;
/*
 * Created by wxn
 * 2018/8/19 16:59
 */


import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.Award;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AwardDaoTest {

	@Autowired
	private AwardDao awardDao;

	@Test
	public void testInsertAward(){
		Award award = new Award();
		award.setAwardDesc("awarddddddd");
		award.setAwardImg("img");
		award.setAwardName("jiangpin");
		award.setEnableStatus(1);
		award.setCreateTime(new Date());
		award.setLastEditTime(new Date());
		award.setPoint(50);
		award.setPriority(100);
		award.setShopId(29L);

		awardDao.insertAward(award);
	}

	@Test
	public void testQueryAwardList(){
		Award condition = new Award();
		condition.setShopId(29L);

		List<Award> awards = awardDao.queryAwardList(condition,0,5);

		System.out.println(awards.size());
	}

	@Test
	public void testQueryAwardCount(){
		Award condition = new Award();
		condition.setShopId(29L);
		System.out.println(awardDao.queryAwardCount(condition));
	}

	@Test
	public void testQueryById(){
		long id = 1L;
		Award award = awardDao.queryAwardByAwardId(id);

	}

	@Test
	public void testUpdateAward(){
		Award award = new Award();
		award.setAwardId(1L);
		award.setAwardDesc("qqqqq");
		award.setAwardImg("qqq");
		award.setAwardName("qqq");
		award.setEnableStatus(1);
		award.setCreateTime(new Date());
		award.setLastEditTime(new Date());
		award.setPoint(50);
		award.setPriority(100);
		award.setShopId(29L);

		awardDao.updateAward(award);
	}

	@Test
	public void testDelete(){
		awardDao.deleteAward(2L,29L);
	}
}
