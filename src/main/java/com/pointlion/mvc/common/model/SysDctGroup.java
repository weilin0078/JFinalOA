package com.pointlion.mvc.common.model;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.mvc.common.model.base.BaseSysDctGroup;
@SuppressWarnings("serial")
public class SysDctGroup extends BaseSysDctGroup<SysDctGroup> {
	public static final SysDctGroup dao = new SysDctGroup();
	public static final String tableName = "sys_dct_group";
	
	/***
	 * 根据主键查询
	 */
	public SysDctGroup getById(String id){
		return SysDctGroup.dao.findById(id);
	}
	
	/***
	 * 删除
	 * @param ids
	 */
	@Before(Tx.class)
	public void deleteByIds(String ids){
    	String idarr[] = ids.split(",");
    	for(String id : idarr){
    		SysDctGroup o = SysDctGroup.dao.getById(id);
    		o.delete();
    	}
	}
	
	/***
	 * 查询某个分组下的所有字典
	 */
	public SysDctGroup getByKey(String key){
		return dao.findFirst("select * from "+tableName+" d where d.key='"+key+"'");
	}
	
}