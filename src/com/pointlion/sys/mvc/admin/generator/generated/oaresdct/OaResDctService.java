package com.pointlion.sys.mvc.admin.generator.generated.oaresdct;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.sys.mvc.common.model.OaResDct;

@SuppressWarnings("serial")
public class OaResDctService{
	public static final OaResDctService me = new OaResDctService();
	
	/***
	 * 根据主键查询
	 */
	public OaResDct getById(String id){
		return OaResDct.dao.findById(id);
	}
	
	/***
	 * 获取分页
	 */
	public Page<Record> getPage(int pnum,int psize){
		String sql  = " from oa_res_dct o ";
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
    		OaResDct o = me.getById(id);
    		o.delete();
    	}
	}
	
}