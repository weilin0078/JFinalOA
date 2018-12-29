
package com.pointlion.sys.mvc.common.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

public class _MappingKit {

	public static void mapping(ActiveRecordPlugin arp) {
		//系统设置的
		arp.addMapping("sys_user", "id", SysUser.class);//用户
		arp.addMapping("sys_user_role", "id", SysUserRole.class);//用户角色
		arp.addMapping("sys_menu", "id", SysMenu.class);//菜单
		arp.addMapping("sys_role", "id", SysRole.class);//角色
		arp.addMapping("sys_role_auth", "id", SysRoleAuth.class);//角色对应功能权限
		arp.addMapping("sys_org", "id", SysOrg.class);//组织结构
		arp.addMapping("sys_friend", "id", SysFriend.class);//用户好友
		arp.addMapping("sys_custom_setting", "id", SysCustomSetting.class);//自定义设置
		arp.addMapping("sys_data_auth_rule", "id", SysDataAuth.class);//数据权限配置信息表
		arp.addMapping("sys_log", "id", SysLog.class);//系统日志表
		arp.addMapping("sys_dct", "id", SysDct.class);//系统字典表
		arp.addMapping("sys_dct_group", "id", SysDctGroup.class);//系统字典分组表
		//办公的
		arp.addMapping("act_re_model", "ID_", ActReModel.class);//流程模型
		arp.addMapping("act_re_procdef", "ID_", ActReProcdef.class);
		arp.addMapping("v_tasklist", "TASKID", VTasklist.class);//任务--视图
		arp.addMapping("oa_notice", "id", OaNotice.class);//通知公告
		arp.addMapping("oa_notice_user", "id", OaNoticeUser.class);//通知公告收
		arp.addMapping("oa_apply_finance","id", OaApplyFinance.class);//财务类申请
		arp.addMapping("oa_flow_carbon_c", "id", OaFlowCarbonC.class);//流程抄送
	}
}

