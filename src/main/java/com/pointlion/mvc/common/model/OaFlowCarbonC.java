package com.pointlion.mvc.common.model;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.mvc.common.model.base.BaseOaFlowCarbonC;
@SuppressWarnings("serial")
public class OaFlowCarbonC extends BaseOaFlowCarbonC<OaFlowCarbonC> {
	public static final OaFlowCarbonC dao = new OaFlowCarbonC();
	public static final String tableName = "oa_flow_carbon_c";
	
	/***
	 * 根据主键查询
	 */
	public OaFlowCarbonC getById(String id){
		return OaFlowCarbonC.dao.findById(id);
	}
	
	/***
	 * 删除
	 * @param ids
	 */
	@Before(Tx.class)
	public void deleteByIds(String ids){
    	String idarr[] = ids.split(",");
    	for(String id : idarr){
    		OaFlowCarbonC o = OaFlowCarbonC.dao.getById(id);
    		o.delete();
    	}
	}
	
}