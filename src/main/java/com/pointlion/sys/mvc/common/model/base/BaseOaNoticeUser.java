package com.pointlion.sys.mvc.common.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseOaNoticeUser<M extends BaseOaNoticeUser<M>> extends Model<M> implements IBean {

	public void setId(java.lang.String id) {
		set("id", id);
	}

	public java.lang.String getId() {
		return get("id");
	}

	public void setUserId(java.lang.String userId) {
		set("user_id", userId);
	}

	public java.lang.String getUserId() {
		return get("user_id");
	}

	public void setUserName(java.lang.String userName) {
		set("user_name", userName);
	}

	public java.lang.String getUserName() {
		return get("user_name");
	}

	public void setNoticeId(java.lang.String noticeId) {
		set("notice_id", noticeId);
	}

	public java.lang.String getNoticeId() {
		return get("notice_id");
	}

	public void setIfSign(java.lang.String ifSign) {
		set("if_sign", ifSign);
	}

	public java.lang.String getIfSign() {
		return get("if_sign");
	}

	public void setSignTime(java.lang.String signTime) {
		set("sign_time", signTime);
	}

	public java.lang.String getSignTime() {
		return get("sign_time");
	}

}
