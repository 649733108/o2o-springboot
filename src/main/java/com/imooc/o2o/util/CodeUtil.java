package com.imooc.o2o.util;
/*
 * Created by wxn
 * 2018/8/21 18:18
 */


import com.google.code.kaptcha.Constants;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CodeUtil {

	/**
	 * 检查验证码
	 * @param request
	 * @return
	 */
	public static boolean checkVerifyCode(HttpServletRequest request) {
		String verifyCodeExpected = (String) request.getSession().getAttribute(
				Constants.KAPTCHA_SESSION_KEY);
		String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");
		if (verifyCodeActual == null || !verifyCodeActual.equals(verifyCodeExpected)) {
			return false;
		}
		return true;
	}

	/**
	 * 生成二维码的图片流
	 */
	public static BitMatrix generateQRCodeStream(String content, HttpServletResponse response){

		//给响应添加头部信息 主要告诉浏览器返回的是图片流
		response.setHeader("Cache-Control","no-store");
		response.setHeader("Pragma","no-cache");
		response.setDateHeader("Expires",0);
		response.setContentType("image/png");

		//设置图片的文字编码以及内边框距
		Map<EncodeHintType,Object>hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
		hints.put(EncodeHintType.MARGIN,0);
		BitMatrix bitMatrix;
		try {
			//参数顺序:编码内容 类型 图片宽度 图片高度 设置参数
			bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE,300,300,hints);
		}catch (WriterException e){
			e.printStackTrace();
			return null;
		}
		return bitMatrix;
	}

}
