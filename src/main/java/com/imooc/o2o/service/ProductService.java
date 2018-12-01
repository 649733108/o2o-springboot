package com.imooc.o2o.service;
/*
 * Created by wxn
 * 2018/8/26 21:56
 */


import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.exception.ProductOperationException;

import java.util.List;

public interface ProductService {

	/**
	 * 查询商品列表并分页
	 * 条件：商品名（模糊） 状态 shopId 商品类别
	 */
	ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

	/**
	 * 添加商品信息 以及图片处理
	 */
	ProductExecution addProduct(Product product,
								ImageHolder thumbnail,
								List<ImageHolder> productImgList
	) throws ProductOperationException;

	/**
	 * 通过productId查询唯一商品
	 */
	Product getProductById(long productId);

	/**
	 * 修改商品信息以及图片处理
	 */
	ProductExecution modifyProduct(Product product,
								   ImageHolder thumbnail,
								   List<ImageHolder> productImgHolderList)
	throws ProductOperationException;

}

