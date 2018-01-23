/**
 * 
 */
package com.pointlion.sys.mvc.common.dto;

import java.util.List;

/**
 * @author Lion
 * @date 2017年2月16日 下午3:42:41
 * @qq 439635374
 */
public class LayTreeNode {
	private String id;//id
	private String name;//名称
	private String alias;//别名
	private List<LayTreeNode> children;//孩子
	private String href;//跳转地址
	private Boolean spread;//是否展开
	private Boolean checked;
	private String checkboxValue;
	private String datatype;
	private String checkboxStyle;
	
	
	public String getCheckboxStyle() {
		return checkboxStyle;
	}
	public void setCheckboxStyle(String checkboxStyle) {
		this.checkboxStyle = checkboxStyle;
	}
	public String getCheckboxValue() {
		return checkboxValue;
	}
	public void setCheckboxValue(String checkboxValue) {
		this.checkboxValue = checkboxValue;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public List<LayTreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<LayTreeNode> children) {
		this.children = children;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public Boolean getSpread() {
		return spread;
	}
	public void setSpread(Boolean spread) {
		this.spread = spread;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
}
