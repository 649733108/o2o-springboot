package com.imooc.o2o.web.shopadmin;
/*
 * Created by wxn
 * 2018/11/29 18:05
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.imooc.o2o.dto.ShopAuthMapExecution;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopAuthMap;
import com.imooc.o2o.entity.WechatAuth;
import com.imooc.o2o.enums.ShopAuthMapStateEnum;
import com.imooc.o2o.service.PersonInfoService;
import com.imooc.o2o.service.ShopAuthMapService;
import com.imooc.o2o.service.WechatAuthService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import com.imooc.o2o.util.baidu.ShortNetAddress;
import com.imooc.o2o.util.weixin.WeiXinUserUtil;
import com.imooc.o2o.util.weixin.WeixinUtil;
import com.imooc.o2o.util.weixin.message.pojo.UserAccessToken;
import com.imooc.o2o.util.weixin.message.req.WechatInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shopadmin")
public class ShopAuthManagementController {

	@Autowired
	ShopAuthMapService shopAuthMapService;
	@Autowired
	WechatAuthService wechatAuthService;
	@Autowired
	PersonInfoService personInfoService;

	//微信获取用户信息的前缀 中间 后缀 和url
	private static String urlPrefix;
	private static String urlMiddle;
	private static String urlSuffix;
	private static String authUrl;

	@Value("${wechat.prefix}")
	public void setUrlPrefix(String urlPrefix) {
		ShopAuthManagementController.urlPrefix = urlPrefix;
	}

	@Value("${wechat.middle}")
	public void setUrlMiddle(String urlMiddle) {
		ShopAuthManagementController.urlMiddle = urlMiddle;
	}
	@Value("${wechat.suffix}")
	public void setUrlSuffix(String urlSuffix) {
		ShopAuthManagementController.urlSuffix = urlSuffix;
	}

	@Value("${wechat.auth.url}")
	public void setAuthUrl(String authUrl) {
		ShopAuthManagementController.authUrl = authUrl;
	}

	@RequestMapping(value = "/listshopauthmapsbyshop",method = RequestMethod.GET)
	private Map<String,Object>listShopAuthMapsByShop(HttpServletRequest request){
		Map<String ,Object> modelMap = new HashMap<>();
		int pageIndex = HttpServletRequestUtil.getInt(request,"pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");

		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");

		if (pageIndex>-1 && pageSize>-1 && currentShop!=null &&currentShop.getShopId()!=null){
			ShopAuthMapExecution se = shopAuthMapService.listShopAuthMapByShopId(currentShop.getShopId(),pageIndex,pageSize);
			modelMap.put("success",true);
			modelMap.put("count",se.getCount());
			modelMap.put("shopAuthMapList",se.getShopAuthMapList());
		}else {
			modelMap.put("success",false);
			modelMap.put("errMsg","empty page message or shopId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/getshopauthmapbyid",method = RequestMethod.GET)
	private Map<String ,Object>getShopAuthById(@RequestParam Long shopAuthId){
		Map<String ,Object> modelMap = new HashMap<>();
		if (shopAuthId!=null && shopAuthId>-1){
			ShopAuthMap shopAuthMap = shopAuthMapService.getShopAuthMapById(shopAuthId);
			modelMap.put("success",true);
			modelMap.put("shopAuthMap",shopAuthMap);
		}else {
			modelMap.put("success",false);
			modelMap.put("errMsg","error shopAuthId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/modifyshopauthmap",method = RequestMethod.POST)
	private Map<String ,Object>modifyShopAuthMap(String shopAuthMapStr,HttpServletRequest request){
		Map<String ,Object> modelMap = new HashMap<>();

		//授权编辑时有验证码操作
		boolean statusChange = HttpServletRequestUtil.getBoolean(request,"statusChange");
		if (!statusChange && !CodeUtil.checkVerifyCode(request)){
			modelMap.put("success",false);
			modelMap.put("errMsg","验证码错误");
			return modelMap;
		}
		ObjectMapper mapper = new ObjectMapper();
		ShopAuthMap shopAuthMap = null;

		try{
			shopAuthMap = mapper.readValue(shopAuthMapStr,ShopAuthMap.class);
		}catch (Exception e){
			modelMap.put("success",false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
		if (shopAuthMap!=null && shopAuthMap.getShopAuthId()!=null){
			try {
				if (!checkPermission(shopAuthMap.getShopAuthId())){
					modelMap.put("success",false);
					modelMap.put("errMsg","无法对店家本身操作，已是最高权限");
					return modelMap;
				}
				ShopAuthMapExecution se = shopAuthMapService.modifyShopAuthMap(shopAuthMap);
				if (se.getState()== ShopAuthMapStateEnum.SUCCESS.getState()){
					modelMap.put("success",true);
				}else {
					modelMap.put("success",false);
					modelMap.put("errMsg",se.getStateInfo());
				}
			}catch (RuntimeException e){
				modelMap.put("success",false);
				modelMap.put("errMsg",e.getMessage());
				return modelMap;
			}
		}else {
			modelMap.put("success",false);
			modelMap.put("errMsg","请输入要修改的授权信息");
		}
		return modelMap;
	}

	private boolean checkPermission(Long shopAuthId) {
		ShopAuthMap grantedPerson = shopAuthMapService.getShopAuthMapById(shopAuthId);
		if (grantedPerson.getTitleFlag()==0){
			return false;
		}else {
			return true;
		}
	}


	/**
	 * 生成二维码
	 */
	@RequestMapping(value = "/generateqrcode4shopauth" , method = RequestMethod.GET)
	@ResponseBody
	private void generateQRCode4ShopAuth(HttpServletRequest request, HttpServletResponse response){
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		if (currentShop!=null && currentShop.getShopId()!=null){
			long timeStamp = System.currentTimeMillis();
			//将店铺id和时间戳传入content，赋值到state中。这样微信获取到这些信息后会回传到授权信息的添加方法
			//加上aaa是为了一会在添加信息的方法里替换这些信息使用
			String content = "{aaashopIdaaa:" + currentShop.getShopId() + ",aaacreateTimeaaa:"+timeStamp+"}";
			try {
				//将content的信息先进行base64编码以避免特殊字符串造成的干扰 之后拼接目标url
				String longUrl = urlPrefix+authUrl+urlMiddle+ URLEncoder.encode(content,"UTF-8")+urlSuffix;
				//将目标url转换成短url
				String shortUrl = ShortNetAddress.getShortURL(longUrl);
				//调用二维码生成的方法
				BitMatrix qRcodeImg = CodeUtil.generateQRCodeStream(shortUrl,response);
				//将二维码以图片流的形式输出到前端
				MatrixToImageWriter.writeToStream(qRcodeImg,"png",response.getOutputStream());
			}catch (IOException e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据微信回传回来的参数添加店铺授权信息
	 */
	@RequestMapping(value = "/addshopauthmap",method = RequestMethod.GET)
	private ModelAndView addShopAuthMap(HttpServletRequest request,HttpServletResponse response)throws IOException{
		//从request里获取微信用户的信息
		WechatAuth auth = getEmployeeInfo(request);
		if (auth!=null){
			//根据userId获取用户信息
			PersonInfo user = personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
			request.getSession().setAttribute("user",user);
			//解析微信回传来的自定义参数state 由于之前进行了编码 这里进行解码
			String qrCodeinfo = URLDecoder.decode(HttpServletRequestUtil.getString(request, "state"), "UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			WechatInfo wechatInfo = null;
			try {
				//将解码后的内容用aaa去替换掉之前生成二维码时候加入的aaa前缀 转换成WechatInfo实体类
				wechatInfo = mapper.readValue(qrCodeinfo.replace("aaa","\""),WechatInfo.class);
			}catch (Exception e){
				return new ModelAndView("shop/operationfail");
			}
			//校验二维码是否过期
			if (!checkQRCodeInfo(wechatInfo)){
				return new ModelAndView("shop/operationfail");
			}

			//去重校验
			ShopAuthMapExecution allMapList = shopAuthMapService.listShopAuthMapByShopId(wechatInfo.getShopId(),1,999);
			List<ShopAuthMap>shopAuthMapList = allMapList.getShopAuthMapList();
			for (ShopAuthMap shopAuthMap : shopAuthMapList) {
				if (shopAuthMap.getEmployee().getUserId().equals(user.getUserId()))
					return new ModelAndView("shop/operationfail");
			}

			try {
				ShopAuthMap shopAuthMap = new ShopAuthMap();
				Shop shop = new Shop();
				shop.setShopId(wechatInfo.getShopId());
				shopAuthMap.setShop(shop);
				shopAuthMap.setEmployee(user);
				shopAuthMap.setTitle("员工");
				shopAuthMap.setTitleFlag(1);
				ShopAuthMapExecution se = shopAuthMapService.addShopAuthMap(shopAuthMap);
				if (se.getState()==ShopAuthMapStateEnum.SUCCESS.getState()){
					return new ModelAndView("shop/operationsuccess") ;
				}else {
					return new ModelAndView("shop/operationfail");
				}
			}catch (RuntimeException e){
				return new ModelAndView("shop/operationfail");
			}
		}
		return new ModelAndView("shop/operationfail");
	}

	private boolean checkQRCodeInfo(WechatInfo wechatInfo) {
		if (wechatInfo!=null && wechatInfo.getShopId()!=null &&wechatInfo.getCreateTime()!=null){
			long nowTime = System.currentTimeMillis();
			if ((nowTime-wechatInfo.getCreateTime())<=600000){
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}

	private WechatAuth getEmployeeInfo(HttpServletRequest request) {

		 String code = request.getParameter("code");
		 WechatAuth auth = null;
		 if (null!=code){
			 UserAccessToken token;
			 try {
			 	token = WeiXinUserUtil.getUserAccessToken(code);
			 	String openId = token.getOpenId();
			 	request.getSession().setAttribute("openId",openId);
			 	auth = wechatAuthService.getWechatAuthByOpenId(openId);
			 }catch (IOException e){
			 	e.printStackTrace();
			 }
		 }
		 return auth;
	}
}

