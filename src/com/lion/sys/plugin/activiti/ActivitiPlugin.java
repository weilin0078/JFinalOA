/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.lion.sys.plugin.activiti;

import java.io.UnsupportedEncodingException;

import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.activerecord.DbKit;

/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
public class ActivitiPlugin implements IPlugin{

	private static ProcessEngine processEngine = null;
	private static ProcessEngineConfiguration processEngineConfiguration = null;
	private boolean isStarted = false;
	@Override
	public boolean start(){
		try {
			createProcessEngine();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean stop() {
		ProcessEngines.destroy(); 
		isStarted = false;
		return true;
	}

	private Boolean createProcessEngine() throws Exception{
		if (isStarted) {
			return true;
		}
		StandaloneProcessEngineConfiguration conf = (StandaloneProcessEngineConfiguration) ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		conf.setDataSource(DbKit.getConfig().getDataSource());
		conf.setEnableDatabaseEventLogging(false);
		conf.setDatabaseSchemaUpdate(ProcessEngineConfigurationImpl.DB_SCHEMA_UPDATE_TRUE);//更新
//		conf.setDatabaseSchemaUpdate(ProcessEngineConfigurationImpl.DB_SCHEMA_UPDATE_DROP_CREATE);//重置数据库!!!调试用!!!请勿打开!!!
		conf.setDbHistoryUsed(true);
//		conf.setTransactionsExternallyManaged(true); // 使用托管事务工厂
		conf.setTransactionFactory(new ActivitiTransactionFactory());
		UuidGenerator uuidG = new UuidGenerator();
		conf.setIdGenerator(uuidG);
		ActivitiPlugin.processEngine = conf.buildProcessEngine();
		isStarted = true;
		//开启流程引擎
		System.out.println("启动流程引擎.......");
		createModel(processEngine);
		return isStarted;
	}

	// 开启流程服务引擎
	public static ProcessEngine buildProcessEngine() {
		if (processEngine == null)
			if (processEngineConfiguration != null) {
				processEngine = processEngineConfiguration.buildProcessEngine();
			}
			return processEngine;
	}
	/**
	 * 创建新模型
	 * @throws UnsupportedEncodingException 
	 * */
	public void createModel(ProcessEngine pe) throws UnsupportedEncodingException{
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
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, "模型名称");
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        String description = StringUtils.defaultString("模型描述信息");
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelData.setMetaInfo(modelObjectNode.toString());
        modelData.setName("模型名称");
        modelData.setKey(StringUtils.defaultString("Urge"));

        repositoryService.saveModel(modelData);
        repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
	}
}
