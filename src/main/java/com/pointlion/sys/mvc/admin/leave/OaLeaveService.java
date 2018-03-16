package com.pointlion.sys.mvc.admin.leave;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.sys.mvc.common.model.OaLeave;

public class OaLeaveService{
	public static final OaLeaveService me = new OaLeaveService();
	public static final String TABLE_NAME = "oa_leave";
	
	/***
	 * 根据主键查询
	 */
	public OaLeave getById(String id){
		return OaLeave.dao.findById(id);
	}
	
	/***
	 * 获取分页
	 */
	public Page<Record> getPage(int pnum,int psize){
		String sql  = " from "+TABLE_NAME+" o ";
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
    		OaLeave o = me.getById(id);
    		o.delete();
    	}
	}
	
}