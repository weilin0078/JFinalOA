/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.sys.mvc.mobile.bumph;

import com.jfinal.kit.StrKit;
import com.pointlion.sys.mvc.admin.bumph.BumphConstants;
import com.pointlion.sys.mvc.admin.workflow.WorkFlowService;
import com.pointlion.sys.mvc.common.base.BaseController;
import com.pointlion.sys.mvc.common.model.SysUser;

/***
 * 通知公告控制器（手机端）
 * @author Administrator
 *
 */
public class MobileBumphController extends BaseController {
	static WorkFlowService wfservice =  WorkFlowService.me;
	static MobileBumphService service =  MobileBumphService.me;
	/***
	 * 获取所有待办
	 */
	public void getMyToDoList(){
		String username = getPara("username");
		if(StrKit.notBlank(username)){
			SysUser u = SysUser.dao.getByUsername(username);
			renderSuccess(wfservice.getToDoListByKey(BumphConstants.BUSINESS_TABLENAME,BumphConstants.DEFKEY_BUMPH,u.getUsername()),null);
		}else{
			renderError();
		}
	}
	
	/***
	 * 获取办理页面基本信息
	 */
	public void getDoBumphTask(){
		String taskid = getPara("taskid");
		if(StrKit.notBlank(taskid)){
			renderSuccess(service.getDoBumphTask(taskid),null);
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
//		String comment = getPara("comment");
		String id = getPara("id");
		if(StrKit.notBlank(taskid)&&StrKit.notBlank(pass)&&StrKit.notBlank(id)){
//			service.completeTask(pass,comment, taskid, OaBumph.dao.findById(id));
			renderSuccess();
		}else{
			renderError();
		}
	}
}
