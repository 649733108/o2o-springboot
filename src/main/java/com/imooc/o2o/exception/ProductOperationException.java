package com.imooc.o2o.exception;
/*
 * Created by wxn
 * 2018/8/27 0:14
 */


public class ProductOperationException extends RuntimeException {
	private static final long serialVersionUID = -552463239137877992L;

	public ProductOperationException(String msg) {
		super(msg);
	}
}
