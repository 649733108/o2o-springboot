package com.imooc.o2o.web.wechat;

import com.imooc.o2o.dto.WechatAuthExecution;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.WechatAuth;
import com.imooc.o2o.enums.WechatAuthStateEnum;
import com.imooc.o2o.service.PersonInfoService;
import com.imooc.o2o.service.WechatAuthService;
import com.imooc.o2o.util.weixin.WeiXinUser;
import com.imooc.o2o.util.weixin.WeiXinUserUtil;
import com.imooc.o2o.util.weixin.message.pojo.UserAccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("wechatlogin")
/**
 * 从微信菜单点击后调用的接口，可以在url里增加参数（role_type）来表明是从商家还是从玩家按钮进来的，依次区分登陆后跳转不同的页面
 * 玩家会跳转到index.html页面
 * 商家如果没有注册，会跳转到注册页面，否则跳转到任务管理页面
 * 如果是商家的授权用户登陆，会跳到授权店铺的任务管理页面
 * @author lixiang
 *
 */
public class WechatLoginController {

	private static Logger log = LoggerFactory
			.getLogger(WechatLoginController.class);

	private static final String FRONTEND = "1";
	private static final String SHOPEND = "2";

	@Autowired
	private PersonInfoService personInfoService;

	@Autowired
	private WechatAuthService wechatAuthService;

	@RequestMapping(value = "/logincheck", method = { RequestMethod.GET })
	public String doGet(HttpServletRequest request, HttpServletResponse response) {
		log.debug("weixin login get...");
		String code = request.getParameter("code");
		String roleType = request.getParameter("state");
		log.debug("weixin login code:" + code);
		WechatAuth auth = null;
		WeiXinUser user = (WeiXinUser) request.getSession().getAttribute("weixinUser");
		String openId = (String) request.getSession().getAttribute("openId");
		if (null != code) {
			UserAccessToken token = (UserAccessToken) request.getSession().getAttribute("token");
			try {
				if (token==null){
					token = WeiXinUserUtil.getUserAccessToken(code);
					request.getSession().setAttribute("token",token);
				}
				log.debug("weixin login token:" + token.toString());
				String accessToken = token.getAccessToken();
				openId = token.getOpenId();
				if (user==null){
					user = WeiXinUserUtil.getUserInfo(accessToken, openId);
					request.getSession().setAttribute("weixinUser",user);
				}
				log.debug("weixin login user:" + user.toString());
				request.getSession().setAttribute("openId", openId);
				auth = wechatAuthService.getWechatAuthByOpenId(openId);
			} catch (IOException e) {
				log.error("error in getUserAccessToken or getUserInfo or findByOpenId: "
						+ e.toString());
				e.printStackTrace();
			}
		}
		if (auth==null){
			PersonInfo personInfo = WeiXinUserUtil.getPersonInfoFromRequest(user);
			auth = new WechatAuth();
			auth.setOpenId(openId);
			if (FRONTEND.equals(roleType)){
				personInfo.setUserType(1);
			}else if (SHOPEND.equals(roleType)){
				personInfo.setUserType(2);
			}
			auth.setPersonInfo(personInfo);
			WechatAuthExecution we = wechatAuthService.register(auth);
			if (we.getState() != WechatAuthStateEnum.SUCCESS.getState()) {
				return null;
			}else {
				personInfo = personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
				request.getSession().setAttribute("user",personInfo);
			}
		}

		PersonInfo personInfo = personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
		request.getSession().setAttribute("user",personInfo);

		log.debug("weixin login success.");
		log.debug("login role_type:" + roleType);
		//进入前端展示页面或者商家管理页面
		if (FRONTEND.equals(roleType)){
			return "frontend/index";
		}else if (SHOPEND.equals(roleType)){
			return "shop/shoplist";
		}else {
			return null;
		}

	}
}
