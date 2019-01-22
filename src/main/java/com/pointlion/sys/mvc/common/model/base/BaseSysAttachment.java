package com.pointlion.sys.mvc.common.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseSysAttachment<M extends BaseSysAttachment<M>> extends Model<M> implements IBean {

	public void setId(java.lang.String id) {
		set("id", id);
	}
	
	public java.lang.String getId() {
		return getStr("id");
	}

	public void setCreateUserId(java.lang.String createUserId) {
		set("create_user_id", createUserId);
	}
	
	public java.lang.String getCreateUserId() {
		return getStr("create_user_id");
	}

	public void setCreateUserName(java.lang.String createUserName) {
		set("create_user_name", createUserName);
	}
	
	public java.lang.String getCreateUserName() {
		return getStr("create_user_name");
	}

	public void setCreateOrgId(java.lang.String createOrgId) {
		set("create_org_id", createOrgId);
	}
	
	public java.lang.String getCreateOrgId() {
		return getStr("create_org_id");
	}

	public void setCreateOrgName(java.lang.String createOrgName) {
		set("create_org_name", createOrgName);
	}
	
	public java.lang.String getCreateOrgName() {
		return getStr("create_org_name");
	}

	public void setCreateTime(java.lang.String createTime) {
		set("create_time", createTime);
	}
	
	public java.lang.String getCreateTime() {
		return getStr("create_time");
	}

	public void setUrl(java.lang.String url) {
		set("url", url);
	}
	
	public java.lang.String getUrl() {
		return getStr("url");
	}

	public void setRealUrl(java.lang.String realUrl) {
		set("real_url", realUrl);
	}
	
	public java.lang.String getRealUrl() {
		return getStr("real_url");
	}

	public void setFileName(java.lang.String fileName) {
		set("file_name", fileName);
	}
	
	public java.lang.String getFileName() {
		return getStr("file_name");
	}

	public void setSuffix(java.lang.String suffix) {
		set("suffix", suffix);
	}
	
	public java.lang.String getSuffix() {
		return getStr("suffix");
	}

	public void setDes(java.lang.String des) {
		set("des", des);
	}
	
	public java.lang.String getDes() {
		return getStr("des");
	}
	
	public void setModuelFrom(java.lang.String u) {
		set("moduel_from", u);
	}
	
	public java.lang.String getModuelFrom() {
		return getStr("moduel_from");
	}
	
	public void setKey(java.lang.String key) {
		set("key", key);
	}
	
	public java.lang.String getKey() {
		return getStr("key");
	}
	
	public void setBusinessId(java.lang.String b) {
		set("business_id", b);
	}
	
	public java.lang.String getBusinessId() {
		return getStr("business_id");
	}
}
