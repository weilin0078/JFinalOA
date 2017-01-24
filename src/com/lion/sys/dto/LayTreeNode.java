/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.lion.sys.dto;

import java.util.List;

public class LayTreeNode {
	private String id;//id
	private String name;//名称
	private String alias;//别名
	private List<LayTreeNode> children;//孩子
	private String href;//跳转地址
	private Boolean spread;//是否展开
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
	
}
