/**
 * 
 */
package com.pointlion.mvc.admin.sys.role;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.pointlion.mvc.admin.oa.workflow.WorkFlowIdentityService;
import com.pointlion.mvc.common.base.BaseController;
import com.pointlion.mvc.common.model.*;
import com.pointlion.mvc.common.utils.UuidUtil;
import com.pointlion.plugin.shiro.ShiroKit;

import java.util.List;

/**
 * @author Lion
 * @date 2017年2月16日 下午4:04:25
 * @qq 439635374
 */

public class RoleController extends BaseController {
	public static final WorkFlowIdentityService idService = WorkFlowIdentityService.me;
	public static final RoleService roleService = RoleService.me;
	/***
	 * 获得列表页
	 */
	public void getListPage(){
    	renderIframe("list.html");
    }
    /***
     * 获取编辑页面
     */
    public void getEditPage(){
    	//添加和修改
    	String id = getPara("id");
    	if(StrKit.notBlank(id)){
    		SysRole role = SysRole.dao.getById(id);
    		setAttr("o", role);
    	}
    	renderIframe("edit.html");
    }
    /***
     * 保存
     */
    public void save(){
    	SysRole role = getModel(SysRole.class);
    	if(StrKit.notBlank(role.getId())){
//    		SysRole oldRole = SysRole.dao.getById(role.getId());
//    		List<SysRole> list = SysRole.dao.getAllUserByRoleid(role.getId());
//    		idService.deleteGroup(oldRole.getKey());//删掉老的角色
//    		for(SysRole r:list){
//    			idService.createRelationShip(r.getStr("username"), role.getKey());//没有用户会创建,没有角色会创建。使用新的角色创建。
//    		}
    		//如果角色key修改了，要查询出该角色下的所有人，然后删掉
    		role.update();
    	}else{
    		role.setId(UuidUtil.getUUID());
    		role.save();
    		idService.addGroup(role);;
    	}
    	renderSuccess();
    }
    /***
     * 查询分页数据
     */
    public void listData(){
    	String curr = getPara("pageNumber");
    	String pageSize = getPara("pageSize");
    	Page<SysRole> page = SysRole.dao.getRolePage(Integer.valueOf(curr),Integer.valueOf(pageSize));
    	renderPage(page.getList(),"" ,page.getTotalRow());
    }
    
    /***
     * 删除
     * @throws Exception
     */
    public void delete() throws Exception{
		SysRole.dao.deleteByIds(getPara("ids"));
    	renderSuccess();
    }
    
    /***
     * 获取选择权限菜单的页面
     */
    public void getGiveAuthPage(){
    	setAttr("roleid", getPara("roleid"));
    	renderIframe("giveAuth.html");
    }

	/***
	 * 打开角色下用户页面
	 */
	public void getRoleUserPage(){
		setAttr("roleId", getPara("roleId",""));
		renderIframe("roleUser.html");
	}

	/***
	 * 角色下用户列表
	 */
	public void getRoleUserListData(){
		String roleId = getPara("roleId","");
		String pageNumber = getPara("pageNumber","");
		String pageSize = getPara("pageSize","");
		Page<Record> page = SysUser.dao.getPageByRoleid(Integer.valueOf(pageNumber),Integer.valueOf(pageSize),roleId);
		renderPage(page.getList(),"" ,page.getTotalRow());
	}


    /***
     * 给角色赋权
     */
    public void changeRoleAuth(){
    	String data = getPara("data");
    	String roleid = getPara("roleid"); 
    	SysRole.dao.changeRoleAuth(roleid,data);
    	ShiroKit.clearAllCachedAuthorizationInfo();//清除所有人shiro缓存，将重新执行初始化权限操作（ShiroDbRealm.doGetAuthorizationInfo方法）
    	renderSuccess();
    }
    
    /***
     * 获取角色下所有的菜单权限
     */
    public void getRoleMenuAuthByRoleid(){
    	String roleId = getPara("roleId");
    	List<SysRoleMenu> list = SysRole.dao.getRoleMenuAuthByRoleId(roleId);
    	renderJson(list);
    }
    /***
     * 获取所有的角色
     * 给用户赋值角色时候用
     */
    public void getAllRoleTreeNode(){
    	String roleKey = getPara("roleKey","");
    	renderJson(SysRole.dao.getAllRoleTreeNode(roleKey));
    }
    /***
     * 获取用户下所有角色
     */
    public void getAllRoleByUserid(){
    	renderJson(SysRole.dao.getAllRoleByUserid(getPara("userid")));
    }
    
    
    /***
     * 打开选择角色页面
     */
    public void getSelectOneRolePage(){
    	renderIframe("selectOneRole.html");
    }
    
    
    /***
     * 打开角色编辑用户页面
     */
    public void getEditRoleUserPage(){
    	String roleid = getPara("roleid");
    	setAttr("userList", SysRole.dao.getAllUserByRoleid(roleid));
    	setAttr("orgid", ShiroKit.getUserOrgId());
    	setAttr("roleid",roleid);
    	//获取角色下所有用户
    	renderIframe("editRoleUser.html");
    }
    /***
     * 检查角色名重复
     */
    public void validRoleName(){
    	SysRole role = getModel(SysRole.class);
    	List<SysRole> list = SysRole.dao.getRoleByRoleName(role.getName());
    	if(list!=null&&list.size()>0){
    		renderValidFail();
    	}else{
    		renderValidSuccess();
    	}
    }
    
    /***
     * 检查角色key重复
     */
    public void validRoleKey(){
    	SysRole role = getModel(SysRole.class);
    	SysRole r = SysRole.dao.getRoleByRoleKey(role.getKey());
    	if(r!=null){
    		renderValidFail();
    	}else{
    		renderValidSuccess();
    	}
    }
    
    
    /***
     * 将角色下添加用户
     */
    public void addUserToRole(){
    	String userId = getPara("userId");
    	String roleId = getPara("roleId");
    	if(StrKit.notBlank(userId)&&StrKit.notBlank(roleId)){
			SysRoleUser ur = SysRoleUser.dao.getByUseridAndRoleid(userId,roleId);
			if(ur!=null){
				renderError("该角色下已有该用户");
			}else{
				SysUser user = SysUser.dao.getById(userId);//用户
				SysRole role = SysRole.dao.findById(roleId);//角色
				ur = new SysRoleUser();
				ur.setId(UuidUtil.getUUID());
				ur.setUserId(userId);
				ur.setRoleId(roleId);
				ur.save();
				idService.createRelationShip(user, role);//没有用户会创建,没有角色会创建。流程引擎
				ShiroKit.clearAllCachedAuthorizationInfo();//清除所有人shiro缓存，将重新执行初始化权限操作（ShiroDbRealm.doGetAuthorizationInfo方法）
				renderSuccess();
			}
		}else{
			renderError("数据获取失败");
		}

    }
    
    /***
     * 角色下删除用户
     */
    public void removeUserFromRole(){
    	String userId = getPara("userId");
    	String roleId = getPara("roleId");
		SysUser user = SysUser.dao.getById(userId);//用户
		SysRole role = SysRole.dao.findById(roleId);//角色
		idService.removeRelationShip(user.getUsername(), role.getKey());//没有用户会创建,没有角色会创建
		SysRoleUser ur = SysRoleUser.dao.getByUseridAndRoleid(userId,roleId);
		if(ur!=null){
			ur.delete();
		}
		ShiroKit.clearAllCachedAuthorizationInfo();//清除所有人shiro缓存，将重新执行初始化权限操作（ShiroDbRealm.doGetAuthorizationInfo方法）
		renderSuccess();
    }


	/***
	 * 打开角色编辑数据权限页面
	 */
	public void editRoleDataScope(){
		String roleId = getPara("roleid","");
		setAttr("roleId",roleId);
		SysRole role = SysRole.dao.getById(roleId);
		setAttr("o",role);
		if("4".equals(role.getDataScope())){//自定义数据权限

		}
		renderIframe("giveDataScope.html");
	}

	/***
	 * 保存数据权限
	 */
	public void saveDataScope(){
		String roleId = getPara("roleId","");
		String scope = getPara("scope","1");
		String orgListStr = getPara("orgListStr","");
		roleService.saveDataScope(roleId,scope,orgListStr);
		renderSuccess();
	}

	/***
	 * 获取角色下所有的自定义数据权限
	 */
	public void getRoleOrgAuthByRoleid(){
		String roleId = getPara("roleId");
		List<SysRoleOrg> list = SysRole.dao.getRoleOrgAuthByRoleId(roleId);
		renderJson(list);
	}

}
