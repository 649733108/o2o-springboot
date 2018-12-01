package com.imooc.o2o.dao;
/*
 * Created by wxn
 * 2018/8/26 21:01
 */


import com.imooc.o2o.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {

	/**
	 * 批量添加productImg
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);

	/**
	 * 删除指定商品下的所有详情图
	 */
	int deleteProductImgByProductId(long productId);

	/**
	 * 根据productId查询productImgList
	 */
	List<ProductImg>queryProductImgList(long productId);

}
