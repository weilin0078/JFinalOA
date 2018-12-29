package com.pointlion.sys.mvc.admin.oa.workflow;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;

import com.pointlion.sys.plugin.activiti.ActivitiPlugin;

public class WorkFlowIdentityService {
	public static final WorkFlowIdentityService me = new WorkFlowIdentityService();
	public static final IdentityService identityService = ActivitiPlugin.buildProcessEngine().getIdentityService();
	
	/***
	 * 创建新用户
	 * @param username
	 */
	public void addUser(String username){
		User user = identityService.createUserQuery().userId(username).singleResult();
		if(user==null){
			identityService.saveUser(new UserEntity(username));//建立activiti用户
		}
	}
	
	/***
	 * 创建权限组
	 */
	public void addGroup(String groupKey){
		Group group = identityService.createGroupQuery().groupId(groupKey).singleResult();//Activiti角色
		if(group==null){
			identityService.saveGroup(new GroupEntity(groupKey));//建立组
		}
	}
	
	/***
	 * 添加关系
	 */
	public void createRelationShip(String username,String groupkey){
		addUser(username);//创建用户，没有用户会新增
		addGroup(groupkey);//创建分组，没有分组的话
		identityService.createMembership(username, groupkey);//建立组和用户关系
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
