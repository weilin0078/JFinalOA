package com.pointlion.mvc.common.utils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;

public class HtmlUnitUtil {
	
	/***
	 * 获取浏览器对象
	 * @return
	 */
	public static WebClient getWebClient(){
			WebClient webClient = new WebClient(BrowserVersion.FIREFOX_45); //创建火狐浏览器
	       // 1 启动JS  
	       webClient.getOptions().setJavaScriptEnabled(true);  
	       // 2 禁用Css，可避免自动二次请求CSS进行渲染  
	       webClient.getOptions().setCssEnabled(false);  
	       //3 启动客户端重定向  
	       webClient.getOptions().setRedirectEnabled(true);  
	       // 4 运行错误时，是否抛出异常  
	       webClient.getOptions().setThrowExceptionOnScriptError(false);  
	       // 5 设置超时  
	       webClient.getOptions().setTimeout(600000);  
	       //6 设置忽略证书  
	       //webClient.getOptions().setUseInsecureSSL(true);  
	       //7 设置Ajax  
	       webClient.setAjaxController(new NicelyResynchronizingAjaxController());  
	       //8设置cookie  
	       webClient.getCookieManager().setCookiesEnabled(true); 
	       //设置js超时时间
	       webClient.setJavaScriptTimeout(30000);
	       return webClient;
	}
}
