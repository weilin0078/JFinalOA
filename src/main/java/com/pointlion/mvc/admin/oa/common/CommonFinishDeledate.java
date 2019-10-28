package com.pointlion.mvc.admin.oa.common;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.engine.runtime.ProcessInstance;

import com.jfinal.plugin.activerecord.Model;
import com.pointlion.mvc.common.utils.Constants;
import com.pointlion.plugin.flowable.FlowablePlugin;

/***
 * 流程结束服务类
 * @author Administrator
 *
 */
public class CommonFinishDeledate implements JavaDelegate{



	@SuppressWarnings("rawtypes")
	@Override
	public void execute(DelegateExecution execution) {
//		ProcessInstance instance = FlowablePlugin.buildProcessEngine().getRuntimeService().createProcessInstanceQuery().processInstanceId(execution.getProcessInstanceId()).singleResult();
//		String id = instance.getBusinessKey();//业务主键
//		String defKey = instance.getProcessDefinitionKey();//流程key
//		String pass = execution.getVariable("pass").toString();//是否同意
//		String tableName = WorkFlowUtil.getTablenameByDefkey(defKey);
//		WorkFlowService.me.updateIfCompleteAndIfAgree(tableName, pass, id);//更新是否同意，是否完成

		ProcessInstance instance = FlowablePlugin.buildProcessEngine().getRuntimeService().createProcessInstanceQuery().processInstanceId(execution.getProcessInstanceId()).singleResult();
		String id = instance.getBusinessKey();//业务主键
		String className = execution.getVariable(OAConstants.WORKFLOW_VAR_APPLY_BUSINESS_CLASSNAME).toString();//对象类名
		String pass = execution.getVariable("pass").toString();//是否同意
			try {
				Class busClass = Class.forName(className);
				Model model = (Model)busClass.newInstance();
				model.set("id", id);
				model.set("if_complete",Constants.IF_COMPLETE_YES);
				if(Constants.SUBMIT_PASS_YES.equals(pass)){//如果进来的参数是pass=1
					model.set("if_agree",Constants.IF_AGREE_YES);
				}else{
					model.set("if_agree",Constants.IF_AGREE_NO);
				}
				model.update();
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			} catch (InstantiationException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}


	}


}
