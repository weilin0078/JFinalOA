/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.sys.mvc.mobile.notice;

import com.jfinal.aop.Clear;
import com.jfinal.kit.StrKit;
import com.pointlion.sys.mvc.common.base.BaseController;
import com.pointlion.sys.mvc.common.model.OaNotice;;

/***
 * 通知公告控制器（手机端）
 * @author Administrator
 *
 */
@Clear()
public class MobileNoticeController extends BaseController {
	
	static MobileNoticeService service =  MobileNoticeService.me;
	
	/***
	 * 获取用户收到的通知公告
	 */
	public void getUserReceiveNotice(){
		String userid = getPara("userid");
		if(StrKit.notBlank(userid)){
			renderSuccess(service.getMyNotice(userid),messageSuccess);
		}else{
			renderError();
		}
	}
	
	/***
	 * 获取通知公告单条信息
	 */
	public void getNoticeById(){
		String id = getPara("id");
		if(StrKit.notBlank(id)){
			renderSuccess(OaNotice.dao.findById(id),messageSuccess);
		}else{
			renderError();
		}
	}
	
}
