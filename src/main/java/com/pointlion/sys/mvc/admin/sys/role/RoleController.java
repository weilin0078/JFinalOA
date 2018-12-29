/**
 * 
 */
package com.pointlion.sys.mvc.admin.sys.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.pointlion.sys.interceptor.MainPageTitleInterceptor;
import com.pointlion.sys.mvc.admin.oa.workflow.WorkFlowIdentityService;
import com.pointlion.sys.mvc.common.base.BaseController;
import com.pointlion.sys.mvc.common.model.SysRole;
import com.pointlion.sys.mvc.common.model.SysRoleAuth;
import com.pointlion.sys.mvc.common.model.SysUser;
import com.pointlion.sys.mvc.common.model.SysUserRole;
import com.pointlion.sys.mvc.common.utils.UuidUtil;
import com.pointlion.sys.plugin.shiro.ShiroKit;

/**
 * @author Lion
 * @date 2017年2月16日 下午4:04:25
 * @qq 439635374
 */
@Before(MainPageTitleInterceptor.class)
public class RoleController extends BaseController{
	public static final WorkFlowIdentityService idService = WorkFlowIdentityService.me;
	/***
	 * 获得列表页
	 */
	public void getListPage(){
    	render("list.html");
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
    	render("edit.html");
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
    		idService.addGroup(role.getKey());;
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
    	render("giveAuth.html");
    }

    
    /***
     * 给角色赋权
     */
    public void changeRoleAuth(){
    	String data = getPara("data");
    	String roleid = getPara("roleid"); 
    	SysRole.dao.changeRoleAuth(roleid,data);
    	renderSuccess();
    }
    
    /***
     * 获取角色下所有的权限
     */
    public void getRoleAuthByRoleid(){
    	String roleid = getPara("roleid");
    	List<SysRoleAuth> list = SysRole.dao.getRoleAuthByRoleId(roleid);
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
    	render("selectOneRole.html");
    }
    
    
    /***
     * 打开角色编辑用户页面
     */
    public void getEditRoleUserPage(){
    	String roleid = getPara("roleid");
    	setAttr("userList", SysRole.dao.getAllUserByRoleid(roleid));
    	setAttr("orgid",ShiroKit.getUserOrgId());
    	setAttr("roleid",roleid);
    	//获取角色下所有用户
    	render("editRoleUser.html");
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
    	String userid = getPara("userid");
    	String roleid = getPara("roleid");
    	SysUserRole ur = SysUserRole.dao.getByUseridAndRoleid(userid,roleid);
		if(ur!=null){
			renderError("该角色下已有该用户");
		}else{
			SysUser user = SysUser.dao.getById(userid);//用户
			SysRole role = SysRole.dao.findById(roleid);//角色
			ur = new SysUserRole();
			ur.setId(UuidUtil.getUUID());
			ur.setUserId(userid);
			ur.setRoleId(roleid);
			ur.save();
			idService.createRelationShip(user.getUsername(), role.getKey());//没有用户会创建,没有角色会创建
			renderSuccess();
		}
    }
    
    /***
     * 角色下删除用户
     */
    public void removeUserFromRole(){
    	String userid = getPara("userid");
    	String roleid = getPara("roleid");
		SysUser user = SysUser.dao.getById(userid);//用户
		SysRole role = SysRole.dao.findById(roleid);//角色
		idService.removeRelationShip(user.getUsername(), role.getKey());//没有用户会创建,没有角色会创建
		SysUserRole ur = SysUserRole.dao.getByUseridAndRoleid(userid,roleid);
		if(ur!=null){
			ur.delete();
		}
		renderSuccess();
    }
    
    /**************************************************************************/
	private String pageTitle = "角色管理";//模块页面标题
	private String breadHomeMethod = "getListPage";//面包屑首页方法
	
	public Map<String,String> getPageTitleBread() {
		Map<String,String> pageTitleBread = new HashMap<String,String>();
		pageTitleBread.put("pageTitle", pageTitle);
		pageTitleBread.put("breadHomeMethod", breadHomeMethod);
		return pageTitleBread;
	}
}
