/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.sys.mvc.base.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.pointlion.sys.mvc.bumph.OaBumph;
import com.pointlion.sys.mvc.bumph.OaBumphOrg;
import com.pointlion.sys.mvc.bumph.OaBumphOrgUser;
import com.pointlion.sys.mvc.chat.SysFriend;
import com.pointlion.sys.mvc.cstmsetting.SysCustomSetting;
import com.pointlion.sys.mvc.menu.SysMenu;
import com.pointlion.sys.mvc.notice.OaNotice;
import com.pointlion.sys.mvc.notice.OaNoticeUser;
import com.pointlion.sys.mvc.org.SysOrg;
import com.pointlion.sys.mvc.role.SysRole;
import com.pointlion.sys.mvc.role.SysRoleAuth;
import com.pointlion.sys.mvc.user.SysUser;
import com.pointlion.sys.mvc.user.SysUserRole;
import com.pointlion.sys.mvc.workflow.ActReProcdef;
import com.pointlion.sys.mvc.workflow.model.ActReModel;
import com.pointlion.sys.mvc.workflow.model.VTasklist;


public class _MappingKit {

	public static void mapping(ActiveRecordPlugin arp) {
		//系统管理
		arp.addMapping("sys_user", "id", SysUser.class);//用户
		arp.addMapping("sys_user_role", "id", SysUserRole.class);//用户角色
		arp.addMapping("sys_menu", "id", SysMenu.class);//菜单
		arp.addMapping("sys_role", "id", SysRole.class);//角色
		arp.addMapping("sys_role_auth", "id", SysRoleAuth.class);//角色对应功能权限
		arp.addMapping("sys_org", "id", SysOrg.class);//组织结构
		arp.addMapping("sys_friend", "id", SysFriend.class);//用户好友
		//在线办公
		//通知公告
		arp.addMapping("oa_notice", "id", OaNotice.class);
		arp.addMapping("oa_notice_user", "id", OaNoticeUser.class);
		//公文
		arp.addMapping("oa_bumph", "id", OaBumph.class);
		arp.addMapping("oa_bumph_org", "id", OaBumphOrg.class);
		arp.addMapping("oa_bumph_org_user", "id", OaBumphOrgUser.class);
		//流程
		arp.addMapping("act_re_model", "ID_", ActReModel.class);//流程模型
		arp.addMapping("act_re_procdef", "ID_", ActReProcdef.class);
		arp.addMapping("v_tasklist", "TASKID", VTasklist.class);//任务--视图
		//自定义设置
		arp.addMapping("sys_custom_setting", "id", SysCustomSetting.class);//自定义设置
	}
}

