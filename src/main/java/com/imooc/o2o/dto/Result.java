package com.imooc.o2o.dto;
/*
 * Created by wxn
 * 2018/8/24 13:24
 */

/**
 * 封装JSON对象，所有返回结果都使用它
 * @param <T>
 */
public class Result<T> {

	private boolean success;//是否成功标志

	private T data; //成功时返回的数据

	private String errMsg; //错误信息

	private Integer errorCode; //错误码

	public Result(){

	}

	//成功时的构造器
	public Result(boolean success,T data){
		this.success = success;
		this.data = data;
	}

	//错误时的构造器
	public Result(boolean success ,Integer errorCode , String errMsg){
		this.success = success;
		this.errMsg = errMsg;
		this.errorCode = errorCode;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
}
