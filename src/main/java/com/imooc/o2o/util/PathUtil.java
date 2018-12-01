package com.imooc.o2o.util;
/*
 * Created by wxn
 * 2018/8/21 10:34
 */


public class PathUtil {

	private static String separator = System.clearProperty("file.separator");

	/**
	 * 获取图片存储路径
	 * @return basePath
	 */
	public static String getImgBasePath(){

		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")){
			basePath = "C:\\Users\\64973\\Pictures\\o2o\\";
		}else {
			basePath = "/usr/o2o";
		}
		basePath = basePath.replace("/" , separator);
		return basePath;
	}

	public static String getShopImagePath(long shopId){
		String imagePath = "/upload/images/item/shop/" + shopId + "/";
		return imagePath.replace("/",separator);
	}
}
