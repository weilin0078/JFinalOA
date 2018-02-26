package com.pointlion.sys.mvc.admin.workflow;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.sys.mvc.common.utils.Constants;
import com.pointlion.sys.plugin.activiti.ActivitiPlugin;
import com.pointlion.sys.plugin.shiro.ShiroKit;

public class WorkFlowService {
	public static final WorkFlowService me = new WorkFlowService();
	/**
	 * 创建新模型
	 * @throws UnsupportedEncodingException 
	 * */
	public void createModel(ProcessEngine pe,String name,String key) throws UnsupportedEncodingException{
		RepositoryService repositoryService = pe.getRepositoryService();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        Model modelData = repositoryService.newModel();

        ObjectNode modelObjectNode = objectMapper.createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        String description = StringUtils.defaultString("模型描述信息");
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelData.setMetaInfo(modelObjectNode.toString());
        modelData.setName(name);
        modelData.setKey(StringUtils.defaultString(key));

        repositoryService.saveModel(modelData);
        repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
	}
	
	/***
	 * 部署模型
	 * @param id
	 * @return
	 */
	@Before(Tx.class)
	public String deploy(String id) {
		String message = "";
		try {
			ProcessEngine pe = ActivitiPlugin.buildProcessEngine();
			RepositoryService repositoryService = pe.getRepositoryService();
			org.activiti.engine.repository.Model modelData = repositoryService.getModel(id);
			BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
			JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
			BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
			BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
			byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel,"ISO-8859-1");
			
			String processName = modelData.getName();
			if (!StringUtils.endsWith(processName, ".bpmn20.xml")){
				processName += ".bpmn20.xml";
			}
//			System.out.println("========="+processName+"============"+modelData.getName());
			ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
			Deployment deployment = repositoryService.createDeployment().name(modelData.getName())
					.addInputStream(processName, in).deploy();
//					.addString(processName, new String(bpmnBytes)).deploy();
			
			// 设置流程分类
			List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
			for (ProcessDefinition processDefinition : list) {
				repositoryService.setProcessDefinitionCategory(processDefinition.getId(), modelData.getCategory());
				message = "部署成功";
			}
			if (list.size() == 0){
				message = "部署失败，没有流程。";
			}
		} catch (Exception e) {
			throw new ActivitiException("设计模型图不正确，检查模型正确性", e);
		}
		return message;
	}
	
	/***
	 * 删除模型
	 */
	@Before(Tx.class)
	public void deleteModel(String id){
		ActivitiPlugin.buildProcessEngine().getRepositoryService().deleteModel(id);
	}
	
	/***
	 * 挂起/激活
	 */
	@Before(Tx.class)
	public String updateState(String state,String procDefId){
		if (state.equals("active")) {
			ActivitiPlugin.buildProcessEngine().getRepositoryService().activateProcessDefinitionById(procDefId, true, null);
			return "激活成功";
		} else if (state.equals("suspend")) {
			ActivitiPlugin.buildProcessEngine().getRepositoryService().suspendProcessDefinitionById(procDefId, true, null);
			return "挂起成功";
		}
		return "无操作";
	}
	
	/***
	 * 转化为模型
	 */
	@Before(Tx.class)
	public Model convertToModel(String procDefId) throws UnsupportedEncodingException, XMLStreamException {
		RepositoryService repositoryService = ActivitiPlugin.buildProcessEngine().getRepositoryService();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
		InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
		processDefinition.getResourceName());
		XMLInputFactory xif = XMLInputFactory.newInstance();
		InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
		XMLStreamReader xtr = xif.createXMLStreamReader(in);
		BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
	
		BpmnJsonConverter converter = new BpmnJsonConverter();
		ObjectNode modelNode = converter.convertToJson(bpmnModel);
		org.activiti.engine.repository.Model modelData = repositoryService.newModel();
		modelData.setKey(processDefinition.getKey());
		modelData.setName(processDefinition.getName());
		modelData.setCategory(processDefinition.getCategory());//.getDeploymentId());
		modelData.setDeploymentId(processDefinition.getDeploymentId());
		modelData.setVersion(Integer.parseInt(String.valueOf(repositoryService.createModelQuery().modelKey(modelData.getKey()).count()+1)));
	
		ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
		modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, modelData.getVersion());
		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
		modelData.setMetaInfo(modelObjectNode.toString());
	
		repositoryService.saveModel(modelData);
	
		repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));
	
		return modelData;
	}
	
	/**
	 * 读取资源，通过部署ID
	 * @param processDefinitionId  流程定义ID
	 * @param processInstanceId 流程实例ID
	 * @param resourceType 资源类型(xml|image)
	 */
	public InputStream resourceRead(String procDefId, String proInsId, String resType) throws Exception {
		RuntimeService runtimeService = ActivitiPlugin.buildProcessEngine().getRuntimeService();
		RepositoryService repositoryService = ActivitiPlugin.buildProcessEngine().getRepositoryService();
		if (StringUtils.isBlank(procDefId)){
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(proInsId).singleResult();
			procDefId = processInstance.getProcessDefinitionId();
		}
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
		
		String resourceName = "";
		if (resType.equals("image")) {
			resourceName = processDefinition.getDiagramResourceName();
		} else if (resType.equals("xml")) {
			resourceName = processDefinition.getResourceName();
		}
		
		InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
		return resourceAsStream;
	}
	/***
	 * 删除正在运行的流程
	 */
	@Before(Tx.class)
	public void deleteDeployment(String deploymentId) {
		ActivitiPlugin.buildProcessEngine().getRepositoryService().deleteDeployment(deploymentId, true);
	}
	
	/***
	 * 启动流程
	 */
	@Before(Tx.class)
	public String startProcess(String id,String defKey,Map<String, Object> var){
		if(var==null){
			var = new HashMap<String, Object>();
		}
		var.put(Constants.WORKFLOW_APPLY_USERNAME, ShiroKit.getUsername());
		ProcessInstance procIns = ActivitiPlugin.buildProcessEngine().getRuntimeService().startProcessInstanceByKey(defKey,id,var);
		return procIns.getId();
	}

	/***
	 * 查询流程待办任务
	 */
	public Record getTaskRecord(String id){
		return Db.findFirst("SELECT * FROM v_tasklist t WHERE t.TASKID='"+id+"'");
	}
	/***
	 * 完成任务
	 */
	@Before(Tx.class)
	public void completeTask(String taskid,Map<String,Object> var){
		if(var!=null){
			var = new HashMap<String,Object>();
		}
		ActivitiPlugin.buildProcessEngine().getTaskService().complete(taskid, var);
	}
	@Before(Tx.class)
	public void completeTask(String taskid,String comment,Map<String,Object> var){
		TaskService service = ActivitiPlugin.buildProcessEngine().getTaskService();
		Record task = getTaskRecord(taskid);
		String insid = task.getStr("INSID");
		if(StrKit.notBlank(insid)&&StrKit.notBlank(comment)){
			service.addComment(taskid, insid, comment);
		}
		if(var==null){
			var = new HashMap<String,Object>();
		}
		service.complete(taskid, var);
	}
	/***
	 * 查询某人的所有公文待办
	 * --首页使用
	 */
	public List<Record> getToDoListByKey(String tableName ,String key,String username){
		String sql = "select * from v_tasklist t ,"+tableName+" b where t.INSID=b.proc_ins_id and  t.DEFKEY='"+key+"'";
		if(StrKit.notBlank(username)){
			sql = sql + " and (t.ASSIGNEE='"+username+"' or t.CANDIDATE='"+username+"')";
		}
		return Db.find(sql);
	}
	/***
	 * 查询某人的待办条目
	 * --管理页面使用
	 */
	public Page<Record> getToDoPageByKey(int pnum,int psize,String tableName,String key ,String username){
		String sql = "select * from v_tasklist t ,"+tableName+" b ";
		if(StrKit.notBlank(username)){
			sql = sql + " and (t.ASSIGNEE='"+username+"' or t.CANDIDATE='"+username+"')";
		}
		return Db.paginate(pnum, psize, "select * "," from v_tasklist t ,"+tableName+" b where t.INSID=b.proc_ins_id and  t.DEFKEY='"+key+"'");
	}
	/***
	 * 获取流转历史
	 */
	public List<Record> getHisTaskList(String insid){
		return Db.find("SELECT t.assignee_,	u.name,	t.name_,	t.end_time_,	c.message_ FROM	sys_user u ,	act_hi_taskinst t LEFT JOIN act_hi_comment c ON t.id_ = c.task_id_ where t.end_time_ IS NOT NULL AND u.username=t.ASSIGNEE_ AND t.proc_inst_id_ = '"+insid+"' ");
	}
	
	/***
	 * 获取流程经办人数据
	 */
	public List<Record> getHisTaskParter(String insid){
		return Db.find("select i.*,u.name from act_hi_identitylink i,sys_user u where u.username=i.USER_ID_ AND PROC_INST_ID_='"+insid+"'");
	}
	
	/***
	 * 删除流程实例
	 */
	public void deleteIns(String procid){
		ProcessEngine pe = ActivitiPlugin.buildProcessEngine();
		pe.getRuntimeService().deleteProcessInstance(procid, "删除流程实例");
    	pe.getHistoryService().deleteHistoricProcessInstance(procid);
	}
}
