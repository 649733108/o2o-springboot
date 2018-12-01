package com.imooc.o2o.service;
/*
 * Created by wxn
 * 2018/9/26 14:07
 */


import com.imooc.o2o.dto.WechatAuthExecution;
import com.imooc.o2o.entity.WechatAuth;
import com.imooc.o2o.exception.WechatAuthException;

public interface WechatAuthService {

	/**
	 * 通过openId查询微信认证信息
	 */
	WechatAuth getWechatAuthByOpenId(String openId);

	/**
	 * 微信注册
	 */
	WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthException;
}
