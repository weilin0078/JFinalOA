package com.pointlion.mvc.admin.sys.dataauth;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pointlion.mvc.admin.sys.dataauth.bean.SysDataAuthRuleBean;
import com.pointlion.mvc.common.model.SysOrg;
import com.pointlion.mvc.common.model.SysUser;
import com.pointlion.mvc.common.utils.Constants;
import com.pointlion.plugin.shiro.ShiroKit;

/***
 * 数据权限，所有的变量，渲染
 * @author Administrator
 *
 */
public class SysDataAuthVariable {
	public static final SysDataAuthVariable me = new SysDataAuthVariable();
	/***
	 * @return
	 */
	public String transRuleVariable(SysDataAuthRuleBean rule){
		String value = rule.getValue();
		String userid = ShiroKit.getUserId();
		SysUser user = SysUser.dao.getById(userid);
		if(user!=null){
			//组装value的值---start
			if(value.indexOf(Constants.CurrentUser_Org_Id)>=0){//登录人机构id
				value = value.replace(Constants.CurrentUser_Org_Id, user.getOrgid());
			}
			if(value.indexOf(Constants.CurrentUser_Org_ParentChildCompanyId)>=0){//登录人子公司
				SysOrg org = SysOrg.dao.getById(user.getOrgid());
				if(org!=null){
					value = value.replace(Constants.CurrentUser_Org_ParentChildCompanyId, org.getParentChildCompanyId());
				}
			}
			if(value.indexOf(Constants.CurrentUser_Id)>=0){//登录人id
				value = value.replace(Constants.CurrentUser_Id, userid);
			}
			if(value.indexOf(Constants.CurrentUser_Org_AllChildId)>=0){//登录人递归子公司(这种情况必须是in)
				List<SysOrg> list = SysOrg.dao.getChildrenAllTree(user.getOrgid());//查询出来的不包括自己
				list.add(SysOrg.dao.getById(user.getOrgid()));//将自己的部门添加进去
				List<String> idList = new ArrayList<String>();
				for(SysOrg org:list){
					idList.add(org.getId());
				}
				if(idList.size()>0){
					if("string".equals(rule.getType())){
						value = "('"+StringUtils.join(idList,"','")+"')";//后面左右两边的单引号会给补上
					}else{
						value = "("+StringUtils.join(idList,",")+")";
					}
				}else{
					value = "";
				}
			}
		}
		//组装value的值---end
		//组装数据类型---start
		if(!"in".equals(rule.getOperation().trim())){//除了要用in的情况
			if("string".equals(rule.getType())){
				value = "'"+value+"'";
			}
		}
		//组装数据类型---end
		return value;
	}
}
