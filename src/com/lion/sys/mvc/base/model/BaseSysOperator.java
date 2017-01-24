/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.lion.sys.mvc.base.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

@SuppressWarnings("serial")
public abstract class BaseSysOperator<M extends BaseSysOperator<M>> extends Model<M> implements IBean {

	public void setIds(java.lang.String ids) {
		set("ids", ids);
	}

	public java.lang.String getIds() {
		return get("ids");
	}

	public void setVersion(java.lang.Long version) {
		set("version", version);
	}

	public java.lang.Long getVersion() {
		return get("version");
	}

	public void setDescription(java.lang.String description) {
		set("description", description);
	}

	public java.lang.String getDescription() {
		return get("description");
	}

	public void setNames(java.lang.String names) {
		set("names", names);
	}

	public java.lang.String getNames() {
		return get("names");
	}

	public void setOnemany(java.lang.String onemany) {
		set("onemany", onemany);
	}

	public java.lang.String getOnemany() {
		return get("onemany");
	}

	public void setReturnparamkeys(java.lang.String returnparamkeys) {
		set("returnparamkeys", returnparamkeys);
	}

	public java.lang.String getReturnparamkeys() {
		return get("returnparamkeys");
	}

	public void setReturnurl(java.lang.String returnurl) {
		set("returnurl", returnurl);
	}

	public java.lang.String getReturnurl() {
		return get("returnurl");
	}

	public void setRowfilter(java.lang.String rowfilter) {
		set("rowfilter", rowfilter);
	}

	public java.lang.String getRowfilter() {
		return get("rowfilter");
	}

	public void setUrl(java.lang.String url) {
		set("url", url);
	}

	public java.lang.String getUrl() {
		return get("url");
	}

	public void setModuleids(java.lang.String moduleids) {
		set("moduleids", moduleids);
	}

	public java.lang.String getModuleids() {
		return get("moduleids");
	}

	public void setSplitpage(java.lang.String splitpage) {
		set("splitpage", splitpage);
	}

	public java.lang.String getSplitpage() {
		return get("splitpage");
	}

	public void setFormtoken(java.lang.String formtoken) {
		set("formtoken", formtoken);
	}

	public java.lang.String getFormtoken() {
		return get("formtoken");
	}

	public void setIpblack(java.lang.String ipblack) {
		set("ipblack", ipblack);
	}

	public java.lang.String getIpblack() {
		return get("ipblack");
	}

	public void setPrivilegess(java.lang.String privilegess) {
		set("privilegess", privilegess);
	}

	public java.lang.String getPrivilegess() {
		return get("privilegess");
	}

	public void setIspv(java.lang.String ispv) {
		set("ispv", ispv);
	}

	public java.lang.String getIspv() {
		return get("ispv");
	}

	public void setPvtype(java.lang.String pvtype) {
		set("pvtype", pvtype);
	}

	public java.lang.String getPvtype() {
		return get("pvtype");
	}

	public void setModulenames(java.lang.String modulenames) {
		set("modulenames", modulenames);
	}

	public java.lang.String getModulenames() {
		return get("modulenames");
	}

}
