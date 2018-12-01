package com.imooc.o2o.service.impl;
/*
 * Created by wxn
 * 2018/9/26 14:26
 */


import com.imooc.o2o.dao.PersonInfoDao;
import com.imooc.o2o.dao.WechatAuthDao;
import com.imooc.o2o.dto.WechatAuthExecution;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.WechatAuth;
import com.imooc.o2o.enums.WechatAuthStateEnum;
import com.imooc.o2o.exception.WechatAuthException;
import com.imooc.o2o.service.WechatAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class WechatAuthServiceImpl implements WechatAuthService {

	private static Logger log = LoggerFactory
			.getLogger(WechatAuthServiceImpl.class);

	@Autowired
	WechatAuthDao wechatAuthDao;

	@Autowired
	private PersonInfoDao personInfoDao;

	@Override
	public WechatAuth getWechatAuthByOpenId(String openId) {
		return wechatAuthDao.queryWechatInfoByOpenId(openId);
	}


	@Override
	@Transactional
	public WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthException {

		//空值判断
		if (wechatAuth == null || wechatAuth.getOpenId()==null){
			return new WechatAuthExecution(WechatAuthStateEnum.NULL_AUTH_INFO);
		}

		try {
			//设置创建时间
			wechatAuth.setCreateTime(new Date());
			//如果微信账号里夹带着用户信息并且用户Id为空,则认为第一次登录
			//自动创建用户信息
			if (wechatAuth.getPersonInfo()!=null && wechatAuth.getPersonInfo().getUserId()==null){
				try {
					wechatAuth.getPersonInfo().setCreateTime(new Date());
					wechatAuth.getPersonInfo().setLastEditTime(new Date());
					wechatAuth.getPersonInfo().setEnableStatus(1);
					PersonInfo personInfo = wechatAuth.getPersonInfo();
					int effectNum = personInfoDao.insertPersonInfo(personInfo);
					wechatAuth.setPersonInfo(personInfo);
					if (effectNum<=0){
						throw new WechatAuthException("添加用户失败");
					}
				}catch (Exception e){
					log.error("insertPersonInfo error : " + e.toString());
				}
			}
			//创建属于本平台的微信账号
			int effectNum = wechatAuthDao.insertWechatAuth(wechatAuth);
			if (effectNum<=0){
				throw new WechatAuthException("账号创建失败");
			}
			else {
				return new WechatAuthExecution(WechatAuthStateEnum.SUCCESS , wechatAuth);
			}
		}catch (Exception e){
			log.error("insertWechatAuth error : " + e.toString());
			throw new WechatAuthException("insertWechatAuth error : " + e.toString());
		}
	}
}
