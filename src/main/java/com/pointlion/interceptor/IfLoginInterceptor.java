package com.pointlion.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.pointlion.plugin.shiro.ShiroKit;
import com.pointlion.plugin.shiro.ext.SimpleUser;

public class IfLoginInterceptor implements Interceptor {
	@Override
	public void intercept(Invocation inv) {
		if(!inv.getActionKey().contains(ShiroKit.getLoginUrl())&&inv.getActionKey().indexOf("/admin/")==0){//如果访问的不是登录地址,并且，访问的是/admin/路由下的地址
			SimpleUser user = ShiroKit.getLoginUser();
			Controller c = inv.getController();
			if(user!=null){
				c.setAttr("userid", user.getId());//设置全局userid,【外键使用】
				c.setAttr("username", user.getUsername());//设置全局username,【登录名】
				c.setAttr("usercaption", user.getName());//设置全局昵称,【昵称】
			}else{
				c.redirect(ShiroKit.getLoginUrl());
				return;
			}
		}
		inv.invoke();
	}

}
