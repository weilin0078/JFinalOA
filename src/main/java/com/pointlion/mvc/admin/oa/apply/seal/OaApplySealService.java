package com.pointlion.mvc.admin.oa.apply.seal;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.mvc.admin.sys.dataauth.SysDataAuthTranslator;
import com.pointlion.mvc.common.model.OaApplySeal;

public class OaApplySealService{
	public static final OaApplySealService me = new OaApplySealService();
	private static final String TABLE_NAME = OaApplySeal.tableName;
	
	/***
	 * 根据主键查询
	 */
	public OaApplySeal getById(String id){
		return OaApplySeal.dao.findById(id);
	}
	
	/***
	 * 获取分页
	 */
	public Page<Record> getPage(int pnum,int psize){
		String dataAuth = SysDataAuthTranslator.me.translator(TABLE_NAME);
		String sql  = " from "+dataAuth+" o LEFT JOIN act_hi_procinst p ON o.proc_ins_id=p.ID_ where 1=1 order by o.create_time desc";
		return Db.paginate(pnum, psize, " select o.*,p.PROC_DEF_ID_ defid ", sql);
	}
	
	/***
	 * 删除
	 * @param ids
	 */
	@Before(Tx.class)
	public void deleteByIds(String ids){
    	String idarr[] = ids.split(",");
    	for(String id : idarr){
    		OaApplySeal o = me.getById(id);
    		o.delete();
    	}
	}
	
}