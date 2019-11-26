package com.pointlion.mvc.admin.sys.upload;

import java.util.List;

import com.pointlion.mvc.common.model.SysRole;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;

import com.jfinal.kit.StrKit;
import com.pointlion.mvc.admin.oa.workflow.WorkFlowIdentityService;
import com.pointlion.mvc.common.model.SysOrg;
import com.pointlion.mvc.common.model.SysUser;
import com.pointlion.mvc.common.model.SysRoleUser;
import com.pointlion.mvc.common.utils.PinYinUtil;
import com.pointlion.mvc.common.utils.UuidUtil;

public class SysUserImportService {
	public static final SysUserImportService me = new SysUserImportService();
	
	public void importUser(List<List<String>> list){
		for(List<String> rows:list){
				String userid = UuidUtil.getUUID();
				String name = rows.get(4).trim();//用户
				String sex = rows.get(6).trim();//性别
				String birthDate = rows.get(7).trim();//日期
				
				String arr[] = birthDate.split("-");
				try{
					if(arr[1].length()==1){
						arr[1] = "0"+arr[1];
					}
					if(arr[2].length()==1){
						arr[2] = "0"+arr[2];
					}
					birthDate = StringUtils.join(arr, "-");
				}catch(Exception e){
					birthDate = "";
				}
				String card = "888888888888888888";//身份证号
				String mobile = "18888888888";//手机号
				String password = mobile.substring(mobile.length()-6);
				String da = rows.get(10).trim();//入职日期
				try{
					String arrda[] = da.split("-");
					if(arrda[1].length()==1){
						arrda[1] = "0"+arrda[1];
					}
					if(arrda[2].length()==1){
						arrda[2] = "0"+arrda[2];
					}
					da = StringUtils.join(arrda, "-");
				}catch(Exception e){
					da = "";
				}
				String roleName = rows.get(5);//角色
				SysUser user = new SysUser();
				user.setId(userid);
				user.setSex("女".equals(sex)?"0":"1");
				String username = PinYinUtil.getPingYin(name);
				SysUser u = SysUser.dao.getByUsername(username);
				if(u!=null){
					username = username + birthDate.substring(0, 4);
				}
				u = SysUser.dao.getByUsername(username);
				if(u!=null){
					username = username + birthDate.substring(0, 6);
				}
				
//				String username1 = mobile;
//				SysUser u = SysUser.dao.getByUsername(username1);
				if(u==null){//保存用户
					user.setUsername(mobile);
					user.setMobile(mobile);
					user.setBirthDate(birthDate);
					user.setInCompanyDate(da);
					user.setIdcard(card);
					user.setName(name);
					String first = rows.get(0).trim();//1级机构
					String second = rows.get(1).trim();//2级机构
					String third = rows.get(2).trim();//3级机构
					String four = rows.get(3).trim();//4级机构
					if(StrKit.isBlank(third)){//第3级为空
						SysOrg org1 = SysOrg.dao.findFirst("select * from sys_org o where o.name='"+first+"'");
						SysOrg org2 = SysOrg.dao.findFirst("select * from sys_org o where o.name='"+second+"' and o.parent_id='"+org1.getId()+"' ");
						user.setOrgid(org2.getId());
					}else if(StrKit.isBlank(four)){//第4级为空
						SysOrg org1 = SysOrg.dao.findFirst("select * from sys_org o where o.name='"+first+"'");
						SysOrg org2 = SysOrg.dao.findFirst("select * from sys_org o where o.name='"+second+"' and o.parent_id='"+org1.getId()+"' ");
						SysOrg org3 = SysOrg.dao.findFirst("select * from sys_org o where o.name='"+third+"' and o.parent_id='"+org2.getId()+"' ");
						user.setOrgid(org3.getId());
					}else if(StrKit.notBlank(four)){
						SysOrg org1 = SysOrg.dao.findFirst("select * from sys_org o where o.name='"+first+"'");
						SysOrg org2 = SysOrg.dao.findFirst("select * from sys_org o where o.name='"+second+"' and o.parent_id='"+org1.getId()+"' ");
						SysOrg org3 = SysOrg.dao.findFirst("select * from sys_org o where o.name='"+third+"' and o.parent_id='"+org2.getId()+"' ");
						SysOrg org4 = SysOrg.dao.findFirst("select * from sys_org o where o.name='"+four+"' and o.parent_id='"+org3.getId()+"' ");
						user.setOrgid(org4.getId());
					}
					user.setStatus("1");
					PasswordService svc = new DefaultPasswordService();
					user.setPassword(svc.encryptPassword(password));//加密新密码
					user.save();
					
					
					SysRoleUser a = new SysRoleUser();
					String roleid = "";
					String rolekey = "";
					if(roleName.indexOf("总")>=0){
						roleid = "1";
						rolekey = "zongjingli";
					}else if(roleName.indexOf("经理")>=0){
						roleid = "2";
						rolekey = "jingli";
					}else if(roleName.indexOf("事")>=0){
						roleid = "3";
						rolekey = "renshi";
					}else if(roleName.indexOf("财务主管")>=0){
						roleid = "5";
						rolekey = "caiwuzhuguan";
					}else if(roleName.indexOf("财务")>=0){
						roleid = "4";
						rolekey = "caiwu";
					}else{
						roleid = "8";
						rolekey = "commonUser";
					}
					SysRole role = new SysRole();
					role.setKey(rolekey);
					WorkFlowIdentityService.me.createRelationShip(user, role);
					a.setId(UuidUtil.getUUID());
					a.setRoleId(roleid);
					a.setUserId(userid);
					a.save();//保存角色关系
				}
					
//			String mobile = rows.get(9).trim();//手机号
//			if(StrKit.notBlank(mobile)){
//				SysUser user = SysUser.dao.getByUsername(mobile);
//				if(user!=null){
//					String roleName = rows.get(5);//角色
//					user.setPosition(roleName);//职位
//					user.update();
//				}
//			}
		}
	}
}
