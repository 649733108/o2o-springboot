package com.imooc.o2o.enums;
/*
 * Created by wxn
 * 2018/8/24 13:37
 */


public enum  ProductCategoryStateEnum {


	SUCCESS(1,"操作成功"),
	INNER_ERROR(-1001,"内部系统错误"),
	EMPTY_LIST(-1002,"添加数少于1"),



			;

	private int state;
	private String stateInfo;

	ProductCategoryStateEnum(int state, String stateInfo){
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public static ProductCategoryStateEnum stateOf(int state){
		for (ProductCategoryStateEnum productCategoryStateEnum : values()){
			if (productCategoryStateEnum.getState() == state){
				return productCategoryStateEnum;
			}
		}
		return null;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}
}
