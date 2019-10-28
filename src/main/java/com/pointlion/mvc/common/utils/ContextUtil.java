package com.pointlion.mvc.common.utils;

import javax.servlet.http.HttpServletRequest;

public class ContextUtil {
	private static String ctx="";/////              /JFinalOA
	public static void setCtx(String context){
		ctx = context;
	} 
	public static String getCtx(){
		return ctx;
	}
	
	/***
	 * 获取路径
	 * http://localhost:8080/JFinalOA
	 */
	public static String getContextUrl(HttpServletRequest request){
    	StringBuffer url = request.getRequestURL(); 
    	String contextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
    	if (contextUrl.endsWith("/")) {
    	    contextUrl = contextUrl.substring(0, contextUrl.length() - 1);
    	}
    	contextUrl = contextUrl + ctx;
		return contextUrl;
	}
}
