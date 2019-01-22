package com.pointlion.sys.mvc.admin.oa.workflow;

import com.pointlion.sys.plugin.activiti.ActivitiPlugin;
import org.flowable.engine.IdentityService;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntity;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntityImpl;
import org.flowable.idm.engine.impl.persistence.entity.UserEntity;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
//import org.activiti.engine.IdentityService;
//import org.activiti.engine.identity.Group;
//import org.activiti.engine.identity.User;
//import org.activiti.engine.impl.persistence.entity.GroupEntity;
//import org.activiti.engine.impl.persistence.entity.UserEntity;

import java.util.List;

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
			UserEntityImpl u = new UserEntityImpl();
			u.setId(username);
			identityService.saveUser(u);//建立activiti用户
		}
	}
	
	/***
	 * 创建权限组
	 */
	public void addGroup(String groupKey){
		Group group = identityService.createGroupQuery().groupId(groupKey).singleResult();//Activiti角色
		if(group==null){
			GroupEntityImpl g = new GroupEntityImpl();
			g.setId(groupKey);
			identityService.saveGroup(g);//建立组
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
