package com.imooc.o2o.service;
/*
 * Created by wxn
 * 2018/8/21 16:50
 */


import com.imooc.o2o.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {
	public static final String SCLISTKEY = "shopcategorylist";

	List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
