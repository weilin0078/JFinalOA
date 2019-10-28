
package com.pointlion.mvc.common.model;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.mvc.common.model.base.BaseSysDct;
@SuppressWarnings("serial")
public class SysDct extends BaseSysDct<SysDct> {
	public static final SysDct dao = new SysDct();
	public static final String tableName = "sys_dct";
	public static final String groupTableName = SysDctGroup.tableName;
	
	/***
	 * 根据主键查询
	 */
	public SysDct getById(String id){
		return SysDct.dao.findById(id);
	}
	
	/***
	 * 删除
	 * @param ids
	 */
	@Before(Tx.class)
	public void deleteByIds(String ids){
    	String idarr[] = ids.split(",");
    	for(String id : idarr){
    		SysDct o = SysDct.dao.getById(id);
    		o.delete();
    	}
	}
	
	/***
	 * 查询某个分组下的所有字典
	 */
	public List<SysDct> getDctByGroupId(String groupId){
		return dao.find("select * from "+ tableName + " where group_id='"+groupId+"'");
	}
	
	/***
	 * 查询父级分组下所有分组
	 * @return
	 */
	public List<SysDctGroup> getGroupListByParentId(String groupId){
		return SysDctGroup.dao.find("select * from "+groupTableName+" where parent_id='"+groupId+"'");
	}
	
	/***
	 * 根据类型和键查询字典
	 * @return
	 */
	public SysDct getByKeyAndType(String key,String type){
		return dao.findFirst("select * from sys_dct d where d.type='"+type+"' and d.key='"+key+"'");
	}

	/***
	 * 根据类型和值查询字典
	 * @return
	 */
	public SysDct getByValueAndType(String value,String type){
		return dao.findFirst("select * from sys_dct d where d.type='"+type+"' and d.value='"+value+"'");
	}
	
	/***
	 * 查询某个类型的所有字典
	 * @param key
	 * @param type
	 * @return
	 */
	public List<SysDct> getByType(String type){
		return dao.find("select * from sys_dct d where d.type='"+type+"'");
	}
}