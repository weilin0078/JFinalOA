/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.sys.mvc.mobile.bumph;

import com.jfinal.aop.Clear;
import com.jfinal.kit.StrKit;
import com.pointlion.sys.mvc.admin.bumph.BumphConstants;
import com.pointlion.sys.mvc.admin.bumph.BumphService;
import com.pointlion.sys.mvc.admin.workflow.WorkFlowService;
import com.pointlion.sys.mvc.common.base.BaseController;
import com.pointlion.sys.mvc.common.model.OaBumph;
import com.pointlion.sys.mvc.common.model.SysUser;;

/***
 * 通知公告控制器（手机端）
 * @author Administrator
 *
 */
@Clear()
public class MobileBumphController extends BaseController {
	static WorkFlowService wfservice =  WorkFlowService.me;
	static BumphService service =  BumphService.me;
	/***
	 * 获取所有待办
	 */
	public void getMyToDoList(){
		String userid = getPara("userid");
		if(StrKit.notBlank(userid)){
			renderSuccess(wfservice.getToDoListByKey(BumphConstants.BUSINESS_TABLENAME,BumphConstants.DEFKEY_BUMPH,SysUser.dao.getUsername(userid)),null);
		}else{
			renderError();
		}
	}
	
	/***
	 * 获取办理页面基本信息
	 */
	public void getBumphById(){
		String id = getPara("id");
		if(StrKit.notBlank(id)){
//			renderSuccess(service.getDoBumphTask(id),null);
		}else{
			renderError();
		}
	}
	
	/***
	 * 办理任务
	 */
	public void completeTask(){
		String taskid = getPara("taskid");
		String pass = getPara("pass");
		String comment = getPara("comment");
		String id = getPara("id");
		if(StrKit.notBlank(taskid)&&StrKit.notBlank(pass)&&StrKit.notBlank(id)){
			service.completeTask(pass,comment, taskid, OaBumph.dao.findById(id));
			renderSuccess();
		}else{
			renderError();
		}
	}
}
