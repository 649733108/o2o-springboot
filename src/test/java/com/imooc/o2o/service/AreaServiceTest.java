package com.imooc.o2o.service;
/*
 * Created by wxn
 * 2018/8/19 17:49
 */


import com.imooc.o2o.entity.Area;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaServiceTest {

	@Autowired
	private AreaService areaService;
	@Autowired
	private CacheService cacheService;

	@Test
	public void testGetAreaList(){
		List<Area> areaList = areaService.getAreaList();
		for (Area area :areaList){
			System.out.println(area.getAreaId() + " : " + area.getAreaName());
		}
		cacheService.removeFromCache(areaService.AREALISTKEY);
		areaList = areaService.getAreaList();
		for (Area area :areaList){
			System.out.println(area.getAreaId() + " : " + area.getAreaName());
		}
	}
}
