package com.pointlion.config.routes;

import com.jfinal.config.Routes;
import com.pointlion.mvc.admin.oa.apply.seal.OaApplySealController;
import com.pointlion.mvc.admin.oa.common.CommonBusinessController;
import com.pointlion.mvc.admin.oa.notice.NoticeController;
import com.pointlion.mvc.admin.oa.workflow.WorkFlowController;
import com.pointlion.mvc.admin.oa.workflow.model.ModelController;
import com.pointlion.mvc.admin.oa.workflow.model.ModelEditorJsonRestResource;
import com.pointlion.mvc.admin.oa.workflow.model.ModelSaveRestResource;
import com.pointlion.mvc.admin.oa.workflow.rest.ProcessDefinitionDiagramLayoutResource;
import com.pointlion.mvc.admin.oa.workflow.rest.ProcessInstanceDiagramLayoutResource;
import com.pointlion.mvc.admin.oa.workflow.rest.ProcessInstanceHighlightsResource;
import com.pointlion.mvc.admin.oa.workflow.rest.StencilsetRestResource;
import com.pointlion.mvc.admin.oa.workplan.WorkPlanController;

public class OARoutes extends Routes{

	@Override
	public void config() {
		setBaseViewPath("/WEB-INF/admin/oa");
		//通用业务控制器
		add("/admin/oa/common/business",CommonBusinessController.class);
		//流程图
		add("/admin/oa/model",ModelController.class,"/workflow/model");//工作流-模型
		add("/admin/oa/workflow",WorkFlowController.class,"/workflow");//工作流
		/***在线办公****/
		add("/admin/oa/notice",NoticeController.class,"/notice");//通知公告
		//工作计划
		add("/admin/oa/workplan",WorkPlanController.class,"/workplan");//工作计划
		add("/admin/oa/apply/seal",OaApplySealController.class,"/apply/seal");//用章申请
		//流程在线编辑器和流程跟踪所用路由
		add("/admin/oa/process-instance/highlights",ProcessInstanceHighlightsResource.class);//modeler
		add("/admin/oa/process-instance/diagram-layout",ProcessInstanceDiagramLayoutResource.class);//modeler
		add("/admin/oa/process-definition/diagram-layout",ProcessDefinitionDiagramLayoutResource.class);//modeler
		add("/admin/oa/modelEditor/save",ModelSaveRestResource.class);
		add("/admin/oa/modelEditor/json",ModelEditorJsonRestResource.class);
		add("/admin/oa/editor/stencilset",StencilsetRestResource.class);
	}

}
