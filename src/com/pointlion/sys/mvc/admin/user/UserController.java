/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.sys.mvc.admin.user;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.pointlion.sys.interceptor.MainPageTitleInterceptor;
import com.pointlion.sys.mvc.common.base.BaseController;
import com.pointlion.sys.mvc.common.model.SysOrg;
import com.pointlion.sys.mvc.common.model.SysUser;
import com.pointlion.sys.mvc.common.utils.UuidUtil;

/***
 * 用户管理控制器
 * @author Administrator
 *
 */
@Before(MainPageTitleInterceptor.class)
public class UserController extends BaseController {
	
	/***
	 * 获取管理首页
	 */
	public void getListPage(){
    	render("/WEB-INF/admin/user/list.html");
    }
	/***
     * 获取分页数据
     **/
    public void listData(){
    	String curr = getPara("pageNumber");
    	String pageSize = getPara("pageSize");
    	String orgid = getPara("orgid");
    	Page<Record> page = SysUser.dao.getPage(Integer.valueOf(curr),Integer.valueOf(pageSize),orgid);
    	renderPage(page.getList(),"" ,page.getTotalRow());
    }
    /***
     * 保存
     */
    public void save(){
    	SysUser o = getModel(SysUser.class);
    	if(StrKit.notBlank(o.getId())){
    		String password = o.getPassword();
    		if(StrKit.isBlank(password)){//如果没传递密码
    			SysUser u = SysUser.dao.findById(o.getId());
    			password = u.getPassword();//获取原始密码
    			o.setPassword(password);//设置为原始密码
    		}else{//传递了密码 , 设置新密码
    			PasswordService svc = new DefaultPasswordService();
    			o.setPassword(svc.encryptPassword(password));//加密新密码
    		}
    		o.update();
    	}else{
    		o.setId(UuidUtil.getUUID());
    		PasswordService svc = new DefaultPasswordService();
    		o.setPassword(svc.encryptPassword(o.getPassword()));//加密新密码
    		o.save();
    	}
    	renderSuccess();
    }
    /***
     * 获取编辑页面
     */
    public void getEditPage(){
    	//添加和修改
    	String id = getPara("id");
    	if(StrKit.notBlank(id)){//修改
    		SysUser o = SysUser.dao.getById(id);
    		String orgid = o.getOrgid();
    		SysOrg org = SysOrg.dao.getById(orgid);
    		setAttr("org", org);
    		setAttr("o", o);
    	}else{
    		String orgid = getPara("orgid");
    		SysOrg org = SysOrg.dao.getById(orgid);
    		setAttr("org", org);
    	}
    	render("/WEB-INF/admin/user/edit.html");
    }
    /***
     * 验证用户 , 是否被注册
     */
    public void validUsername(){
    	SysUser user = getModel(SysUser.class);
    	if(user!=null){
    		String username = user.getUsername();
    		if(StrKit.notBlank(username)){
    			SysUser o =  SysUser.dao.getByUsername(username);
    			if(o==null){//用户不存在 
    				renderValidSuccess();
    			}else{
    				renderValidFail();
    			}
    		}else{
    			renderValidSuccess();
    		}
    	}else{
    		renderValidSuccess();
    	}
    }
    
    /***
     * 给用户赋值角色
     */
    public void getGiveUserRolePage(){
    	String userid = getPara("userid");
    	setAttr("userid", userid);
    	render("/WEB-INF/admin/user/giveUserRole.html");
    }
    /***
     * 给用户赋值角色
     */
    public void giveUserRole(){
    	String userid = getPara("userid");
    	String data = getPara("data");
    	SysUser.dao.giveUserRole(userid,data);
    	renderSuccess();
    }
    /***
     * 删除
     * @throws Exception
     */
    public void delete() throws Exception{
		String ids = getPara("ids");
    	//执行删除
		SysUser.dao.deleteByIds(ids);
    	renderSuccess("删除成功!");
    }
    
    /**************************************************************************/
	private String pageTitle = "用户管理";//模块页面标题
	private String breadHomeMethod = "getListPage";//面包屑首页方法
	
	public Map<String,String> getPageTitleBread() {
		Map<String,String> pageTitleBread = new HashMap<String,String>();
		pageTitleBread.put("pageTitle", pageTitle);
		pageTitleBread.put("breadHomeMethod", breadHomeMethod);
		return pageTitleBread;
	}
}
