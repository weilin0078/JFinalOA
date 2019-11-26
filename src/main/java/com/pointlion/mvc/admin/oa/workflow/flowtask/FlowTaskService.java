package com.pointlion.mvc.admin.oa.workflow.flowtask;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.pointlion.mvc.admin.oa.workflow.WorkFlowUtil;
import com.pointlion.mvc.common.model.OaApplyCustom;

public class FlowTaskService{
	public static final FlowTaskService me = new FlowTaskService();
	

	/***
	 * 获取固定流程待办任务对象
	 */
	public Record getTaskObject(String taskid,String defKey){
		String tableName = WorkFlowUtil.getTablenameByDefkey(defKey);
		Record o = Db.findFirst("select * from "+tableName+" o , v_tasklist t where t.INSID = o.proc_ins_id and t.TASKID='"+taskid+"'");
		return o;
	}
	/***
	 * 获取自定义流程待办任务对象
	 */
	public Record getCustomTaskObject(String taskid,String defKey){
		String tableName = OaApplyCustom.tableName;
		Record o = Db.findFirst("select * from "+tableName+" o , v_tasklist t where t.INSID = o.proc_ins_id and t.TASKID='"+taskid+"'");
		return o;
	}
	/***
	 * 获取已办任务对象
	 */
	public Record getBusinessObject(String id,String defkey){
		String tableName = WorkFlowUtil.getTablenameByDefkey(defkey);
		if(StrKit.isBlank(tableName)){
			tableName = OaApplyCustom.tableName;
		}
		Record o = Db.findFirst("select * from "+tableName+" o where o.id='"+id+"'");
		return o;
	}
	
	/***
	 * 获取已经办理流程DefKey集合
	 */
	public List<String> getHavedoneDefkeyList(String username){
		List<String> havedoneKeyList = new ArrayList<String>();
    	List<Record> list = Db.find("select KEY_ DEFKEY from act_hi_taskinst t ,act_re_procdef d where d.ID_=t.PROC_DEF_ID_ and t.ASSIGNEE_='"+username+"' GROUP BY d.KEY_");//所有已办数据类型
    	for(Record r:list){
    		havedoneKeyList.add(r.getStr("DEFKEY"));
    	}
    	return havedoneKeyList;
	}

	
	/***
	 * 
	 * 启动流程图的时候，写入固定的流程变量。根据流程图不同。
	 */
//	@SuppressWarnings("rawtypes")
//	public Map<String, Object> setAttrByDefKey(String defKey,Model o){
//		Map<String, Object> var = new HashMap<String, Object>();
//		if(defKey.equals(OAConstants.DEFKEY_APPLY_COST)){//如果是报销的时候
//			String money = o.getStr("money");
//	    	if(StrKit.notBlank(money)){
//	    		Integer m = Integer.parseInt(money);
//	    		var.put("money",m);
//	    	}
//		}
//    	return var;
//	}
}