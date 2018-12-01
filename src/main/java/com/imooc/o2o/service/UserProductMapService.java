package com.imooc.o2o.service;
/*
 * Created by wxn
 * 2018/12/1 23:40
 */


import com.imooc.o2o.dto.UserProductMapExecution;
import com.imooc.o2o.entity.UserProductMap;

public interface UserProductMapService {

	UserProductMapExecution listUserProductMap(UserProductMap userProductCondition , Integer pageIndex , Integer pageSize);

}
