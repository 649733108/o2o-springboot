package com.imooc.o2o.dao;
/*
 * Created by wxn
 * 2018/8/24 10:50
 */


import com.imooc.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryDao {

	List<ProductCategory> queryProductCategoryListByShopId(long shopId);

	/**
	 * 批量新增商品类别
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);

	/**
	 * 删除指定商品类别
	 */
	int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);

}
