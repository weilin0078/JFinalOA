/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.sys.mvc.chat;

import com.pointlion.sys.mvc.base.BaseController;
import com.pointlion.sys.mvc.user.SysUser;

/***
 * 首页控制器
 */
public class ChatController extends BaseController {
	
	public void getChatPage(){
		String id = getPara("id");
		SysUser user = SysUser.dao.getById(id);
		setAttr("user", user);
		render("/WEB-INF/admin/common/chat/friendChat.html");
	}
}
