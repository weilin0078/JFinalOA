/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.sys.mvc.mobile.login;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;

import com.jfinal.aop.Clear;
import com.pointlion.sys.mvc.common.base.BaseController;
import com.pointlion.sys.mvc.common.model.SysUser;

/***
 * 通知公告控制器（手机端）
 * @author Administrator
 *
 */
@Clear()
public class MobileLoginController extends BaseController {
	
	/***
	 * 手机端登陆
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
        		renderSuccess(user.getId(),null);
        	}else{
        		renderError("用户名或密码错误");
        	}
        }
	}
}
