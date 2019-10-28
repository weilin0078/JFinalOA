package com.pointlion.mvc.admin.oa.workflow;

import java.util.List;

import com.pointlion.mvc.common.model.SysRole;
import com.pointlion.mvc.common.model.SysUser;
import org.flowable.engine.IdentityService;
//import org.flowable.engine.identity.Group;
//import org.flowable.engine.identity.User;
//import org.flowable.engine.impl.persistence.entity.GroupEntity;
//import org.flowable.engine.impl.persistence.entity.UserEntity;

import com.pointlion.plugin.flowable.FlowablePlugin;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;

public class WorkFlowIdentityService {
	public static final WorkFlowIdentityService me = new WorkFlowIdentityService();
	public static final IdentityService identityService = FlowablePlugin.buildProcessEngine().getIdentityService();
	
	/***
	 * 创建新用户
	 * @param sysUser
	 */
	public void addUser(SysUser sysUser){
		String username = sysUser.getUsername();
		User user = identityService.createUserQuery().userId(username).singleResult();
		if(user==null){
//			User u = new UserEntityImpl();
//			u.setId(username);
//			identityService.saveUser(u);//建立activiti用户

			user = identityService.newUser(username);
			user.setFirstName(sysUser.getName());
			user.setLastName("");
			user.setEmail(sysUser.getEmail());
			//保存用户到数据库
			identityService.saveUser(user);
		}
	}
	
	/***
	 * 创建权限组
	 */
	public void addGroup(SysRole role){
		String groupKey = role.getKey();
		Group group = identityService.createGroupQuery().groupId(groupKey).singleResult();//Activiti角色
		if(group==null){
			group = identityService.newGroup(groupKey);
			group.setName(role.getName());
			group.setType("");
			identityService.saveGroup(group);//建立组
		}
	}
	
	/***
	 * 添加关系
	 */
	public void createRelationShip(SysUser sysUser, SysRole role){
		addUser(sysUser);//创建用户，没有用户会新增
		addGroup(role);//创建分组，没有分组的话
		identityService.createMembership(sysUser.getUsername(), role.getKey());//建立组和用户关系
	}
	
	/***
	 * 删除关系
	 */
	public void removeRelationShip(String username,String groupkey){
		identityService.deleteMembership(username, groupkey);//删除关系
	}
	
	/***
	 * 删除用户
	 */
	
	public void deleteUser(String username){
		identityService.deleteUser(username);
	}
	/***
	 * 删除角色
	 */
	public void deleteGroup(String groupKey){
		identityService.deleteGroup(groupKey);
	}
	/***
	 * 删除用户的所有角色
	 */
	public void deleteUserAllRole(String username){
		List<Group> groupList = identityService.createGroupQuery().groupMember(username).list();
		if(groupList!=null&&groupList.size()>0){
			for(Group g : groupList){
				identityService.deleteMembership(username, g.getId());
			}
		}
	}
}
