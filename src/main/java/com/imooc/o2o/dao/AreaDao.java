package com.imooc.o2o.dao;
/*
 * Created by wxn
 * 2018/8/19 16:43
 */


import com.imooc.o2o.entity.Area;

import java.util.List;

public interface AreaDao {

	/**
	 * 列出区域列表
	 * @return areaList
	 */
	List<Area> queryArea();
}
