package com.imooc.o2o.entity;
/*
 * Created by wxn
 * 2018/11/19 2:30
 */

import java.util.Date;

/**
 * 顾客店铺积分映射
 */
public class UserShopMap {

	private Integer userShopId;
	private Date createTime;
	private PersonInfo user;
	private Shop shop;
	private Integer point;

	public Integer getUserShopId() {
		return userShopId;
	}

	public void setUserShopId(Integer userShopId) {
		this.userShopId = userShopId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public PersonInfo getUser() {
		return user;
	}

	public void setUser(PersonInfo user) {
		this.user = user;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}
}
