package com.imooc.o2o.dto;
/*
 * Created by wxn
 * 2018/11/29 17:03
 */


import com.imooc.o2o.entity.ShopAuthMap;
import com.imooc.o2o.enums.ShopAuthMapStateEnum;

import java.util.List;

public class ShopAuthMapExecution {
	private int state;

	private String stateInfo;

	private Integer count;

	private ShopAuthMap shopAuthMap;

	private List<ShopAuthMap> shopAuthMapList;

	public ShopAuthMapExecution(){

	}

	//失败的构造器
	public ShopAuthMapExecution(ShopAuthMapStateEnum stateEnum){
		this.state = stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
	}

	//成功的构造器
	public ShopAuthMapExecution(ShopAuthMapStateEnum stateEnum,
								List<ShopAuthMap>shopAuthMapList){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shopAuthMapList = shopAuthMapList;
	}
	//成功的构造器
	public ShopAuthMapExecution(ShopAuthMapStateEnum stateEnum,
								ShopAuthMap shopAuthMap){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shopAuthMap = shopAuthMap;
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public ShopAuthMap getShopAuthMap() {
		return shopAuthMap;
	}

	public void setShopAuthMap(ShopAuthMap shopAuthMap) {
		this.shopAuthMap = shopAuthMap;
	}

	public List<ShopAuthMap> getShopAuthMapList() {
		return shopAuthMapList;
	}

	public void setShopAuthMapList(List<ShopAuthMap> shopAuthMapList) {
		this.shopAuthMapList = shopAuthMapList;
	}
}
