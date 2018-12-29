package com.pointlion.sys.mvc.admin.oa.common;

public class OAConstants {
	/*****
	 * 模版引擎中用到了！！！修改时候注意  
	 * me.addSharedObject("OAConstants", new OAConstants());
	 */
//	//公文
//	public static final String DEFKEY_BUMPH="Bumph";//公文
//	//项目
//	public static final String DEFKEY_PROJECT_BUILD="ProjectBuild";//项目立项申请
//	public static final String DEFKEY_PROJECT_CHANGE_MEMBER="ProjectChangeMember";//项目重要人员变更申请
//	public static final String DEFKEY_PROJECT_EXPRESS_CONFIRM="ProjectExpressConfirm";//项目单据快递确认
//	//合同
//	public static final String DEFKEY_CONTRACT_APPLY_1="ContractApply_1";//合同申请
//	public static final String DEFKEY_CONTRACT_APPLY_2="ContractApply_2";//合同申请
//	public static final String DEFKEY_CONTRACT_APPLY_3="ContractApply_3";//合同申请
//	public static final String DEFKEY_CONTRACT_APPLY_4="ContractApply_4";//合同申请
//	public static final String DEFKEY_CONTRACT_APPLY_5="ContractApply_5";//合同申请
//	public static final String DEFKEY_CONTRACT_APPLY_6="ContractApply_6";//合同申请
//	public static final String DEFKEY_CONTRACT_APPLY_7="ContractApply_7";//合同申请
//	public static final String DEFKEY_CONTRACT_APPLY_8="ContractApply_8";//合同申请
//	public static final String DEFKEY_CONTRACT_APPLY_9="ContractApply_9";//合同申请
//	public static final String DEFKEY_CONTRACT_APPLY_10="ContractApply_10";//合同申请
//	public static final String DEFKEY_CONTRACT_APPLY_11="ContractApply_11";//合同申请
//	public static final String DEFKEY_CONTRACT_APPLY_12="ContractApply_12";//合同申请
//	public static final String DEFKEY_CONTRACT_APPLY_13="ContractApply_13";//合同申请
//	public static final String DEFKEY_CONTRACT_APPLY_14="ContractApply_14";//合同申请
//	public static final String DEFKEY_CONTRACT_APPLY_15="ContractApply_15";//合同申请
//	public static final String DEFKEY_CONTRACT_APPLY_16="ContractApply_16";//合同申请
//	public static final String DEFKEY_CONTRACT_APPLY_17="ContractApply_17";//合同申请
//	
//	public static final String DEFKEY_CONTRACT_CHANGE="ContractChange";//合同变更申请
//	public static final String DEFKEY_CONTRACT_INVOICE="ContractInvoice";//合同开票申请
//	public static final String DEFKEY_CONTRACT_PAY="ContractPay";//合同付款申请
//	public static final String DEFKEY_CONTRACT_STOP="ContractStop";//合同终止申请
//	//常用日常申请
//	public static final String DEFKEY_APPLY_BUSINESSCARD="BusinessCard";//名片印刷申请
//	public static final String DEFKEY_APPLY_BUY="Buy";//采购申请
//	public static final String DEFKEY_APPLY_COST="Cost";//费用申请
//	public static final String DEFKEY_APPLY_GIFT="Gift";//礼物申请
//	public static final String DEFKEY_APPLY_HOTEL="Hotel";//宾馆申请
//	public static final String DEFKEY_APPLY_MEETROOM="Meetroom";//会议室申请
//	public static final String DEFKEY_APPLY_OFFICEOBJECT="OfficeObject";//办公用品申请
//	public static final String DEFKEY_APPLY_RESGET="ResGet";//物品领用
//	public static final String DEFKEY_APPLY_SEAL="Seal";//公章申请
//	public static final String DEFKEY_APPLY_TRAINTICKET="TrainTicket";//车船票申请
//	public static final String DEFKEY_APPLY_USE_CAR="UseCar";//汽车借用
//	public static final String DEFKEY_APPLY_USERCHANGESTATION="UserChangeStation";//调岗申请
//	public static final String DEFKEY_APPLY_USERDIMISSION="UserDimission";//辞职申请
//	public static final String DEFKEY_APPLY_USERREGULAR="UserRegular";//转正申请	
//	public static final String DEFKEY_APPLY_USERCARWORK="UserCarWork";//私车公用申请
//	public static final String DEFKEY_APPLY_LEAVE="Leave";//请假申请
//	//财务类申请
//	public static final String DEFKEY_APPLY_FINANCE_1="Finance_1";//财务类申请-建材及设备采购款支付申请
//	public static final String DEFKEY_APPLY_FINANCE_2="Finance_2";//财务类申请-工程款支付申请
//	public static final String DEFKEY_APPLY_FINANCE_3="Finance_3";//财务类申请-税费申请
//	public static final String DEFKEY_APPLY_FINANCE_4="Finance_4";//财务类申请-归还贷款申请
//	public static final String DEFKEY_APPLY_FINANCE_5="Finance_5";//财务类申请-财务相关费用支付申请
//	public static final String DEFKEY_APPLY_FINANCE_6="Finance_6";//财务类申请-管理费用支付申请（差旅，应酬）
//	public static final String DEFKEY_APPLY_FINANCE_7="Finance_7";//财务类申请-管理费用支付申请（其他）
//	public static final String DEFKEY_APPLY_FINANCE_8="Finance_8";//财务类申请-无票报销
//	public static final String DEFKEY_APPLY_FINANCE_9="Finance_9";//财务类申请-业务暂借款申请
//	public static final String DEFKEY_APPLY_FINANCE_10="Finance_10";//财务类申请-资金调拨申请
//	public static final String DEFKEY_APPLY_FINANCE_11="Finance_11";//财务类申请-审批内卡融资
//	public static final String DEFKEY_APPLY_FINANCE_12="Finance_12";//财务类申请-审批内卡财务筹划
//	public static final String DEFKEY_APPLY_FINANCE_13="Finance_13";//财务类申请-审批内卡工商登记
//	public static final String DEFKEY_APPLY_FINANCE_14="Finance_14";//财务类申请-审批内卡其他
//	public static final String DEFKEY_APPLY_FINANCE_15="Finance_15";//财务类申请-固定资产申请
//	//银行账户
//	public static final String DEFKEY_APPLY_ACCOUNTBANK_1 = "AccountBank_1";	//开户申请
//	public static final String DEFKEY_APPLY_ACCOUNTBANK_2 = "AccountBank_2";	//销户申请
//	public static final String DEFKEY_APPLY_ACCOUNTBANK_3 = "AccountBank_3";	//变更申请
//	public static final String DEFKEY_APPLY_ACCOUNTBANK_4 = "AccountBank_4";	//开通网银
//	public static final String DEFKEY_APPLY_ACCOUNTBANK_5 = "AccountBank_5";	//注销网银
//	public static final String DEFKEY_APPLY_ACCOUNTBANK_6 = "AccountBank_6";	//开户开通网银
//	//资产
////	public static final String DEFKEY_AMS_ALLOT="AmsAllot";//资产调配
//	public static final String DEFKEY_AMS_BORROW="AmsBorrow";//资产借用
//	public static final String DEFKEY_AMS_DISPOSE="AmsDispose";//资产处置
//	public static final String DEFKEY_AMS_NEED="AmsNeed";//资产需求
//	public static final String DEFKEY_AMS_RECEIVE="AmsReceive";//资产领用
//	public static final String DEFKEY_AMS_REPAIR="AmsRepair";//资产报修
//	public static final String DEFKEY_AMS_SIGNIN="AmsSignin";//资产录入
	//自定义流程
	public static final String DEFKEY_APPLY_CUSTOM = "OaApplyCustom";
	/***
	 * 流程中会用的角色
	 */
	public static final String WORKFLOW_ROLE_KEY_OrgLeader="OrgLeader";//部门经理
	public static final String WORKFLOW_ROLE_KEY_ChildCompanyFinanceLeader="ChildCompanyFinanceLeader";//分公司财务经理
	public static final String WORKFLOW_ROLE_KEY_ChildCompanyLeader="ChildCompanyLeader";//分公司总经理
	public static final String WORKFLOW_ROLE_KEY_MainOfficeLeader="MainOfficeLeader";//总公司行政部经理
	public static final String WORKFLOW_ROLE_KEY_MainTopLeader="MainTopLeader";//总公司总经理
	public static final String WORKFLOW_ROLE_KEY_MainFinanceLeader="MainFinanceLeader";//总公司财务部经理
	public static final String WORKFLOW_ROLE_KEY_MainProjectLeader="MainProjectLeader";//总公司工程部经理
	public static final String WORKFLOW_ROLE_KEY_MainHRLeader="MainHRLeader";//总公司人事行政部经理
	
	/***
	 * 流程的一些参数
	 */
	//必要的流程变量
	public static final String WORKFLOW_VAR_PROCESS_INSTANCE_START_TIME = "procIncStartTime";//申请时间
	public static final String WORKFLOW_VAR_APPLY_TITLE = "title";//标题
	public static final String WORKFLOW_VAR_APPLY_BUSINESS_CLASSNAME = "className";//申请模块的model类名
	//流程条件中用的
	public static final String WORKFLOW_VAR_APPLY_VAR_CHILDCOMPANY_LEVEL = "childCompanyLevel";//申请人所在子公司级别.总公司：0。一级子公司：1。二级子公司：2。
	public static final String WORKFLOW_VAR_APPLY_VAR_MONEY = "money";//【财务相关流程可用】【合同申请流程可用】，相关金额
	//设置办理人用的。流程图中可用的流程变量！！！！！！！！！
	public static final String WORKFLOW_VAR_APPLY_USERNAME = "applyUsername";//【所有流程可用】，当前申请人
	public static final String WORKFLOW_VAR_APPLY_MY_ORG_LEADER = "applyMYOrgLeader";//【所有流程可用】，部门经理,申请人所在部门的经理，自己为经理的时候为自己，没有经理则向上递归查找上级经理。如果一直都没有则为自己。
	public static final String WORKFLOW_VAR_APPLY_MY_CHILD_COMPANY_FINANCE = "applyChildCompanyFinanceLeader";//【所有流程可用】，申请人所在分公司财务
	public static final String WORKFLOW_VAR_APPLY_MY_CHILD_COMPANY_LEADER = "applyChildCompanyLeader";//【所有流程可用】，申请人所在分公司总经理
	public static final String WORKFLOW_VAR_APPLY_FIRST_CHILD_COMPANY_FINANCE = "applyFirstChildCompanyFinanceLeader";//【所有流程可用】，申请人所在一级分公司财务经理
	public static final String WORKFLOW_VAR_APPLY_FIRST_CHILD_COMPANY_LEADER = "applyFirstChildCompanyLeader";//【所有流程可用】，申请人所在一级分公司总经理
	public static final String WORKFLOW_VAR_APPLY_TOP_COMPANY_OFFICE = "applyMainOfficeLeader";//【所有流程可用】，总公司综合行政部经理
	public static final String WORKFLOW_VAR_APPLY_ABOUT_ORG_LEADER = "applyAboutOrgLeader";//【业务暂借款申请】，单据中所选的相关职能部门-的部门经理审批
	public static final String WORKFLOW_VAR_APPLY_TOP_COMPANY_HR = "applyMainHRLeader";//【所有流程可用】，总公司人事行政部
	public static final String WORKFLOW_VAR_APPLY_TOP_COMPANY_FINANCE = "applyMainFinanceLeader";//【所有流程可用】，总公司财务部经理
	public static final String WORKFLOW_VAR_APPLY_TOP_COMPANY_PROJECT = "applyMainProjectLeader";//【所有流程可用】，总公司工程部经理
	public static final String WORKFLOW_VAR_APPLY_TOP_COMPANY_LEADER = "applyMainTopLeader";//【所有流程可用】，总公司总经理
	public static final String WORKFLOW_VAR_APPLY_BANK_ACCOUNT_AUDIT1 = "bankAccountAudit1";//【银行卡开户销户相关可用】，第1个办理人
	public static final String WORKFLOW_VAR_APPLY_BANK_ACCOUNT_AUDIT2 = "bankAccountAudit2";//【银行卡开户销户相关可用】，第2个办理人
	public static final String WORKFLOW_VAR_APPLY_BANK_ACCOUNT_AUDIT3 = "bankAccountAudit3";//【银行卡开户销户相关可用】，第3个办理人
	
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
