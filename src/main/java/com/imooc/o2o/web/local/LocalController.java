package com.imooc.o2o.web.local;
/*
 * Created by wxn
 * 2018/10/31 5:37
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/local")
public class LocalController {
	//绑定微信账号页路由
	@RequestMapping(value = "/accountbind" , method = RequestMethod.GET)
	private String accountBind(){
		return "local/accountbind";
	}

	//修改密码页路由
	@RequestMapping(value = "/changepsw" , method = RequestMethod.GET)
	private String changepsw(){
		return "local/changepsw";
	}

	//登录页路由
	@RequestMapping(value = "/login" , method = RequestMethod.GET)
	private String login(){
		return "local/login";
	}
}
