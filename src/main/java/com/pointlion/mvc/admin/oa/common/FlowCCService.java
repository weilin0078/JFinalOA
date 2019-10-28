package com.pointlion.mvc.admin.oa.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.pointlion.mvc.common.model.OaFlowCarbonC;
import com.pointlion.mvc.common.utils.DateUtil;
import com.pointlion.mvc.common.utils.UuidUtil;

public class FlowCCService {
	public static final FlowCCService me = new FlowCCService();
	
	/***
	 * 添加流程抄送(包含更新)
	 */
	public void addFlowCC(Controller c,String businessId,String defkey,String tablename){
		Db.update("delete from oa_flow_carbon_c where business_id = '"+businessId+"' ");
		String flowCC = c.getPara("flowCC");
		if(StrKit.notBlank(flowCC)){
			String useridArr[] = flowCC.split(",");
			for(String userid:useridArr){
				OaFlowCarbonC cc = new OaFlowCarbonC();
				cc.setId(UuidUtil.getUUID());
				cc.setBusinessId(businessId);
				cc.setCreateTime(DateUtil.getCurrentTime());
				cc.setUserId(userid);
				cc.setDefkey(defkey);
				cc.setTableName(tablename);
				cc.save();
			}
		}
	}
	
	/***
	 * 添加流程抄送(包含更新)
	 */
	public void setAttrFlowCC(Controller c,String businessId,String defkey){
		List<OaFlowCarbonC> list = OaFlowCarbonC.dao.find("select * from oa_flow_carbon_c c ,sys_user u  where u.id=c.user_id and business_id = '"+businessId+"' ");
		List<String> idList = new ArrayList<String>();
		List<String> nameList = new ArrayList<String>();
		 for(OaFlowCarbonC cc : list){
			 idList.add(cc.getUserId());
			 nameList.add(cc.getStr("name"));
		 }
		c.setAttr("flowCC", StringUtils.join(idList,","));
		c.setAttr("flowCCName", StringUtils.join(nameList,","));
	}
}
