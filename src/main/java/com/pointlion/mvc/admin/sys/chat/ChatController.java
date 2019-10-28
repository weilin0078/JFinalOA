/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.mvc.admin.sys.chat;

import com.pointlion.mvc.common.base.BaseController;
import com.pointlion.mvc.common.model.SysUser;

/***
 * 首页控制器
 */
public class ChatController extends BaseController {
	
	public void getChatPage(){
		String id = getPara("id");
		SysUser user = SysUser.dao.getById(id);
		setAttr("user", user);
		renderIframe("friendChat.html");
	}
}
