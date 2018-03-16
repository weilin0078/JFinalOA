package com.pointlion.sys.plugin.shiro;

import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.pointlion.sys.plugin.shiro.ext.SimpleUser;


/**
 * ShiroKit. (Singleton, ThreadSafe)
 *
 * @author dafei
 */
public class ShiroKit {

	/**
	 * 登录成功时所用的页面。
	 */
	private static String successUrl = "/";

	/**
	 * 登录成功时所用的页面。
	 */
	private static String loginUrl = "/login.html";


	/**
	 * 登录成功时所用的页面。
	 */
	private static String unauthorizedUrl ="/401.jsp";


	/**
	 * Session中保存的请求的Key值
	 */
	private static String SAVED_REQUEST_KEY = "jfinalShiroSavedRequest";


	/**
	 * 用来记录那个action或者actionpath中是否有shiro认证注解。
	 */
	private static ConcurrentMap<String, AuthzHandler> authzMaps = null;

	/**
	 * 禁止初始化
	 */
	public ShiroKit() {}

	static void init(ConcurrentMap<String, AuthzHandler> maps) {
		authzMaps = maps;
	}

	static AuthzHandler getAuthzHandler(String actionKey){
		/*
		if(authzMaps.containsKey(controllerClassName)){
			return true;
		}*/
		return authzMaps.get(actionKey);
	}

	public static final String getSuccessUrl() {
		return successUrl;
	}

	public static final void setSuccessUrl(String successUrl) {
		ShiroKit.successUrl = successUrl;
	}

	public static final String getLoginUrl() {
		return loginUrl;
	}

	public static final void setLoginUrl(String loginUrl) {
		ShiroKit.loginUrl = loginUrl;
	}

	public static final String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}

	public static final void setUnauthorizedUrl(String unauthorizedUrl) {
		ShiroKit.unauthorizedUrl = unauthorizedUrl;
	}
	/**
	 * Session中保存的请求的Key值
	 * @return
	 */
	public static final String getSavedRequestKey(){
		return SAVED_REQUEST_KEY;
	}
	/***
	 * 获取登陆用户信息
	 * @return
	 */
	public static SimpleUser getLoginUser(){
		Subject subject = SecurityUtils.getSubject();
		if(subject!=null){
			return (SimpleUser)subject.getPrincipal();
		}else{
			SecurityUtils.getSubject().logout();
			return null;
		}
	}
	/***
	 * 获取登陆用户主键
	 * @return
	 */
	public static String getUserId(){
		SimpleUser user = ShiroKit.getLoginUser();
		return user.getId();
	}
	/***
	 * 获取登陆用户id
	 * @return
	 */
	public static String getUsername(){
		SimpleUser user = ShiroKit.getLoginUser();
		return user.getUsername();
	}
	
	/***
	 * 获取用户名称
	 * @return
	 */
	public static String getUserName(){
		SimpleUser user = ShiroKit.getLoginUser();
		return user.getName();
	}
	
	/***
	 * 判断是否有资源权限
	 * @param p
	 * @return
	 */
	public static boolean hasPermission(String p) {
		Subject subject = SecurityUtils.getSubject();
		Boolean result = false;
		try {
			result = ((subject != null) && (subject.isPermitted(p)));
		} catch (Exception e) {
			e.printStackTrace();
			return false; 
		}
		return result;
	}
}
