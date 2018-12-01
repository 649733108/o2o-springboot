package com.imooc.o2o.entity;
/*
 * Created by wxn
 * 2018/11/19 2:45
 */

import java.util.Date;

/**
 * 店铺授权映射
 */
public class ShopAuthMap {

	private Long shopAuthId;
	//职称名
	private String title;
	//职称符号 用于权限控制
	private Integer titleFlag;
	//授权有效状态 0无效 1有效
	private Integer enableStatus;
	private Date createTime;
	private Date lastEditTime;

	private String name;

	private PersonInfo employee;
	private Shop shop;

	public Long getShopAuthId() {
		return shopAuthId;
	}

	public void setShopAuthId(Long shopAuthId) {
		this.shopAuthId = shopAuthId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getTitleFlag() {
		return titleFlag;
	}

	public void setTitleFlag(Integer titleFlag) {
		this.titleFlag = titleFlag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public PersonInfo getEmployee() {
		return employee;
	}

	public void setEmployee(PersonInfo employee) {
		this.employee = employee;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Integer getEnableStatus() {
		return enableStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEnableStatus(Integer enableStatus) {

		this.enableStatus = enableStatus;
	}
}
