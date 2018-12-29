package com.pointlion.sys.mvc.admin.oa.common;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.runtime.ProcessInstance;

import com.jfinal.plugin.activerecord.Model;
import com.pointlion.sys.mvc.common.utils.Constants;
import com.pointlion.sys.plugin.activiti.ActivitiPlugin;

/***
 * 流程结束服务类
 * @author Administrator
 *
 */
public class CommonFinishDeledate implements JavaDelegate{



	@SuppressWarnings("rawtypes")
	@Override
	public void execute(DelegateExecution execution) throws Exception {
//		ProcessInstance instance = ActivitiPlugin.buildProcessEngine().getRuntimeService().createProcessInstanceQuery().processInstanceId(execution.getProcessInstanceId()).singleResult();
//		String id = instance.getBusinessKey();//业务主键
//		String defKey = instance.getProcessDefinitionKey();//流程key
//		String pass = execution.getVariable("pass").toString();//是否同意
//		String tableName = WorkFlowUtil.getTablenameByDefkey(defKey);
//		WorkFlowService.me.updateIfCompleteAndIfAgree(tableName, pass, id);//更新是否同意，是否完成
		
		
		ProcessInstance instance = ActivitiPlugin.buildProcessEngine().getRuntimeService().createProcessInstanceQuery().processInstanceId(execution.getProcessInstanceId()).singleResult();
		String id = instance.getBusinessKey();//业务主键
		String className = execution.getVariable(OAConstants.WORKFLOW_VAR_APPLY_BUSINESS_CLASSNAME).toString();//对象类名
		Class busClass = Class.forName("com.pointlion.sys.mvc.common.model."+className);
		Model model = (Model)busClass.newInstance();
		model.set("id", id);
		model.set("if_complete",Constants.IF_COMPLETE_YES);
		model.set("if_agree", Constants.IF_AGREE_YES);
		model.update();
	}


}
