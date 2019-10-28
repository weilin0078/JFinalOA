package com.pointlion.mvc.common.model;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.mvc.common.model.base.BaseSchJobExecute;
@SuppressWarnings("serial")
public class SchJobExecute extends BaseSchJobExecute<SchJobExecute> {
	public static final SchJobExecute dao = new SchJobExecute();
	public static final String tableName = "sch_job_execute";
	
	/***
	 * 根据主键查询
	 */
	public SchJobExecute getById(String id){
		return SchJobExecute.dao.findById(id);
	}
	
	/***
	 * 删除
	 * @param ids
	 */
	@Before(Tx.class)
	public void deleteByIds(String ids){
    	String idarr[] = ids.split(",");
    	for(String id : idarr){
    		SchJobExecute o = SchJobExecute.dao.getById(id);
    		o.delete();
    	}
	}
	
}