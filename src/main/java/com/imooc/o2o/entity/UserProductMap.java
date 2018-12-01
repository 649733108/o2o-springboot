package com.imooc.o2o.entity;
/*
 * Created by wxn
 * 2018/11/19 2:24
 */

import java.util.Date;

/**
 * 用户消费商品的映射
 */
public class UserProductMap {

	private Integer userProductId;

	private Date createTime;

	private PersonInfo user;

	private Product product;

	private PersonInfo operator;

	private Integer point;

	private Shop shop;

	public Integer getUserProductId() {
		return userProductId;
	}

	public void setUserProductId(Integer userProductId) {
		this.userProductId = userProductId;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public PersonInfo getOperator() {
		return operator;
	}

	public void setOperator(PersonInfo operator) {
		this.operator = operator;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
}
