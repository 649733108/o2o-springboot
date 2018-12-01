package com.imooc.o2o.web.shopadmin;
/*
 * Created by wxn
 * 2018/12/1 23:53
 */

import com.imooc.o2o.dto.UserProductMapExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.UserProductMap;
import com.imooc.o2o.service.ProductSellDailyService;
import com.imooc.o2o.service.UserProductMapService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/shopadmin")
@RestController
public class UserProductManagementController {

	@Autowired
	private UserProductMapService userProductMapService;
	@Autowired
	private ProductSellDailyService productSellDailyService;

	@RequestMapping(value = "/listuserproductmapsbyshop",method = RequestMethod.GET)
	private Map<String,Object> listUserProductMapsByShop(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<>();
		int pageIndex = HttpServletRequestUtil.getInt(request,"pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		if (pageIndex>-1 && pageSize>-1 && currentShop!=null && currentShop.getShopId()!=null){
			UserProductMap userProductMapCondition = new UserProductMap();
			userProductMapCondition.setShop(currentShop);
			String productName = HttpServletRequestUtil.getString(request,"productName");
			if (productName!=null){
				Product product = new Product();
				product.setProductName(productName);
				userProductMapCondition.setProduct(product);
			}
			UserProductMapExecution ue = userProductMapService.listUserProductMap(userProductMapCondition,pageIndex,pageSize);
			modelMap.put("success",true);
			modelMap.put("userProductMapList",ue.getUserProductMapList());
			modelMap.put("count",ue.getCount());
		}else {
			modelMap.put("success",false);
			modelMap.put("errMsg","empty pageSize or pageIndex or ShopId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/listproductselldailyinfobyshop",method = RequestMethod.GET)
	private Map<String ,Object> listProductSellDailyInfoByShop(){
		Map<String,Object>modelMap = new HashMap<>();



		return modelMap;


	}

}
