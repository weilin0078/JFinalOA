package com.pointlion.mvc.admin.sys.log;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.mvc.common.model.SysLog;

public class SysLogService{
	public static final SysLogService me = new SysLogService();
	public static final String TABLE_NAME = SysLog.tableName;
	
	/***
	 * 根据主键查询
	 */
	public SysLog getById(String id){
		return SysLog.dao.findById(id);
	}
	
	/***
	 * 获取分页
	 */
	public Page<Record> getPage(int pnum,int psize){
		String sql  = " from "+TABLE_NAME+" o order by o.create_time desc";
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
    		SysLog o = me.getById(id);
    		o.delete();
    	}
	}
	
}