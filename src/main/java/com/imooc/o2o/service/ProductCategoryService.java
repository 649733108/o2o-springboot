package com.imooc.o2o.service;
/*
 * Created by wxn
 * 2018/8/24 12:24
 */


import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.exception.ProductCategoryOpeationException;

import java.util.List;

public interface ProductCategoryService {

	List<ProductCategory> getProductCategoryByShopId(long shopId);

	ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)throws ProductCategoryOpeationException;

	/**
	 * 将此类别下的商品 的类别id置为空，然后再删除掉该商品类别
	 */
	ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOpeationException;
}
