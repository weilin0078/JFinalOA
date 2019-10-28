package com.pointlion.mvc.common.model;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.mvc.common.model.base.BaseOaApplySeal;
@SuppressWarnings("serial")
public class OaApplySeal extends BaseOaApplySeal<OaApplySeal> {
	public static final OaApplySeal dao = new OaApplySeal();
	public static final String tableName = "oa_apply_seal";
	
	/***
	 * 根据主键查询
	 */
	public OaApplySeal getById(String id){
		return OaApplySeal.dao.findById(id);
	}
	
	/***
	 * 删除
	 * @param ids
	 */
	@Before(Tx.class)
	public void deleteByIds(String ids){
    	String idarr[] = ids.split(",");
    	for(String id : idarr){
    		OaApplySeal o = OaApplySeal.dao.getById(id);
    		o.delete();
    	}
	}
	
}