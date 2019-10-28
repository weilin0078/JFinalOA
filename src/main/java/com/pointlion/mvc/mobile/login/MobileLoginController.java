/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.mvc.mobile.login;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;

import com.pointlion.mvc.common.base.BaseController;
import com.pointlion.mvc.common.model.SysOrg;
import com.pointlion.mvc.common.model.SysUser;

/***
 * 手机的登录控制器（手机端）
 * @author Administrator
 *
 */
public class MobileLoginController extends BaseController {
	
	/***
	 * 手机端登录
	 */
	public void doLogin(){
		String username = getPara("username");
		String password = getPara("password");
		//验证用户是否存在
		SysUser user = SysUser.dao.getByUsername(username);
		if(user==null){
        	renderError("用户不存在");
        }else{
        	//验证密码
        	PasswordService svc = new DefaultPasswordService();
        	if(svc.passwordsMatch(password, user.getPassword())){
        		Map<String,String> map = new HashMap<String,String>();
        		map.put("ID", user.getId());
        		map.put("USERNAME", user.getUsername());
        		map.put("NAME", user.getName());
        		String orgId = user.getOrgid();
        		map.put("ORGID", orgId);
        		SysOrg org = new SysOrg();
        		map.put("ORGNAME", org.getById(orgId)==null?"":org.getById(orgId).getName());
        		renderSuccess(map,null);
        	}else{
        		renderError("用户名或密码错误");
        	}
        }
	}
}
