/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.lion.sys.mvc.base.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.lion.sys.mvc.menu.SysMenu;
import com.lion.sys.mvc.module.SysModule;
import com.lion.sys.mvc.operate.SysOperate;
import com.lion.sys.mvc.role.SysRole;
import com.lion.sys.mvc.role.SysRoleoperator;
import com.lion.sys.mvc.user.SysUser;
import com.lion.sys.mvc.workflow.model.ActReModel;


public class _MappingKit {

	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("sys_user", "id", SysUser.class);//用户
		arp.addMapping("sys_menu", "id", SysMenu.class);//菜单
		arp.addMapping("act_re_model", "ID_", ActReModel.class);//模型
		arp.addMapping("sys_module", "id", SysModule.class);//模块
		arp.addMapping("sys_operate", "id", SysOperate.class);//功能
		arp.addMapping("sys_role", "id", SysRole.class);//角色
		arp.addMapping("sys_roleoperator", "id", SysRoleoperator.class);//角色对应功能权限
	}
}

