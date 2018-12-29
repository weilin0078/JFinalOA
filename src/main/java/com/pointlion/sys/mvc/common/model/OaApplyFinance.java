package com.pointlion.sys.mvc.common.model;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.sys.mvc.common.model.base.BaseOaApplyFinance;
@SuppressWarnings("serial")
public class OaApplyFinance extends BaseOaApplyFinance<OaApplyFinance> {
	public static final OaApplyFinance dao = new OaApplyFinance();
	public static final String tableName = "oa_apply_finance";
	
	/***
	 * 根据主键查询
	 */
	public OaApplyFinance getById(String id){
		return OaApplyFinance.dao.findById(id);
	}
	
	/***
	 * 获取分页
	 */
	public Page<Record> getPage(int pnum,int psize){
		String sql  = " from "+tableName+" o ";
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
    		OaApplyFinance o = OaApplyFinance.dao.getById(id);
    		o.delete();
    	}
	}
	
}