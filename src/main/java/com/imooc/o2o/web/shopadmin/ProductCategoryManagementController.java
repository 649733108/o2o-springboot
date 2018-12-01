package com.imooc.o2o.web.shopadmin;
/*
 * Created by wxn
 * 2018/8/24 13:28
 */

import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.dto.Result;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductCategoryStateEnum;
import com.imooc.o2o.exception.ProductCategoryOpeationException;
import com.imooc.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {

	@Autowired
	private ProductCategoryService productCategoryService;

	@RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
	@ResponseBody
	private Result<List<ProductCategory>> getProductCategoryByShopId(HttpServletRequest request) {

		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

//		currentShop = new Shop();
//		currentShop.setShopId(2L);

		if (currentShop!=null &&currentShop.getShopId()>0) {
			List<ProductCategory>productCategoryList = productCategoryService.getProductCategoryByShopId(currentShop.getShopId());
			return new Result<>(true, productCategoryList);
		} else {
			ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
			return new Result<>(false, ps.getState(), ps.getStateInfo());
		}

	}

	@RequestMapping(value = "/addproductcategories", method = RequestMethod.POST)
	@ResponseBody
	private Map<String ,Object> addProductCategories(@RequestBody List<ProductCategory>productCategoryList, HttpServletRequest request){
		Map<String,Object>modelMap = new HashMap<>();
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		if (currentShop!=null){
			for (ProductCategory productCategory : productCategoryList) {
				productCategory.setShopId(currentShop.getShopId());
				productCategory.setCreateTime(new Date());
			}
			if (productCategoryList!=null && productCategoryList.size()>0){
				try {
					ProductCategoryExecution pe = productCategoryService.batchAddProductCategory(productCategoryList);
					if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()){
						modelMap.put("success",true);
					}else {
						modelMap.put("success",false);
						modelMap.put("errMsg",pe.getStateInfo());
					}
				}catch (ProductCategoryOpeationException e){
					modelMap.put("success",false);
					modelMap.put("errMsg",e.toString());
				}
			}else {
				modelMap.put("success",false);
				modelMap.put("errMsg","shopId Error");
			}
		}else {
			modelMap.put("success",false);
			modelMap.put("errMsg","请添加完整的信息");
		}
		return modelMap;


	}

	@RequestMapping(value = "/removeproductcategory" ,method = RequestMethod.POST)
	@ResponseBody
	private Map<String ,Object> removeProductCategory(Long productCategoryId , HttpServletRequest request){
		Map<String,Object>modelMap = new HashMap<>();
		if (productCategoryId!=null &&productCategoryId>0){
			try {
				Shop shop = (Shop)request.getSession().getAttribute("currentShop");
				ProductCategoryExecution pe = productCategoryService.deleteProductCategory(productCategoryId,shop.getShopId());
				if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()){
					modelMap.put("success",true);
				}else {
					modelMap.put("success",false);
					modelMap.put("errMsg",pe.getStateInfo());
				}
			}catch (RuntimeException e){
				modelMap.put("success",false);
				modelMap.put("errMsg",e.toString());
			}
		}else {
			modelMap.put("success",false);
			modelMap.put("errMsg","请至少选择一个商品类别");
		}
		return modelMap;
	}

}
