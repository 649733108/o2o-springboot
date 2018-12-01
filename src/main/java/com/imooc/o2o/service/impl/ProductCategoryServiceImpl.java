package com.imooc.o2o.service.impl;
/*
 * Created by wxn
 * 2018/8/24 12:25
 */


import com.imooc.o2o.dao.ProductCategoryDao;
import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.enums.ProductCategoryStateEnum;
import com.imooc.o2o.exception.ProductCategoryOpeationException;
import com.imooc.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryDao productCategoryDao;

	@Autowired
	private ProductDao productDao;


	@Override
	public List<ProductCategory> getProductCategoryByShopId(long shopId) {
		return productCategoryDao.queryProductCategoryListByShopId(shopId);
	}

	@Override
	@Transactional
	public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOpeationException {
		if (productCategoryList!=null && productCategoryList.size()>0){
			try {
				int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
				if (effectedNum<0){
					throw new ProductCategoryOpeationException("店铺类别创建失败");
				}else {
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
				}
			}catch (Exception e){
				throw new ProductCategoryOpeationException("batchAddProductCategory error :" + e.getMessage());
			}
		}else {
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
		}
	}

	@Override
	@Transactional
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOpeationException {
		//将此类别下的商品 的类别id置为空
		try {
			int effectedNum = productDao.updateProductCategoryToNull(productCategoryId);
			if (effectedNum<0){
				throw new ProductCategoryOpeationException("商品类别更新失败");
			}
		}catch (Exception e){
			throw new ProductCategoryOpeationException("商品类别删除失败" + e.getMessage()) ;
		}

		try{
			int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId,shopId);
			if (effectedNum<0){
				throw new ProductCategoryOpeationException("商品类别删除失败");
			}else {
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}
		}catch (Exception e){
			throw new ProductCategoryOpeationException("delete Product category error : " + e.getMessage());
		}
	}
}
