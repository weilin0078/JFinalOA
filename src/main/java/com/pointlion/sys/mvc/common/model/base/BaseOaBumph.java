package com.pointlion.sys.mvc.common.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseOaBumph<M extends BaseOaBumph<M>> extends Model<M> implements IBean {

	public void setId(java.lang.String id) {
		set("id", id);
	}

	public java.lang.String getId() {
		return get("id");
	}

	public void setTitle(java.lang.String title) {
		set("title", title);
	}

	public java.lang.String getTitle() {
		return get("title");
	}

	public void setSenderId(java.lang.String senderId) {
		set("sender_id", senderId);
	}

	public java.lang.String getSenderId() {
		return get("sender_id");
	}

	public void setSenderName(java.lang.String senderName) {
		set("sender_name", senderName);
	}

	public java.lang.String getSenderName() {
		return get("sender_name");
	}

	public void setSenderOrgid(java.lang.String senderOrgid) {
		set("sender_orgid", senderOrgid);
	}

	public java.lang.String getSenderOrgid() {
		return get("sender_orgid");
	}

	public void setSenderOrgname(java.lang.String senderOrgname) {
		set("sender_orgname", senderOrgname);
	}

	public java.lang.String getSenderOrgname() {
		return get("sender_orgname");
	}

	public void setDocType(java.lang.String docType) {
		set("doc_type", docType);
	}

	public java.lang.String getDocType() {
		return get("doc_type");
	}

	public void setDocNumYear(java.lang.String docNumYear) {
		set("doc_num_year", docNumYear);
	}

	public java.lang.String getDocNumYear() {
		return get("doc_num_year");
	}

	public void setDocNumN(java.lang.Integer docNumN) {
		set("doc_num_n", docNumN);
	}

	public java.lang.Integer getDocNumN() {
		return get("doc_num_n");
	}

	public void setContent(java.lang.String content) {
		set("content", content);
	}

	public java.lang.String getContent() {
		return get("content");
	}

	public void setIfSubmit(java.lang.String ifSubmit) {
		set("if_submit", ifSubmit);
	}

	public java.lang.String getIfSubmit() {
		return get("if_submit");
	}

	public void setIfSend(java.lang.String ifSend) {
		set("if_send", ifSend);
	}

	public java.lang.String getIfSend() {
		return get("if_send");
	}

	public void setIfComplete(java.lang.String ifComplete) {
		set("if_complete", ifComplete);
	}

	public java.lang.String getIfComplete() {
		return get("if_complete");
	}
	
	public void setIfAgree(java.lang.String ifAgree) {
		set("if_agree", ifAgree);
	}
	
	public java.lang.String getIfAgree() {
		return getStr("if_agree");
	}
	public void setDocNum(java.lang.String docNum) {
		set("doc_num", docNum);
	}

	public java.lang.String getDocNum() {
		return get("doc_num");
	}
	
	public void setDocNumSource(java.lang.String docNumSource) {
		set("doc_num_source", docNumSource);
	}

	public java.lang.String getDocNumSource() {
		return get("doc_num_source");
	}
	
	public void setProcInsId(java.lang.String procInsId) {
		set("proc_ins_id", procInsId);
	}

	public java.lang.String getProcInsId() {
		return get("proc_ins_id");
	}

	public void setFirstLeaderAudit(java.lang.String firstLeaderAudit) {
		set("first_leader_audit", firstLeaderAudit);
	}

	public java.lang.String getFirstLeaderAudit() {
		return get("first_leader_audit");
	}

	public void setSecondLeaderAudit(java.lang.String secondLeaderAudit) {
		set("second_leader_audit", secondLeaderAudit);
	}

	public java.lang.String getSecondLeaderAudit() {
		return get("second_leader_audit");
	}

	public void setCreateTime(java.lang.String createTime) {
		set("create_time", createTime);
	}

	public java.lang.String getCreateTime() {
		return get("create_time");
	}
	
	public void setSendTime(java.lang.String sendTime) {
		set("send_time", sendTime);
	}

	public java.lang.String getSendTime() {
		return get("send_time");
	}
}
