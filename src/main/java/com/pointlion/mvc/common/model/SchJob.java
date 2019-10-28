package com.pointlion.mvc.common.model;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.mvc.common.model.base.BaseSchJob;
@SuppressWarnings("serial")
public class SchJob extends BaseSchJob<SchJob> {
	public static final SchJob dao = new SchJob();
	public static final String tableName = "SCH_JOB";
	
	/***
	 * 根据主键查询
	 */
	public SchJob getById(String id){
		return SchJob.dao.findById(id);
	}
	
	/***
	 * 删除
	 * @param ids
	 */
	@Before(Tx.class)
	public void deleteByIds(String ids){
    	String idarr[] = ids.split(",");
    	for(String id : idarr){
    		SchJob o = SchJob.dao.getById(id);
    		o.delete();
    	}
	}
	
}