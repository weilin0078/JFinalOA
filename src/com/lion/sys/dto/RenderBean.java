package com.lion.sys.dto;

import java.io.Serializable;

/**
 * Render返回JSON数据封装
 * @author 董华健
 */
public class RenderBean implements Serializable{
	
	private static final long serialVersionUID = -1126196958137979710L;

	/**
	 * 状态：成功success、失败error
	 */
	private Boolean success ;
	
	/**
	 * 状态码
	 */
	private String code = "";

	/**
	 * 描述
	 */
	private String message = "";
	
	/**
	 * 正常情况下返回的数据
	 */
	private Object data;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}



	
}
