package com.pointlion.mvc.admin.sys.schjob;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.mvc.common.model.SchJob;
import com.pointlion.mvc.common.model.SchJobExecute;
import com.pointlion.plugin.shiro.ShiroKit;

public class SchJobService{
	public static final SchJobService me = new SchJobService();
	public static final String EXE_TABLE_NAME = SchJobExecute.tableName;
	public static final String DCT_TABLE_NAME = SchJob.tableName;
	
	/***
	 * 获取执行类
	 */
	public SchJobExecute getExeById(String id){
		return SchJobExecute.dao.findById(id);
	}
	
	/***
	 * 获取工作
	 * @param id
	 * @return
	 */
	public SchJob getJobById(String id){
		return SchJob.dao.findById(id);
	}
	/***
	 * 获取分页
	 */
	public Page<Record> getExePage(int pnum,int psize){
		String sql  = " from "+DCT_TABLE_NAME+" j ,"+EXE_TABLE_NAME+" e where e.JOB_ID=j.ID ORDER BY e.CREATE_TIME desc";
		return Db.paginate(pnum, psize, " select * ", sql);
	}
	
	/***
	 * 删除执行
	 * @param ids
	 */
	@Before(Tx.class)
	public void deleteExeByIds(String ids){
		String idarr[] = ids.split(",");
		for(String id : idarr){
			SchJobExecute o = me.getExeById(id);
			o.delete();
		}
	}

	
	
	
	/***************************字典管理***********************/
	/***
	 * 获取分页
	 */
	public Page<Record> getJobPage(int pnum,int psize){
		String sql  = " from "+DCT_TABLE_NAME+" o order by o.CREATE_TIME desc";
		return Db.paginate(pnum, psize, " select * ", sql);
	}
	
	/***
	 * 删除
	 * @param ids
	 */
	@Before(Tx.class)
	public void deleteJobByIds(String ids){
		String idarr[] = ids.split(",");
		for(String id : idarr){
			SchJob o = me.getJobById(id);
			o.delete();
		}
	}
	
}