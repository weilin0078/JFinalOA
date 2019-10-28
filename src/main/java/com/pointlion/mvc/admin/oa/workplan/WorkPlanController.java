package com.pointlion.mvc.admin.oa.workplan;

import com.pointlion.mvc.common.base.BaseController;

public class WorkPlanController extends BaseController{
	
	/***
	 * 工作计划首页
	 */
	public void getWorkPlanPage(){
		renderIframe("workplan.html");
	}
}
