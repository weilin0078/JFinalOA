package com.pointlion.mvc.common.model;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.mvc.common.model.base.BaseSysMobileMessage;
@SuppressWarnings("serial")
public class SysMobileMessage extends BaseSysMobileMessage<SysMobileMessage> {
	public static final SysMobileMessage dao = new SysMobileMessage();
	public static final String tableName = "sys_mobile_message";
	
	/***
	 * 根据主键查询
	 */
	public SysMobileMessage getById(String id){
		return SysMobileMessage.dao.findById(id);
	}
	
	/***
	 * 删除
	 * @param ids
	 */
	@Before(Tx.class)
	public void deleteByIds(String ids){
    	String idarr[] = ids.split(",");
    	for(String id : idarr){
    		SysMobileMessage o = SysMobileMessage.dao.getById(id);
    		o.delete();
    	}
	}
	
}