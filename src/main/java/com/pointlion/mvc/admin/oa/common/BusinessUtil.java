package com.pointlion.mvc.admin.oa.common;

import java.util.Date;

import com.jfinal.plugin.activerecord.Model;
import com.pointlion.mvc.common.model.SysOrg;
import com.pointlion.mvc.common.utils.DateUtil;
import com.pointlion.mvc.common.utils.PinYinUtil;
import com.pointlion.mvc.common.utils.StringUtil;

public class BusinessUtil {
	
	
	/***
	 * 生成编号
	 * @param username
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Integer getMaxNum(Model m){
		//生成编号规则
		return 1;
	}
	
	
	
	/***
	 * 编号。分公司简称+自增编号
	 * @param username
	 * @return
	 */
	public static String getAddNum(Integer num,String username){
		String cnum = "BUSINESS001";//默认编号
		SysOrg org = SysOrg.dao.getByUsername(username);
		if(org!=null){
			if("0".equals(org.getType())){//部门
				String pcid = org.getParentChildCompanyId();
				SysOrg pcorg = SysOrg.dao.getById(pcid);
				if(pcorg!=null){
					String name = pcorg.getName();
					cnum = PinYinUtil.converterToFirstSpell(name)+DateUtil.format(new Date(), "yyyyMM")+StringUtil.addZeroForNum(num+"", 3, "left");
				}
			}else if("1".equals(org.getType())){//公司
				String name = org.getName();
				cnum = PinYinUtil.converterToFirstSpell(name)+DateUtil.format(new Date(), "yyyyMM")+StringUtil.addZeroForNum(num+"", 3, "left");
			}
			
		}
		return cnum;
	}
}
