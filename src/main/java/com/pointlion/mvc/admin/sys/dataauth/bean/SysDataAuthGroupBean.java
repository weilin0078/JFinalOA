package com.pointlion.mvc.admin.sys.dataauth.bean;

import java.util.List;

public class SysDataAuthGroupBean {
	private List<SysDataAuthGroupBean> groups;
	private List<SysDataAuthRuleBean> rules;
	private String operation;
	public List<SysDataAuthGroupBean> getGroups() {
		return groups;
	}
	public void setGroups(List<SysDataAuthGroupBean> groups) {
		this.groups = groups;
	}
	public List<SysDataAuthRuleBean> getRules() {
		return rules;
	}
	public void setRules(List<SysDataAuthRuleBean> rules) {
		this.rules = rules;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
}
