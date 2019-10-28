package com.pointlion.mvc.admin.sys.upload;

import java.util.List;

import com.pointlion.mvc.common.model.SysRole;
import com.pointlion.mvc.common.utils.PinYinUtil;
import com.pointlion.mvc.common.utils.UuidUtil;

public class SysRoleImportService {
	public static final SysRoleImportService me = new SysRoleImportService();
	
	public void importRole(List<List<String>> list){
		for(List<String> rows:list){
				String roleName = rows.get(5);//角色
				if(roleName.indexOf("总")>0){
					roleName = "总经理";
				}else if(roleName.indexOf("经理")>0){
					roleName = "经理";
				}else if(roleName.indexOf("事")>0){
					roleName = "人事";
				}else if(roleName.indexOf("财务主管")>0){
					roleName = "财务主管";
				}else if(roleName.indexOf("财务")>0){
					roleName = "财务主管";
				}
				List<SysRole> roleList = SysRole.dao.getRoleByRoleName(roleName);//
				if(roleList.size()==0){//角色没有
					SysRole r = new SysRole();
					r.setId(UuidUtil.getUUID());
					r.setName(roleName);
					r.setKey(PinYinUtil.getPingYin(roleName));
					r.setDescription(roleName);
					r.save();
				}
		}
	}
}
