package com.pointlion.sys.mvc.admin.usecar;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.sys.mvc.common.model.OaUseCar;

public class OaUseCarService{
	public static final OaUseCarService me = new OaUseCarService();
	public static final String TABLE_NAME = "oa_use_car";
	
	/***
	 * 根据主键查询
	 */
	public OaUseCar getById(String id){
		return OaUseCar.dao.findById(id);
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
    		OaUseCar o = me.getById(id);
    		o.delete();
    	}
	}
	
}