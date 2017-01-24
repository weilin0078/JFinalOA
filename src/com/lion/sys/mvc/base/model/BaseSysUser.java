/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.lion.sys.mvc.base.model;

import com.jfinal.plugin.activerecord.IBean;
import com.lion.sys.mvc.base.BaseModel;

@SuppressWarnings("serial")
public abstract class BaseSysUser<M extends BaseSysUser<M>> extends BaseModel<M> implements IBean {

	public void setId(java.lang.String id) {
		set("id", id);
	}

	public java.lang.String getId() {
		return get("id");
	}

	public void setOrderid(java.lang.Long orderid) {
		set("orderid", orderid);
	}

	public java.lang.Long getOrderid() {
		return get("orderid");
	}

	public void setUsername(java.lang.String username) {
		set("username", username);
	}

	public java.lang.String getUsername() {
		return get("username");
	}

	public void setPassword(java.lang.String password) {
		set("password", password);
	}

	public java.lang.String getPassword() {
		return get("password");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return get("name");
	}

	public void setSalt(java.lang.String salt) {
		set("salt", salt);
	}

	public java.lang.String getSalt() {
		return get("salt");
	}

	public void setStatus(java.lang.String status) {
		set("status", status);
	}

	public java.lang.String getStatus() {
		return get("status");
	}

	public void setStopdate(java.util.Date stopdate) {
		set("stopdate", stopdate);
	}

	public java.util.Date getStopdate() {
		return get("stopdate");
	}

	public void setDepartmentid(java.lang.String departmentid) {
		set("departmentid", departmentid);
	}

	public java.lang.String getDepartmentid() {
		return get("departmentid");
	}

	public void setStationid(java.lang.String stationid) {
		set("stationid", stationid);
	}

	public java.lang.String getStationid() {
		return get("stationid");
	}

	public void setEmail(java.lang.String email) {
		set("email", email);
	}

	public java.lang.String getEmail() {
		return get("email");
	}

	public void setIdcard(java.lang.String idcard) {
		set("idcard", idcard);
	}

	public java.lang.String getIdcard() {
		return get("idcard");
	}

	public void setMobile(java.lang.String mobile) {
		set("mobile", mobile);
	}

	public java.lang.String getMobile() {
		return get("mobile");
	}

}
