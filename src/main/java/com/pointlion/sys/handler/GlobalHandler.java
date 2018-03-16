/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.sys.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;
import com.jfinal.log.Log;

/**
 * 全局Handler，设置一些通用功能
 * 描述：主要是一些全局变量的设置，再就是日志记录开始和结束操作
 */
public class GlobalHandler extends Handler {
	
	private static final Log log = Log.getLog(GlobalHandler.class);

	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		log.debug("设置 web 路径");
		String ctx = request.getContextPath();
		request.setAttribute("ctx", ctx);//设置全局上下文
//		log.debug("设置Header");
//		request.setAttribute("decorator", "none");
//		response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
//		response.setHeader("Pragma","no-cache"); //HTTP 1.0
//		response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
		next.handle(target, request, response, isHandled);
		log.debug("请求处理完毕，计算耗时");
	}
	
}
