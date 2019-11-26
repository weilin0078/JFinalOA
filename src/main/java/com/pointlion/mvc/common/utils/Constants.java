package com.pointlion.mvc.common.utils;

public class Constants {
	public static final String IF_MOBILE_CLIENT = "1";//是否是手机端访问
	public static final String SUBMIT_PASS_YES="1";
	public static final String SUBMIT_PASS_NO="0";
	
	public static final String IF_SUBMIT_YES="1";//是否提交
	public static final String IF_SUBMIT_NO="0";//是否提交
	
	public static final String IF_COMPLETE_YES="1";//是否完成
	public static final String IF_COMPLETE_NO="0";//是否完成
	
	public static final String IF_AGREE_YES="1";//是否同意--同意
	public static final String IF_AGREE_NO="2";//是否同意--不同意
	public static final String IF_AGREE_UNKNOWN="0";//默认
	
	public static final String FLOW_IF_AGREE_YES = "1";//流程线上的变量--同意
	public static final String FLOW_IF_AGREE_NO = "0";//流程线上的变量--不同意
	
	public static final String IF_BCAK_NO="0";//默认,未归还
	public static final String IF_BACK_YES="1";//已归还
	
	public static final String SUUUUUUUUUUUUUPER_USER_NAME = "lion";//后门管理员


//	一些比较重要的角色
	public static final String SYS_ROLE_SUPERADMIN = "SuperAdmin";//超级管理员角色
	public static final String SYS_ROLE_ORGLEADER="OrgLeader";//部门经理
	public static final String SYS_ROLE_MAINLEADER="MainLeader";//总经理
	public static final String SYS_ROLE_FINANCE="Finance";//财务
	public static final String SYS_ROLE_OFFICESTAFF="OfficeStaff";//人资
	public static final String SYS_ROLE_HR="HR";//人资

//	数据权限--老版本的数据权限，代码可以保留，建议不用
	public static final String SYS_DATA_AUTH_RULE_NOROLE = "@@@@@NoRole@@@@@";//数据权限没有权限的角色id
	public static final String CurrentUser_Org_Id = "$CurrentUser_Org_id$";//当前登录人，所在部门的id
	public static final String CurrentUser_Org_ParentChildCompanyId = "$CurrentUser_Org_ParentChildCompanyId$";//当前登录人，所在部门的所属子公司id
	public static final String CurrentUser_Id = "$CurrentUser_Id$";//当前登录人的id
	public static final String CurrentUser_Org_AllChildId = "$CurrentUser_Org_AllChildId$";//当前登录人，所在部门id的，所有下级部门id。（此属性只能配合in使用）

	//数据权限分类
	public static final String SYS_ROLE_SCOPE_MYSELF = "1";//查看自己
	public static final String SYS_ROLE_SCOPE_MYORG = "2";//查看自己部门
	public static final String SYS_ROLE_SCOPE_MYORG_AND_CHILDREN = "3";//查看自己部门以及部门下级
	public static final String SYS_ROLE_SCOPE_CUSTOM = "4";//查看自定义
	public static final String SYS_ROLE_SCOPE_ALL = "5";//查看所有
}
