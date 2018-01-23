/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.sys.mvc.mobile.user;

import com.jfinal.aop.Clear;
import com.jfinal.kit.StrKit;
import com.pointlion.sys.mvc.common.base.BaseController;
import com.pointlion.sys.mvc.common.model.SysUser;

/***
 * 通知公告控制器（手机端）
 * @author Administrator
 *
 */
@Clear()
public class MobileUserController extends BaseController {
	
	static MobileUserService service =  MobileUserService.me;
	
	/***
	 * 获取所有联系人
	 */
	public void getAllUser(){
		renderSuccess(service.getAllUser(),null);
	}
	
	/***
	 * 获取单个联系人
	 */
	public void getOneUser(){
		String id = getPara("id");
		if(StrKit.notBlank(id)){
			renderSuccess(SysUser.dao.findById(id),null);
		}else{
			renderError();
		}
	}
}
