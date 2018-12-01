package com.imooc.o2o.dto;
/*
 * Created by wxn
 * 2018/8/24 14:25
 */


import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.enums.ProductCategoryStateEnum;

import java.util.List;

public class ProductCategoryExecution {

	//结果状态
	private int state;

	//状态标识
	private String stateInfo;

	private List<ProductCategory>productCategoryList;

	public ProductCategoryExecution(){

	}

	//操作失败时使用的构造器
	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum , List<ProductCategory>productCategoryList){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.productCategoryList = productCategoryList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public List<ProductCategory> getProductCategoryList() {
		return productCategoryList;
	}

	public void setProductCategoryList(List<ProductCategory> productCategoryList) {
		this.productCategoryList = productCategoryList;
	}
}
