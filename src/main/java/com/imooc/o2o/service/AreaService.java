package com.imooc.o2o.service;
/*
 * Created by wxn
 * 2018/8/19 17:39
 */


import com.imooc.o2o.entity.Area;

import java.util.List;

public interface AreaService {
	public static final String AREALISTKEY = "arealist";

	List<Area>getAreaList();
}
