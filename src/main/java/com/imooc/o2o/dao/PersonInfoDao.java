package com.imooc.o2o.dao;
/*
 * Created by wxn
 * 2018/9/26 13:18
 */


import com.imooc.o2o.entity.PersonInfo;

public interface PersonInfoDao {

	/**
	 * 通过用户Id查询用户
	 */
	PersonInfo queryPersonInfoById(Long userId);

	/**
	 * 添加用户信息
	 */
	int insertPersonInfo(PersonInfo personInfo);
}
