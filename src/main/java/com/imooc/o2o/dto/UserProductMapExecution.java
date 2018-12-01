package com.imooc.o2o.dto;
/*
 * Created by wxn
 * 2018/11/29 17:03
 */


import com.imooc.o2o.entity.UserProductMap;
import com.imooc.o2o.enums.UserProductMapStateEnum;

import java.util.List;

public class UserProductMapExecution {
	private int state;

	private String stateInfo;

	private Integer count;

	private UserProductMap userProductMap;

	private List<UserProductMap> userProductMapList;

	public UserProductMapExecution(){

	}

	//失败的构造器
	public UserProductMapExecution(UserProductMapStateEnum stateEnum){
		this.state = stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
	}

	//成功的构造器
	public UserProductMapExecution(UserProductMapStateEnum stateEnum,
								   List<UserProductMap>userProductMapList){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.userProductMapList = userProductMapList;
	}
	//成功的构造器
	public UserProductMapExecution(UserProductMapStateEnum stateEnum,
								   UserProductMap userProductMap){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.userProductMap = userProductMap;
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

	public UserProductMap getUserProductMap() {
		return userProductMap;
	}

	public void setUserProductMap(UserProductMap userProductMap) {
		this.userProductMap = userProductMap;
	}

	public List<UserProductMap> getUserProductMapList() {
		return userProductMapList;
	}

	public void setUserProductMapList(List<UserProductMap> userProductMapList) {
		this.userProductMapList = userProductMapList;
	}
}
