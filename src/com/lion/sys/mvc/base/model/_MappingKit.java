/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.lion.sys.mvc.base.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.lion.sys.mvc.menu.SysMenu;
import com.lion.sys.mvc.user.SysUser;
import com.lion.sys.mvc.workflow.model.ActReModel;


public class _MappingKit {

	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("sys_user", "id", SysUser.class);
		arp.addMapping("sys_menu", "id", SysMenu.class);
		arp.addMapping("act_re_model", "ID_", ActReModel.class);
	}
}

