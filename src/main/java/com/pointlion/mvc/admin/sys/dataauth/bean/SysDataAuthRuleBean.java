package com.pointlion.mvc.admin.sys.dataauth.bean;

public class SysDataAuthRuleBean {
	private String field;//字段
	private String operation;//大于，等于，小鱼，eq，不等于
	private String value;//值
	private String type;//int String
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
