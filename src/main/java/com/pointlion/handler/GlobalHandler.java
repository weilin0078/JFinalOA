/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.core.Action;
import com.jfinal.core.JFinal;
import com.jfinal.handler.Handler;
import com.jfinal.render.RenderManager;
import com.pointlion.mvc.common.utils.ContextUtil;
/**
 * 全局Handler，设置一些通用功能
 * 描述：主要是一些全局变量的设置，再就是日志记录开始和结束操作
 */
public class GlobalHandler extends Handler {
	
//	private static final ServerLog log = ServerLog.getLog(GlobalHandler.class);
	private static final RenderManager renderManager = RenderManager.me();

	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled){
		response.setHeader("Access-Control-Allow-Origin","*");//允许跨域请求
		String ctx = request.getContextPath();
		ContextUtil.setCtx(ctx);
		request.setAttribute("ctx", ctx);//设置全局上下文
        String[] urlPara = {null};
        Action action = JFinal.me().getAction(target, urlPara);
        if(action==null){
        	renderManager.getRenderFactory().getRender("/error/404.html").setContext(request, response).render();
        	return;
        }
		next.handle(target, request, response, isHandled);
	}
	
}
