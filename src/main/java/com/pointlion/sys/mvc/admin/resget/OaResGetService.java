package com.pointlion.sys.mvc.admin.resget;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.sys.mvc.admin.workflow.WorkFlowService;
import com.pointlion.sys.mvc.common.model.OaResGet;
import com.pointlion.sys.plugin.activiti.ActivitiPlugin;

public class OaResGetService{
	public static final OaResGetService me = new OaResGetService();
	
	/***
	 * 根据主键查询
	 */
	public OaResGet getById(String id){
		return OaResGet.dao.findById(id);
	}
	
	/***
	 * 获取分页
	 */
	public Page<Record> getPage(int pnum,int psize){
		String sql  = " from oa_res_get o ";
		return Db.paginate(pnum, psize, " select * ", sql);
	}
	
	/***
	 * 删除
	 * @param ids
	 */
	@Before(Tx.class)
	public void deleteByIds(String ids){
    	String idarr[] = ids.split(",");
    	for(String id : idarr){
    		OaResGet o = me.getById(id);
    		o.delete();
    	}
	}
	
	/***
	 * 启动流程
	 */
	@Before(Tx.class)
	public void startProcess(String id){
		WorkFlowService service = new WorkFlowService();
		Map<String, Object> var = new HashMap<String,Object>();
		String procInsId = service.startProcess(id, OaResGetConstants.DEFKEY_RESGET,var);
		OaResGet resget = OaResGet.dao.findById(id);
		resget.setProcInsId(procInsId);
		resget.setIfSubmit(OaResGetConstants.IF_SUBMIT_YES);
		resget.update();
	}
	/***
	 * 撤回
	 */
	@Before(Tx.class)
	public void callBack(String id){
		OaResGet resget = OaResGet.dao.findById(id);
		String procid = resget.getProcInsId();//流程实例id
    	resget.setProcInsId("");
    	resget.setIfSubmit(OaResGetConstants.IF_SUBMIT_NO);
    	resget.setIfComplete(OaResGetConstants.IF_COMPLETE_NO);
    	resget.update();
    	ProcessEngine pe = ActivitiPlugin.buildProcessEngine();
    	pe.getRuntimeService().deleteProcessInstance(procid, "删除流程实例");
    	pe.getHistoryService().deleteHistoricProcessInstance(procid);
	}
	
}