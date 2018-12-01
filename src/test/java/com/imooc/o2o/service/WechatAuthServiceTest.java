package com.imooc.o2o.service;
/*
 * Created by wxn
 * 2018/9/26 14:43
 */


import com.imooc.o2o.dto.WechatAuthExecution;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.WechatAuth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatAuthServiceTest{

	@Autowired
	private WechatAuthService wechatAuthService;

	@Test
	public void testRegister(){
		WechatAuth wechatAuth = new WechatAuth();
		PersonInfo personInfo = new PersonInfo();
		String openId = "qwertyyyu";
		personInfo.setName("测试一下");
		personInfo.setUserType(1);
		wechatAuth.setPersonInfo(personInfo);
		wechatAuth.setOpenId(openId);

		WechatAuthExecution wechatAuthExecution = wechatAuthService.register(wechatAuth);

		wechatAuth = wechatAuthService.getWechatAuthByOpenId(openId);
		System.out.println(wechatAuth.getPersonInfo().getName());
	}
}
