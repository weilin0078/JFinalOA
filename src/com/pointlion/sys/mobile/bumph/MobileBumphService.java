package com.pointlion.sys.mobile.bumph;

import java.util.ArrayList;
import java.util.List;

import com.pointlion.sys.mvc.bumph.BumphConstants;
import com.pointlion.sys.mvc.bumph.BumphService;
import com.pointlion.sys.mvc.bumph.OaBumph;
import com.pointlion.sys.mvc.bumph.OaBumphOrg;
import com.pointlion.sys.tool.StringUtil;

/***
 * 手机端通知公告调用服务
 * @author Administrator
 *
 */
public class MobileBumphService extends BumphService{
	public static final MobileBumphService me = new MobileBumphService();
	
	public OaBumph getDoBumphTask(String id){
		OaBumph bumph = OaBumph.dao.findById(id);
		List<OaBumphOrg> flist = OaBumphOrg.dao.getList(id,BumphConstants.TYPE_ZHUSONG);
		List<String> f = new ArrayList<String>();
		List<String> fn = new ArrayList<String>();
		for(OaBumphOrg fo : flist){
			f.add(fo.getOrgid());
			fn.add(fo.getOrgname());
		}
		bumph.put("firstOrgName", StringUtil.join(fn,","));
		//获取所有抄送单位
		List<OaBumphOrg> slist = OaBumphOrg.dao.getList(id,BumphConstants.TYPE_CHAOSONG);
		List<String> s = new ArrayList<String>();
		List<String> sn = new ArrayList<String>();
		for(OaBumphOrg so : slist){
			s.add(so.getOrgid());
			sn.add(so.getOrgname());
		}
		bumph.put("secondOrgName", StringUtil.join(sn,","));
		return bumph;
	}
}
