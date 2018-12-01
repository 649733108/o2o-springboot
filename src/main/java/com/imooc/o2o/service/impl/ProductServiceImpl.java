package com.imooc.o2o.service.impl;
/*
 * Created by wxn
 * 2018/8/26 22:02
 */


import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dao.ProductImgDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductImg;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exception.ProductOperationException;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageCalculator;
import com.imooc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;

	@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
		int count = productDao.queryProductCount(productCondition);

		ProductExecution pe = new ProductExecution();

		if (productList != null) {
			pe.setProductList(productList);
			pe.setCount(count);
			pe.setState(ProductStateEnum.SUCCESS.getState());
			pe.setStateInfo(ProductStateEnum.SUCCESS.getStateInfo());
		} else {
			pe.setState(ProductStateEnum.INNER_ERROR.getState());
			pe.setStateInfo(ProductStateEnum.INNER_ERROR.getStateInfo());
		}
		return pe;
	}


	/**
	 * 添加商品
	 * 1.处理缩略图 获取缩略图相对路径并赋值给product
	 * 2.往tb_product写入商品信息 获取productId
	 * 3.结合productId批量处理商品详情图
	 * 4.将商品详情图列表批量插入tb_product_img中
	 */
	@Override
	@Transactional
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws ProductOperationException {
		//空值判断
		if (product!=null && product.getShop()!=null && product.getShop().getShopId()!=null){
			//给商品设置默认属性
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			//默认为上架状态
			product.setEnableStatus(1);
			//若商品缩略图不为空则添加
			if (thumbnail!=null){
				addThumbnail(product,thumbnail);
			}
			try {
				//创建商品信息
				int effectNum = productDao.insertProduct(product);
				if (effectNum<=0)
					throw new ProductOperationException("创建商品失败");
			}catch (Exception e){
				throw new ProductOperationException("创建商品失败 : " + e.getMessage());
			}
			//若商品详情图不为空则添加
			if (productImgList!=null && productImgList.size()>0){
				addProductImgList(product,productImgList);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS,product);
		}else {
			//传参为空则返回空值错误信息
			return new ProductExecution(ProductStateEnum.EMPTY);
		}

	}

	@Override
	public Product getProductById(long productId) {
		return productDao.queryProductById(productId);
	}


	/**
	 * 添加商品
	 * 1.处理缩略图 若缩略图参数有值 则处理缩略图
	 * 	 若原先存在缩略图 则先删除再添加新图
	 * 	 之后获取缩略图路径并复制给product
	 * 2.若商品详情图有参数 则对详情图列表进行同样处理
	 * 3.将tb_product_img下面的该商品原先详情图记录全部删除
	 * 4.更新tb_product信息
	 */
	@Override
	@Transactional
	public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException {
		//空值判断
		if (product!=null && product.getShop()!=null && product.getShop().getShopId()!=null){
			//给商品设置默认属性
			product.setLastEditTime(new Date());
			//若商品缩略图不为空且原有缩略图不为空 则删除原有图并添加
			if (thumbnail!=null){
				//先获取一遍原有信息
				Product tempProduct = productDao.queryProductById(product.getProductId());
				if (tempProduct.getImgAddr()!=null){
					ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
				}
				addThumbnail(product,thumbnail);
			}
			//如果有新的详情图,则删除原先的 再添加
			if (productImgHolderList!=null && productImgHolderList.size()>0){
				deleteProductImgList(product.getProductId());
				addProductImgList(product,productImgHolderList);
			}
			try {
				//更新商品信息
				int effectNum = productDao.updateProduct(product);
				if (effectNum<=0)
					throw new ProductOperationException("更新商品失败");
			}catch (Exception e){
				throw new ProductOperationException("创建商品失败 : " + e.getMessage());
			}
			return new ProductExecution(ProductStateEnum.SUCCESS,product);
		}else {
			//传参为空则返回空值错误信息
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	/**
	 * 删除某个商品下的全部详情图
	 */
	private void deleteProductImgList(Long productId) {
		//根据productId获取原来的图片
		List<ProductImg>productImgList = productImgDao.queryProductImgList(productId);
		//删除原来的图片
		for (ProductImg productImg : productImgList) {
			ImageUtil.deleteFileOrPath(productImg.getImgAddr());
		}
		//删除数据库里原有图片的信息
		productImgDao.deleteProductImgByProductId(productId);
	}

	private void addThumbnail(Product product, ImageHolder thumbnail) {
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail,dest);
		product.setImgAddr(thumbnailAddr);
	}

	/**
	 * 批量添加图片
	 */
	private void addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
		//获取图片存储路径 这里直接存放到相应店铺的文件夹地下
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		List<ProductImg> productImgList = new ArrayList<>();
		//遍历图片一次去处理,并添加进productImg实体类里
		for (ImageHolder productImgHolder : productImgHolderList) {
			String imgAddr = ImageUtil.generateNormalImg(productImgHolder,dest);
			ProductImg productImg = new ProductImg();
			productImg.setImgAddr(imgAddr);
			productImg.setProductId(product.getProductId());
			productImg.setCreateTime(new Date());
			productImgList.add(productImg);
		}
		//如果确实有图片需要添加,就执行批量添加操作
		if (productImgList.size()>0){
			try {
				int effectNum = productImgDao.batchInsertProductImg(productImgList);
				if (effectNum<=0){
					throw new ProductOperationException("创建商品详情图片失败");
				}
			}catch (Exception e){
				throw new ProductOperationException("创建商品详情图片失败" + e.getMessage());
			}
		}
	}
}
