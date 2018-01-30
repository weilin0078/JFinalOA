package com.pointlion.sys.mvc.admin.resget;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.sys.mvc.common.model.OaResGet;

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
	
}