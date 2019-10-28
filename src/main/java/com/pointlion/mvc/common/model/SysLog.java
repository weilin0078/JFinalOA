package com.pointlion.mvc.common.model;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.mvc.common.model.base.BaseSysLog;
@SuppressWarnings("serial")
public class SysLog extends BaseSysLog<SysLog> {
	public static final SysLog dao = new SysLog();
	public static final String tableName = "sys_log";
	
	/***
	 * 根据主键查询
	 */
	public SysLog getById(String id){
		return SysLog.dao.findById(id);
	}
	
	/***
	 * 删除
	 * @param ids
	 */
	@Before(Tx.class)
	public void deleteByIds(String ids){
    	String idarr[] = ids.split(",");
    	for(String id : idarr){
    		SysLog o = SysLog.dao.getById(id);
    		o.delete();
    	}
	}
	
}