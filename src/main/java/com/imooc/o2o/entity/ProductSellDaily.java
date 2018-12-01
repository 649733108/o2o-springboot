package com.imooc.o2o.entity;
/*
 * Created by wxn
 * 2018/11/19 2:34
 */

import java.util.Date;

/**
 * 商品日销量
 */
public class ProductSellDaily {

	//哪天的销量 精确到天
	private Date createTime;
	private Integer total;

	private Product product;
	private Shop shop;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
}
