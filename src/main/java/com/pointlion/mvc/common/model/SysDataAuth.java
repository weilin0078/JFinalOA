package com.pointlion.mvc.common.model;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.mvc.common.model.base.BaseSysDataAuth;
@SuppressWarnings("serial")
public class SysDataAuth extends BaseSysDataAuth<SysDataAuth> {
	public static final SysDataAuth dao = new SysDataAuth();
	public static final String tableName = "sys_data_auth_rule";
	
	/***
	 * 根据主键查询
	 */
	public SysDataAuth getById(String id){
		return SysDataAuth.dao.findById(id);
	}
	
	/***
	 * 删除
	 * @param ids
	 */
	@Before(Tx.class)
	public void deleteByIds(String ids){
    	String idarr[] = ids.split(",");
    	for(String id : idarr){
    		SysDataAuth o = SysDataAuth.dao.getById(id);
    		o.delete();
    	}
	}
	
	/***
	 * 根据表名获取所有的规则
	 */
	public List<SysDataAuth> getAllRuleByTableAndRoleid(String table,String roleid){
		return SysDataAuth.dao.find("select * from "+tableName+" rule where rule.table='"+table+"' and rule.role_id='"+roleid+"'");
	}
}