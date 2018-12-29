package com.pointlion.sys.config.routes;

import com.jfinal.config.Routes;
import com.pointlion.sys.mvc.admin.oa.apply.finance.OaApplyFinanceController;
import com.pointlion.sys.mvc.admin.oa.notice.NoticeController;
import com.pointlion.sys.mvc.admin.oa.workflow.WorkFlowController;
import com.pointlion.sys.mvc.admin.oa.workflow.flowtask.FlowTaskController;
import com.pointlion.sys.mvc.admin.oa.workflow.model.ModelController;
import com.pointlion.sys.mvc.admin.oa.workflow.model.ModelEditorJsonRestResource;
import com.pointlion.sys.mvc.admin.oa.workflow.model.ModelSaveRestResource;
import com.pointlion.sys.mvc.admin.oa.workflow.rest.ProcessDefinitionDiagramLayoutResource;
import com.pointlion.sys.mvc.admin.oa.workflow.rest.ProcessInstanceDiagramLayoutResource;
import com.pointlion.sys.mvc.admin.oa.workflow.rest.ProcessInstanceHighlightsResource;
import com.pointlion.sys.mvc.admin.oa.workflow.rest.StencilsetRestResource;

public class OARoutes extends Routes{

	@Override
	public void config() {
		setBaseViewPath("/WEB-INF/admin/oa");
		//流程图
		add("/admin/oa/model",ModelController.class,"/workflow/model");//工作流-模型
		add("/admin/oa/workflow",WorkFlowController.class,"/workflow");//工作流
		/***在线办公****/
		add("/admin/oa/notice",NoticeController.class,"/notice");//通知公告
		add("/admin/oa/apply/finance",OaApplyFinanceController.class,"/apply/finance");//财务类申请
		//在线办公---申请
		add("/admin/oa/workflow/flowtask",FlowTaskController.class,"/workflow/flowtask");//处理通用任务
		//流程在线编辑器和流程跟踪所用路由
		add("/admin/oa/process-instance/highlights",ProcessInstanceHighlightsResource.class);//modeler
		add("/admin/oa/process-instance/diagram-layout",ProcessInstanceDiagramLayoutResource.class);//modeler
		add("/admin/oa/process-definition/diagram-layout",ProcessDefinitionDiagramLayoutResource.class);//modeler
		add("/admin/oa/modelEditor/save",ModelSaveRestResource.class);
		add("/admin/oa/modelEditor/json",ModelEditorJsonRestResource.class);
		add("/admin/oa/editor/stencilset",StencilsetRestResource.class);
	}

}
