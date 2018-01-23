/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.sys.mvc.common.model.base;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.pointlion.sys.mvc.common.model.SysMenu;
import com.jfinal.plugin.activerecord.IBean;

@SuppressWarnings("serial")
public abstract class BaseSysMenu<M extends BaseSysMenu<M>> extends Model<M> implements IBean {
	private List<SysMenu> children;
	
	public List<SysMenu> getChildren() {
		return children;
	}
	public void setChildren(List<SysMenu> children) {
		this.children = children;
	}

	public void setId(java.lang.String id) {
		set("id", id);
	}

	public java.lang.String getId() {
		return get("id");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return get("name");
	}

	public void setSort(java.lang.Integer sort) {
		set("sort", sort);
	}

	public java.lang.Integer getSort() {
		return get("sort");
	}

	public void setParentId(java.lang.String parentId) {
		set("parent_id", parentId);
	}

	public java.lang.String getParentId() {
		return get("parent_id");
	}

	public void setIsparent(java.lang.String isparent) {
		set("isparent", isparent);
	}

	public java.lang.String getIsparent() {
		return get("isparent");
	}
	public void setDescription(java.lang.String description) {
		set("description", description);
	}

	public java.lang.String getDescription() {
		return get("description");
	}
	
	public void setIfShow(String ifShow){
		set("if_show",ifShow);
	}
	
	public String getIfShow(){
		return getStr("if_show");
	}
	
	public void setPermission(String permission){
		set("permission", permission);
	}
	
	public String getPermission(){
		return getStr("permission");
	}
	
	public void setIcon(String icon){
		set("icon", icon);
	}
	
	public String getIcon(){
		return getStr("icon");
	}
	
}
