package com.pointlion.mvc.admin.oa.common;

public class OAConstants {
	/*****
	 * 模版引擎中用到了！！！修改时候注意  
	 * me.addSharedObject("OAConstants", new OAConstants());
	 */
	//自定义流程
	public static final String DEFKEY_APPLY_CUSTOM = "OaApplyCustom";

	/***
	 * 流程的一些参数
	 */
	//必要的流程变量
	public static final String WORKFLOW_VAR_PROCESS_INSTANCE_START_TIME = "procIncStartTime";//申请时间
	public static final String WORKFLOW_VAR_APPLY_TITLE = "title";//标题
	public static final String WORKFLOW_VAR_APPLY_BUSINESS_CLASSNAME = "className";//申请模块的model类名
	//设置办理人用的。流程图中可用的流程变量！！！！！！！！！
	public static final String WORKFLOW_VAR_APPLY_USERNAME = "applyUsername";//【所有流程可用】，当前申请人
	public static final String WORKFLOW_VAR_JOINTLY_USERLIST = "jointlyUserList";//会签任务的列表属性
	public static final String WORKFLOW_VAR_JOINTLY_USER = "jointlyUser";//会签任务的会签人

	/***
	 * 开启流程办理的时候，某些功能
	 */
	public static final String WORKFLOW_OPEN_ADD_ASSIGNEE="ifNeedAddAssignee";//开启加签功能----（网流程图的流程条件里，用的也是这个参数，${pass=='1'&&ifNeedAddAssignee=='1'}）


	/***
	 * 一些流程必要的状态
	 */
	public static final String OA_CONTRACT_STATE_STOP = "2";
	//资产的状态
	public static final String AMS_ASSET_STATE_0= "0";
	public static final String AMS_ASSET_STATE_1= "1";
	public static final String AMS_ASSET_STATE_2= "2";
	public static final String AMS_ASSET_STATE_3= "3";
	

	
}
