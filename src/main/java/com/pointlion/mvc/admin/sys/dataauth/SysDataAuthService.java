package com.pointlion.mvc.admin.sys.dataauth;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.mvc.common.model.SysDataAuth;

public class SysDataAuthService{
	public static final SysDataAuthService me = new SysDataAuthService();
	public static final String TABLE_NAME = SysDataAuth.tableName;
	
	/***
	 * 根据主键查询
	 */
	public SysDataAuth getById(String id){
		return SysDataAuth.dao.findById(id);
	}
	
	/***
	 * 获取分页
	 */
	public Page<Record> getPage(int pnum,int psize){
		String sql  = " from "+TABLE_NAME+" o   order by o.create_time desc";
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
    		SysDataAuth o = me.getById(id);
    		o.delete();
    	}
	}
	
	/***
	 * 获取所有表名
	 */
	public List<Record> getAllTables(){
		return Db.find("select table_name from information_schema.tables where table_schema=?",PropKit.get("dbname"));
	}
	
	/***
	 * 查询所有列名
	 * @param tableName
	 * @return
	 */
	public List<Record> getAllCols(String tableName){
		return Db.find("select data_type,character_maximum_length,column_name,column_comment,table_name from INFORMATION_SCHEMA.Columns where table_name=? and table_schema=?",tableName,PropKit.get("dbname"));
	}
	
}