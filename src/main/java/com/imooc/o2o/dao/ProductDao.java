package com.imooc.o2o.dao;
/*
 * Created by wxn
 * 2018/8/26 20:49
 */


import com.imooc.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {

	/**
	 * 插入商品
	 */
	int insertProduct(Product product);

	/**
	 * 查询商品列表并分页
	 * 条件： 商品名（模糊） 商品状态 店铺Id 商品类别
	 */
	List<Product> queryProductList(@Param("productCondition") Product productCondition,
								   @Param("rowIndex") int rowIndex,
								   @Param("pageSize") int pageSize);

	/**
	 * 根据productId查询商品
	 */
	Product queryProductById(long productId);

	/**
	 * 查询对应的商品总数
	 */
	int queryProductCount(@Param("productCondition") Product productCondition);

	/**
	 * 更新商品信息
	 */
	int updateProduct(Product product);



	/**
	 * 删除商品类别之前 将商品的productCategoryId置为空
	 */
	int updateProductCategoryToNull(long productCategoryId);
}
