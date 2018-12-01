package com.imooc.o2o.service.impl;
/*
 * Created by wxn
 * 2018/12/1 23:48
 */


import com.imooc.o2o.dao.UserProductMapDao;
import com.imooc.o2o.dto.UserProductMapExecution;
import com.imooc.o2o.entity.UserProductMap;
import com.imooc.o2o.service.UserProductMapService;
import com.imooc.o2o.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProductMapServiceImpl implements UserProductMapService {

	@Autowired
	private UserProductMapDao userProductMapDao;

	@Override
	public UserProductMapExecution listUserProductMap(UserProductMap userProductCondition, Integer pageIndex, Integer pageSize) {
		if (userProductCondition!=null && pageIndex!=null && pageSize!=null){
			int beginIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
			List<UserProductMap> userProductMaps = userProductMapDao.queryUserProductMapList(userProductCondition,beginIndex,pageSize);
			int count = userProductMapDao.queryUserProductMapCount(userProductCondition);
			UserProductMapExecution se = new UserProductMapExecution();
			se.setCount(count);
			se.setUserProductMapList(userProductMaps);
			return se;
		}else {
			return null;
		}
	}
}
