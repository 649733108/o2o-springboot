package com.imooc.o2o.web.frontend;
/*
 * Created by wxn
 * 2018/9/19 17:44
 */

import com.imooc.o2o.entity.Product;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.service.ShopService;
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
@RequestMapping("/frontend")
public class ProductDetailController {

	@Autowired
	private ShopService shopService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;


	/**
	 * 根据productId查询商铺详细信息
	 */
	@RequestMapping(value = "/getproductdetail" ,method = RequestMethod.GET)
	@ResponseBody
	private Map<String ,Object>getProductDetail(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();

		long productId = HttpServletRequestUtil.getLong(request,"productId");
		if (productId>0){
			Product product = productService.getProductById(productId);

			if (product!=null) {
				modelMap.put("product", product);
				modelMap.put("success", true);
			}
			else {
				modelMap.put("success",false);
				modelMap.put("errMsg","没有这个商品");
			}
		}else {
			modelMap.put("success",false);
			modelMap.put("errMsg","请输入productId");
		}
		return modelMap;
	}


}
