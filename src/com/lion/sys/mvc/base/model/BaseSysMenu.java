/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.lion.sys.mvc.base.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;
import com.lion.sys.mvc.menu.SysMenu;

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

	public void setSort(java.lang.String sort) {
		set("sort", sort);
	}

	public java.lang.String getSort() {
		return get("sort");
	}

	public void setOperatorid(java.lang.String operatorid) {
		set("operatorid", operatorid);
	}

	public java.lang.String getOperatorid() {
		return get("operatorid");
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

}
