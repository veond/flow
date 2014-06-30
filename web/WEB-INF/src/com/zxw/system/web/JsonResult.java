package com.zxw.system.web;

public class JsonResult {

	public JsonResult() {
		super();
	}

	public JsonResult(boolean isSuccess, String msg, Object data) {
		super();
		this.isSuccess = isSuccess;
		this.msg = msg;
		this.data = data;
	}
	
	public JsonResult(boolean isSuccess, int code, String msg, Object data) {
		super();
		this.isSuccess = isSuccess;
		this.msg = msg;
		this.data = data;
		this.code = code;
	}
	
	public JsonResult(String msg) {
		super();
		this.msg = msg;
	}
	
	public JsonResult(boolean isSuccess, String msg) {
		super();
		this.isSuccess = isSuccess;
		this.msg = msg;
	}

	/**
	 * 是否成功
	 */
	private boolean isSuccess = false;

	/**
	 * 返回信息
	 */
	private String msg = "未知错误信息";

	/**
	 * 返回结果
	 */
	private Object data = null;
	
	/**
	 * 状态码
	 */
	private int code = -1;

	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
}
