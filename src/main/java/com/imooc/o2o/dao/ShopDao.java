package com.imooc.o2o.dao;
/*
 * Created by wxn
 * 2018/8/20 16:03
 */


import com.imooc.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {

	/**
	 * 新增店铺
	 *
	 * @param shop 店铺
	 * @return 影响行数
	 */
	int insertShop(Shop shop);

	int updateShop(Shop shop);

	Shop queryByShopId(long shopId);

	/**
	 * 分页查询店铺
	 * 条件：店铺名（模糊），店铺状态，店铺类别，areaId，ownerId
	 */
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
							 @Param("rowIndex") int rowIndex,
							 @Param("pageSize") int pageSize);

	/**
	 * 查询店铺总数
	 */
	int queryShopCount(@Param("shopCondition") Shop shopCondition);


}
