package com.imooc.o2o.exception;
/*
 * Created by wxn
 * 2018/8/27 0:14
 */


public class WechatAuthException extends RuntimeException {


	private static final long serialVersionUID = 365495560514635947L;

	public WechatAuthException(String msg) {
		super(msg);
	}
}
