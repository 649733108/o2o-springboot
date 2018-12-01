package com.imooc.o2o.web.shopadmin;
/*
 * Created by wxn
 * 2018/8/21 15:17
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shopadmin" , method = RequestMethod.GET)
public class ShopAdminController {

	@RequestMapping(value = "/shopoperation")
	public String shopOperation(){
		return "shop/shopoperation";
	}

	@RequestMapping(value = "/shoplist")
	public String shopList(){
		return "shop/shoplist";
	}

	@RequestMapping(value = "/shopmanagement")
	public String shopManagement(){
		return "shop/shopmanagement";
	}

	@RequestMapping(value = "/productcategorymanagement")
	public String productCategoryManagement(){
		return "shop/productcategorymanagement";
	}

	@RequestMapping(value = "/productmanagement")
	public String productManagement(){
		return "shop/productmanagement";
	}

	@RequestMapping(value = "/productedit")
	public String productEdit(){
		//转发至商品添加、编辑页面
		return "shop/productedit";
	}
	@RequestMapping(value = "/shopauthmanagement")
	public String shopAuthManagement(){
		return "shop/shopauthmanagement";
	}
	@RequestMapping(value = "/shopauthedit")
	public String shopAuthEdit(){
		return "shop/shopauthedit";
	}
	@RequestMapping(value = "/operationsuccess")
	public String operationSuccess(){
		return "shop/operationsuccess";
	}
	@RequestMapping(value = "/operationfail")
	public String operationFail(){
		return "shop/operationfail";
	}
	@RequestMapping(value = "/productbuycheck")
	public String productBuyCheck(){
		return "shop/productbuycheck";
	}
}
