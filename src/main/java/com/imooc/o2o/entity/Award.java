package com.imooc.o2o.entity;
/*
 * Created by wxn
 * 2018/11/1 15:12
 */


import java.util.Date;

/**
 * 奖品
 */
public class Award {

	//主键Id
	private long awardId;
	//奖品名
	private String awardName;
	//描述
	private String awardDesc;
	//图片地址
	private String awardImg;
	//需要多少积分去兑换
	private Integer point;
	//权重
	private Integer priority;

	private Date createTime;
	private Date lastEditTime;

	//过期时间
	private Date expireTime;

	//可用状态：0不可用 1可用
	private Integer enableStatus;
	//属于哪个店铺
	private Long shopId;

	public long getAwardId() {
		return awardId;
	}

	public void setAwardId(long awardId) {
		this.awardId = awardId;
	}

	public String getAwardName() {
		return awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	public String getAwardDesc() {
		return awardDesc;
	}

	public void setAwardDesc(String awardDesc) {
		this.awardDesc = awardDesc;
	}

	public String getAwardImg() {
		return awardImg;
	}

	public void setAwardImg(String awardImg) {
		this.awardImg = awardImg;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
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

	public Integer getEnableStatus() {
		return enableStatus;
	}

	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
}
