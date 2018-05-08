/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.sys.mvc.mobile.notice;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.kit.StrKit;
import com.pointlion.sys.mvc.common.base.BaseController;
import com.pointlion.sys.mvc.common.model.OaNotice;
import com.pointlion.sys.mvc.common.model.OaNoticeUser;
import com.pointlion.sys.mvc.common.model.SysUser;

/***
 * 通知公告控制器（手机端）
 * @author Administrator
 *
 */
public class MobileNoticeController extends BaseController {
	
	static MobileNoticeService service =  MobileNoticeService.me;
	
	/***
	 * 获取用户收到的通知公告
	 */
	public void getUserReceiveNotice(){
		String userid = getPara("userid");
		if(StrKit.notBlank(userid)){
			SysUser user = SysUser.dao.getByUsername(userid);
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("notSignList", service.getMyNotice(user.getId(),"0"));
			data.put("haveSignList", service.getMyNotice(user.getId(),"1"));
			renderSuccess(data,messageSuccess);
		}else{
			renderError();
		}
	}
	
	/***
	 * 获取通知公告单条信息
	 */
	public void getNoticeById(){
		String id = getPara("id");
		String userid = getPara("userid");
		if(StrKit.notBlank(id)){
			SysUser u = SysUser.dao.getByUsername(userid);
			OaNoticeUser user = OaNoticeUser.dao.getNoticeUserByNoticeIdAndUserid(id, u.getId());
			user.setIfSign("1");
			user.update();
			renderSuccess(OaNotice.dao.findById(id),messageSuccess);
		}else{
			renderError();
		}
	}
	
}
