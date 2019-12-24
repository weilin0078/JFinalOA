package com.pointlion.mvc.common.model;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.mvc.common.model.base.BaseSysRoleOrg;
import com.pointlion.mvc.common.utils.Constants;
import com.pointlion.mvc.common.utils.ListUtil;
import com.pointlion.plugin.shiro.ShiroKit;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class SysRoleOrg extends BaseSysRoleOrg<SysRoleOrg> {
	public static final SysRoleOrg dao = new SysRoleOrg();
	public static final String tableName = "sys_role_org";

	public SysRoleOrg getById(String id){
		return SysRoleOrg.dao.findById(id);
	}

	@Before(Tx.class)
	public void deleteByIds(String ids){
		String idarr[] = ids.split(",");
		for(String id : idarr){
			SysRoleOrg o = SysRoleOrg.dao.getById(id);
			o.delete();
		}
	}

	/***
	 * 根据用户id，动态组装数据权限所需要的sql
	 * @param userId
	 * @return
	 */
	public String getRoleOrgSql(String userId){
		String sql = " and ";
		//获取权限最大的角色
		List<String> scopeList = SysRole.dao.getDataScopeListByUserId(userId);
		List<String> sqlList = new ArrayList<String>();
		if(ListUtil.ifContain(scopeList, Constants.SYS_ROLE_SCOPE_MYSELF)){//查看自己
			sqlList.add(" o.userid= '"+userId+"' ");
		}
		if(ListUtil.ifContain(scopeList, Constants.SYS_ROLE_SCOPE_MYORG)){//查看自己部门的
			sqlList.add(" o.org_id= '"+ ShiroKit.getUserOrgId() +"' ");
		}
		if(ListUtil.ifContain(scopeList, Constants.SYS_ROLE_SCOPE_MYORG_AND_CHILDREN)){//查看自己部门以及下级部门的
			SysOrg myOrg = ShiroKit.getUserOrg();
			//该查询查不出自己
			List<SysOrg> orgList = SysOrg.dao.getAllChildren(myOrg.getId());
			List<String> orgIdList = new ArrayList<String>();
			for(SysOrg org : orgList){
				orgIdList.add(org.getId());
			}
			//将自己添加
			orgIdList.add(myOrg.getId());
			sqlList.add(" o.org_id in ('"+StringUtils.join(orgIdList,"','")+"') ");
		}
		if(ListUtil.ifContain(scopeList, Constants.SYS_ROLE_SCOPE_CUSTOM)){//自定义的权限
			List<String> orgIdList = new ArrayList<String>();
			List<SysRoleOrg> roleOrgList = dao.getByUserId(userId);
			SysOrg myOrg = ShiroKit.getUserOrg();
			for(SysRoleOrg sysRoleOrg : roleOrgList){
				orgIdList.add(sysRoleOrg.getOrgId());
			}
			sqlList.add(" o.org_id in ('"+StringUtils.join(orgIdList,"','")+"') ");
		}
		if(ListUtil.ifContain(scopeList, Constants.SYS_ROLE_SCOPE_ALL)){//全部的权限
			sqlList.add(" 1=1 ");
		}
		return sql + "("+StringUtils.join(sqlList," or ")+")";
	}

	/***
	 * 根据用户id查询自定义数据权限
	 * @param userId
	 * @return
	 */
	public List<SysRoleOrg> getByUserId(String userId){
		return dao.find("SELECT o.* FROM sys_role r,sys_role_user ur,sys_user u,sys_role_org o WHERE ur.user_id = u.id AND ur.role_id = r.id AND u.id = '"+userId+"' AND r.data_scope = '"+ Constants.SYS_ROLE_SCOPE_CUSTOM+"' AND r.id = o.role_id");
	}

	/***
	 * 根据角色id 删除所有自定义数据权限
	 * @param roleId
	 */
	public void deleteByRoleId(String roleId){
		Db.update("delete from "+tableName+" where role_id='"+roleId+"'");
	}


}