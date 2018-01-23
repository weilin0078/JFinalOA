package com.pointlion.sys.mvc.admin.login;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {
	public static String usernameKey = "username";
	/***
	 * 设置用户名--登录名
	 * @param req
	 * @param username
	 * !!!!!!!!!!!!!!!!!!!!!!!!建议使用shirokit获取登陆用名，如果获取不到会自动跳到登录页
	 */
	public static void setUsernameToSession(HttpServletRequest req,String username){
		req.getSession().setAttribute(usernameKey, username);
	} 
	public static String getUsernameFromSession(HttpServletRequest req){
		return req.getSession().getAttribute(usernameKey).toString();
	}
	
}
