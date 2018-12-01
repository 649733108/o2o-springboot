package com.imooc.o2o.util;
/*
 * Created by wxn
 * 2018/8/21 10:15
 */


import com.imooc.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {

	private static String basePath = PathUtil.getImgBasePath();
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();

	/**
	 * 生成缩略图
	 */
	public static String generateThumbnail(ImageHolder imageHolder, String targetAddr){

		String realFileName = getRandomFileName();
		String extension = getFileExtension(imageHolder.getImageName());
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension ;
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(imageHolder.getImage()).size(200,200)
			.watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath + "/watermark.jpg")),0.25f)
			.outputQuality(0.8f).toFile(dest);
		}catch (IOException e){
			e.printStackTrace();
		}
		return relativeAddr;
	}

	/**
	 * 处理详情图
	 */
	public static String generateNormalImg(ImageHolder imageHolder, String targetAddr) {
		String realFileName = getRandomFileName();
		String extension = getFileExtension(imageHolder.getImageName());
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension ;
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(imageHolder.getImage()).size(337,640)
					.watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath + "/watermark.jpg")),0.25f)
					.outputQuality(0.9f).toFile(dest);
		}catch (IOException e){
			e.printStackTrace();
		}
		return relativeAddr;
	}

	/**
	 * 生成随机文件名，当前年月日小时分钟秒+五位随机数
	 * @return
	 */
	public static String getRandomFileName(){

		//获取五位随机数
		int rannum = r.nextInt(89999) + 10000;
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr + rannum;
	}

	/**
	 * 创建目标路径所涉及到的目录
	 */
	private static void makeDirPath(String targetAddr){
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()){
			dirPath.mkdirs();
		}
	}

	/**
	 * 获取输入文件流的扩展名
	 */
	private static String getFileExtension(String fileName){
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 删除文件
	 * @param storePath 文件或目录的路径
	 * 如果是文件路径，则删除该文件
	 * 如果是目录路径，则删除该目录下所有文件
	 */
	public static void deleteFileOrPath(String storePath){
		File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
		if (fileOrPath.exists()){
			if (fileOrPath.isDirectory()){
				//如果是目录
				File[] files = fileOrPath.listFiles();
				for (int i = 0; i < files.length; i++) {
					files[i].delete();
				}
			}
			fileOrPath.delete();
		}
	}


	public static void main(String args[]) throws IOException {

		Thumbnails.of(new File("C:/Users/WuXinnan/Pictures/Saved Pictures/52.png"))
				.size(200, 200).watermark(Positions.BOTTOM_RIGHT,
				ImageIO.read(new File(basePath + "watermark.jpg"))
				, 0.25f).outputQuality(0.8f)
				.toFile("C:/Users/WuXinnan/Pictures/Saved Pictures/52new.png");
	}


}
