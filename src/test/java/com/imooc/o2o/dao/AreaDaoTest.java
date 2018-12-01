package com.imooc.o2o.dao;
/*
 * Created by wxn
 * 2018/8/19 16:59
 */


import com.imooc.o2o.entity.Area;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaDaoTest {

	@Autowired
	private AreaDao areaDao;

	@Test
	public void testQueryArea(){
		List<Area> areaList = areaDao.queryArea();
		assertEquals(4,areaList.size());
	}
}
