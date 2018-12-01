package com.imooc.o2o.web.shopadmin;
/*
 * Created by wxn
 * 2018/8/21 13:14
 */


import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exception.ShopOperationException;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private ProductCategoryService productCategoryService;

	@RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopById(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if (shopId > -1) {
			try {
				Shop shop = shopService.getByShopId(shopId);
				if (shop == null) {
					modelMap.put("success", false);
					modelMap.put("errMsg", "Shop doesn't exist");
					return modelMap;
				}
				List<Area> areaList = areaService.getAreaList();
				modelMap.put("shop", shop);
				modelMap.put("areaList", areaList);
				modelMap.put("success", true);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "shopId is empty!");
		}
		return modelMap;
	}

	@RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopInitInfo() {
		Map<String, Object> modelMap = new HashMap<>();
		List<ShopCategory> shopCategoryList = new ArrayList<>();
		List<Area> areaList = new ArrayList<>();

		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}

	@RequestMapping(value = "/registershop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<>();

		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}

		//1.接收并转化相应的参数，包括店铺信息以及图片信息
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}

		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}

		//2.注册店铺
		if (shop != null && shopImg != null) {
			PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
			shop.setOwner(owner);
			shop.setPriority(0);

			ShopExecution se = null;
			try {
				ImageHolder shopImgHolder = new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
				se = shopService.addShop(shop, shopImgHolder);
				if (se.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
					//该用户可以操作的店铺列表
					@SuppressWarnings("unchecked")
					List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
					if (shopList == null || shopList.size() == 0) {
						shopList = new ArrayList<>();
					}
					shopList.add(se.getShop());
					request.getSession().setAttribute("shopList", shopList);

				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (IOException | ShopOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入用户信息");
			return modelMap;
		}

	}


	@RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyShop(HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<>();

		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}

		//1.接收并转化相应的参数，包括店铺信息以及图片信息
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}

		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		}

		//2.更新店铺信息
		if (shop != null && shop.getShopId() != null) {

			ShopExecution se = null;
			try {
				ImageHolder shopImgHolder = new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
				if (shopImg == null) {
					se = shopService.modifyShop(shop, null);
				} else {
					se = shopService.modifyShop(shop, shopImgHolder);
				}
				if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (IOException | ShopOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺id");
			return modelMap;
		}

	}

	/**
	 * 查询店铺列表
	 */
	@RequestMapping(value = "/getshoplist" ,method = RequestMethod.GET)
	@ResponseBody
	private Map<String ,Object> getShopList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();

		PersonInfo user;

		user = (PersonInfo) request.getSession().getAttribute("user");
		if (user==null){
			modelMap.put("success",false);
			modelMap.put("errMsg","请登录");
			return modelMap;
		}
		try {
			Shop shopCondition = new Shop();
			shopCondition.setOwner(user);
			ShopExecution se = shopService.getShopList(shopCondition,1,100);
			modelMap.put("success",true);
			modelMap.put("shopList",se.getShopList());
			//列出店铺成功后,将店铺放入session中作为权限检验依据,即该账号只能操作他自己的店铺
			request.getSession().setAttribute("shopList",se.getShopList());
			modelMap.put("user",user);
		}catch (Exception e){
			modelMap.put("success",false);
			modelMap.put("errMsg",e.getMessage());
		}
		return modelMap;
	}

	@RequestMapping(value = "/getshopmanagementinfo",method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getShopManagementInfo(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();

		long shopId = HttpServletRequestUtil.getLong(request,"shopId");
		if (shopId<0){
			Object currentObj = request.getSession().getAttribute("currentShop");
			if (currentObj==null){
				modelMap.put("redirect" , true);
				modelMap.put("url" , "/shopadmin/shoplist");
			}else {
				Shop shop = (Shop) currentObj;
				modelMap.put("redirect" ,false);
				modelMap.put("shopId" , shop.getShopId());
			}
		}else {
			Shop currentShop = new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop",currentShop);
			modelMap.put("redirect",false);
		}
		return modelMap;
	}



	/**
	 * 将输入流转化成File
	 */
//	private static void inputStreamToFile(InputStream ins , File file){
//		FileOutputStream os = null;
//		try {
//			os = new FileOutputStream(file);
//			int bytesRead = 0;
//			byte[] buffer = new byte[1024];
//			while ((bytesRead = ins.read(buffer))!=-1){
//				os.write(buffer,0,bytesRead);
//			}
//		}catch (Exception e){
//			throw new RuntimeException("调用inputStreamToFile产生异常" + e.getMessage());
//		}
//		finally {
//			try {
//				if (os!=null){
//					os.close();
//				}
//				if (ins!=null){
//					ins.close();
//				}
//			}catch (IOException e){
//				throw new RuntimeException("inputStreamToFile关闭io产生异常" + e.getMessage());
//			}
//		}
//	}
}
