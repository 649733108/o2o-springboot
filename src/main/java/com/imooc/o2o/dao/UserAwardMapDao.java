package com.imooc.o2o.dao;
/*
 * Created by wxn
 * 2018/11/28 3:05
 */


import com.imooc.o2o.entity.UserAwardMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserAwardMapDao {
	List<UserAwardMap> queryUserAwardMapList(@Param("userAwardCondition") UserAwardMap userAwardCondition,
											 @Param("rowIndex") int rowIndex,
											 @Param("pageSize") int pageSize);

	int queryUserAwardMapCount(@Param("userAwardCondition") UserAwardMap userAwardCondition);

	UserAwardMap queryUserAwardMapById(long userAwardId);

	int insertUserAwardMap(UserAwardMap userAwardMap);

	int updateUserAwardMap(UserAwardMap userAwardMap);
}
