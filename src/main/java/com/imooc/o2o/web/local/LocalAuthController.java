package com.imooc.o2o.web.local;
/*
 * Created by wxn
 * 2018/10/31 4:29
 */

import com.imooc.o2o.dto.LocalAuthExecution;
import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.enums.LocalAuthStateEnum;
import com.imooc.o2o.service.LocalAuthService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/local", method = RequestMethod.GET)
public class LocalAuthController {
	@Autowired
	private LocalAuthService localAuthService;

	@RequestMapping(value = "/bindlocalauth", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> bindLocalAuth(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		//验证码校验
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		//获取输入的账号
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password = HttpServletRequestUtil.getString(request, "password");
		//从session中获取当前用户信息(用户一旦微信登录过后,便能获取到用户的信息)
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		//非空判断 账号密码以及当前用户session非空
		if (userName != null && password != null && user != null && user.getUserId() != null) {
			LocalAuth localAuth = new LocalAuth();
			localAuth.setUserName(userName);
			localAuth.setPassword(password);
			localAuth.setPersonInfo(user);
			//绑定账号
			LocalAuthExecution lae = localAuthService.bindLocalAuth(localAuth);
			if (lae.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", lae.getStateInfo());
			}
		} else if (userName==null || password==null){
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名和密码均不能为空");
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请登录微信账号");
		}
		return modelMap;
	}

	@RequestMapping(value = "/changelocalpwd", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> changeLocalPwd(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		//验证码校验
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password = HttpServletRequestUtil.getString(request, "password");
		String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		if (userName != null && password != null && newPassword != null && user != null
				&& user.getUserId() != null && !password.equals(newPassword)) {
			try {
				//查看原先账号 看看信息是否一致
				LocalAuth localAuth = localAuthService.getLocalAuthByUserId(user.getUserId());
				if (localAuth==null|| !localAuth.getUserName().equals(userName)){
					modelMap.put("success",false);
					modelMap.put("errMsg","输入账号非本次登录账号");
					return modelMap;
				}
				//修改密码
				LocalAuthExecution lae  = localAuthService.modifyLocalAuth(user.getUserId(),userName,password,newPassword);
				if (lae.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", lae.getStateInfo());
				}
			}catch (Exception e){
				modelMap.put("success",false);
				modelMap.put("errMsg",e.getMessage());
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入正确信息");
		}
		return modelMap;
	}

	@RequestMapping(value = "/logincheck",method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> loginCheck(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		//获取是否需要验证码校验的标识符
		boolean needVerify = HttpServletRequestUtil.getBoolean(request,"needVerify");
		if (needVerify && !CodeUtil.checkVerifyCode(request)){
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password = HttpServletRequestUtil.getString(request, "password");
		if (userName!=null && password!=null){
			LocalAuth localAuth = localAuthService.getLocalAuthByUserNameAndPwd(userName,password);
			if (localAuth!=null){
				modelMap.put("success",true);
				request.getSession().setAttribute("user",localAuth.getPersonInfo());
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "用户名或密码错误");
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名或密码不能为空");
		}
		return modelMap;
	}

	@RequestMapping(value = "/logout" ,method = RequestMethod.POST)
	@ResponseBody
	private Map<String ,Object>logout(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		request.getSession().setAttribute("user",null);
		modelMap.put("success", true);
		return modelMap;
	}

}
