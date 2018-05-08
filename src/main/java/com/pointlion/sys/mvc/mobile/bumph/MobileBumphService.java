package com.pointlion.sys.mvc.mobile.bumph;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.pointlion.sys.mvc.admin.bumph.BumphConstants;
import com.pointlion.sys.mvc.admin.bumph.BumphService;
import com.pointlion.sys.mvc.common.model.OaBumphOrg;
import com.pointlion.sys.mvc.common.utils.StringUtil;

/***
 * 手机端通知公告调用服务
 * @author Administrator
 *
 */
public class MobileBumphService extends BumphService{
	public static final MobileBumphService me = new MobileBumphService();
	
	/***
	 * 获取手机端办理任务接口
	 * @param id
	 * @return
	 */
	public Record getDoBumphTask(String taskid){
		Record bumph = Db.findFirst("select * from v_tasklist t , oa_bumph b where t.TASKID=? and b.proc_ins_id=t.INSID",taskid);
		String id = bumph.getStr("id");
		List<OaBumphOrg> flist = OaBumphOrg.dao.getList(id,BumphConstants.TYPE_ZHUSONG);
		List<String> f = new ArrayList<String>();
		List<String> fn = new ArrayList<String>();
		for(OaBumphOrg fo : flist){
			f.add(fo.getOrgid());
			fn.add(fo.getOrgname());
		}
		bumph.set("firstOrgName", StringUtil.join(fn,","));
		//获取所有抄送单位
		List<OaBumphOrg> slist = OaBumphOrg.dao.getList(id,BumphConstants.TYPE_CHAOSONG);
		List<String> s = new ArrayList<String>();
		List<String> sn = new ArrayList<String>();
		for(OaBumphOrg so : slist){
			s.add(so.getOrgid());
			sn.add(so.getOrgname());
		}
		bumph.set("secondOrgName", StringUtil.join(sn,","));
		return bumph;
	}
}
