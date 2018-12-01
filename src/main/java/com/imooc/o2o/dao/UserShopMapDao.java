package com.imooc.o2o.dao;
/*
 * Created by wxn
 * 2018/11/28 22:53
 */


import com.imooc.o2o.entity.UserShopMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserShopMapDao {

	List<UserShopMap> queryUserShopMapList(
			@Param("userShopCondition") UserShopMap userShopCondition,
			@Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize
	);

	int queryUserShopMapCount(@Param("userShopCondition") UserShopMap userShopCondition);

	UserShopMap queryUserShopMap(@Param("userId") long userId,
								 @Param("shopId")long shopId);

	int insertUserShopMap(UserShopMap userShopMap);

	int updateUserShopMapPoint(UserShopMap userShopMap);
}
