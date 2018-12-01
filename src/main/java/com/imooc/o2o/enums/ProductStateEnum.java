package com.imooc.o2o.enums;
/*
 * Created by wxn
 * 2018/8/21 11:13
 */


public enum ProductStateEnum {

	CHECK(0,"审核中"),
	OFFLINE(-1,"非法商品"),
	SUCCESS(1,"操作成功"),
	PASS(2,"通过认证"),
	INNER_ERROR(-1001,"内部系统错误"),
	NULL_SHOP_ID(-1002,"ShopId为空"),
	NULL_SHOP(-1003,"店铺为空"),
	EMPTY(-1004,"参数为空" );

	private int state;
	private String stateInfo;

	private ProductStateEnum(int state , String stateInfo){
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public static ProductStateEnum stateOf(int state){
		for (ProductStateEnum shopStateEnum : values()){
			if (shopStateEnum.getState() == state){
				return shopStateEnum;
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
