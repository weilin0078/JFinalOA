/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.sys.mvc.common.dto;

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
	
	/***
	 * 总数-分页
	 */
	private int total;
	/***
	 * 数据 - 分页
	 */
	private Object rows;
	
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

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Object getRows() {
		return rows;
	}

	public void setRows(Object rows) {
		this.rows = rows;
	}
	
}
