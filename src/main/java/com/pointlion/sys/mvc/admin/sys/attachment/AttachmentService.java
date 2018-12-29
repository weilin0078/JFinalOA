package com.pointlion.sys.mvc.admin.sys.attachment;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.sys.mvc.common.model.SysAttachment;

public class AttachmentService{
	public static final AttachmentService me = new AttachmentService();
	public static final String TABLE_NAME = SysAttachment.tableName;
	
	/***
	 * 根据主键查询
	 */
	public SysAttachment getById(String id){
		return SysAttachment.dao.findById(id);
	}
	
	/***
	 * 获取分页
	 */
	public Page<Record> getPage(String busid,int pnum,int psize){
		String sql  = " from "+TABLE_NAME+" o where 1=1 ";
		if(StrKit.notBlank(busid)){
			sql = sql + " and o.business_id='"+busid+"'";
		}
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
    		SysAttachment o = me.getById(id);
    		o.delete();
    	}
	}
	
	/***
	 * 获取附件数量
	 */
	public Integer getBusinessAttachmentCount(String busid){
		Record r = Db.findFirst("select count(*) c from sys_attachment a where a.business_id='"+busid+"'");
		Integer c = r.getInt("c");
		return c;
	}
	
}