/**
 * 
 */
package com.pointlion.sys.mvc.admin.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.pointlion.sys.interceptor.MainPageTitleInterceptor;
import com.pointlion.sys.mvc.common.base.BaseController;
import com.pointlion.sys.mvc.common.model.SysRole;
import com.pointlion.sys.mvc.common.model.SysRoleAuth;
import com.pointlion.sys.mvc.common.utils.DateUtil;
import com.pointlion.sys.mvc.common.utils.UuidUtil;

/**
 * @author Lion
 * @date 2017年2月16日 下午4:04:25
 * @qq 439635374
 */
@Before(MainPageTitleInterceptor.class)
public class RoleController extends BaseController{
	/***
	 * 获得列表页
	 */
	public void getListPage(){
    	render("/WEB-INF/admin/role/list.html");
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
    	render("/WEB-INF/admin/role/edit.html");
    }
    /***
     * 保存
     */
    public void save(){
    	SysRole role = getModel(SysRole.class);
    	if(StrKit.notBlank(role.getId())){
    		role.update();
    	}else{
    		role.setId(UuidUtil.getUUID());
    		role.setCreateTime(DateUtil.getTime());
    		role.save();
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
    	render("/WEB-INF/admin/role/giveAuth.html");
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
    	renderJson(SysRole.dao.getAllRoleTreeNode());
    }
    /***
     * 获取用户下所有角色
     */
    public void getAllRoleByUserid(){
    	renderJson(SysRole.dao.getAllRoleByUserid(getPara("userid")));
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
