package com.lion.sys.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;

import com.jfinal.handler.Handler;
import com.jfinal.log.Log;

/**
 * 全局Handler，设置一些通用功能
 * @author 董华健
 * 描述：主要是一些全局变量的设置，再就是日志记录开始和结束操作
 */
public class GlobalHandler extends Handler {
	
	private static final Log log = Log.getLog(GlobalHandler.class);

	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		
		log.debug("设置 web 路径");
		String ctx = request.getContextPath();
		request.setAttribute("ctx", ctx);

		log.debug("设置Header");
		request.setAttribute("decorator", "none");
		response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
		response.setHeader("Pragma","no-cache"); //HTTP 1.0
		response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
		
		next.handle(target, request, response, isHandled);
		
		log.debug("请求处理完毕，计算耗时");
	}
	
}
