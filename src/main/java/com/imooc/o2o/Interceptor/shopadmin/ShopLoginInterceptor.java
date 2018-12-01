package com.imooc.o2o.Interceptor.shopadmin;
/*
 * Created by wxn
 * 2018/11/1 10:03
 */

import com.imooc.o2o.entity.PersonInfo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 店家管理系统登录验证拦截器
 */
public class ShopLoginInterceptor extends HandlerInterceptorAdapter {
	/**
	 * 主要做事前拦截,即用户操作发生前,该写perHandle里的逻辑进行拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//从session中取出用户信息来
		Object userObj = request.getSession().getAttribute("user");
		if (userObj != null) {
			PersonInfo user = (PersonInfo)userObj;
			if (user.getUserId() > 0 && user.getEnableStatus() == 1 && user.getUserType() == 2){
				return true;
			}
		}
		//若不满足登录验证 则直接跳转到账号登录页面
		request.getSession().setAttribute("user",null);

		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<script>");
		out.println("window.open('"+request.getContextPath() + "/local/login?usertype=2','_self')");
		out.println("</script>");
		out.println("</html>");

		return false;
	}
}
