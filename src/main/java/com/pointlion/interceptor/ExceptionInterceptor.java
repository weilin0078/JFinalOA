package com.pointlion.interceptor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.pointlion.mvc.common.dto.RenderBean;
import com.pointlion.mvc.common.model.SysLog;
import com.pointlion.mvc.common.utils.DateUtil;
import com.pointlion.mvc.common.utils.UuidUtil;
import com.pointlion.plugin.shiro.ShiroKit;

import net.sf.json.JSONObject;

public class ExceptionInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
    	Controller c = inv.getController();
        try{
            inv.invoke();
        }catch (Exception e){
        	e.printStackTrace();
        	RenderBean renderBean = new RenderBean();
        	String s = e.getMessage();
        	System.out.println("ExceptionInterceptor捕获异常："+s);
        	renderBean.setSuccess(false);
        	Map<String,Object> data = new HashMap<String,Object>();
        	if(s.indexOf("Data too long")>0){
        		renderBean.setMessage("输入数据太长，请减少输入内容！！！！");
        		String col = s.split("'")[1];
        		data.put("type", "errorCol");
        		data.put("errorCol", col);
        	}else{
        		renderBean.setMessage(e.getMessage());
        	}
        	renderBean.setData(data);
    		String ifGoPage= inv.getController().getPara("_pjax");
    		if(StrKit.notBlank(ifGoPage)){//如果是pjax跳转页面的时候报错了
    			inv.getController().render("/error/500.html");
    		}else{
    			inv.getController().renderJson(renderBean);
    		}
        }
    }
}
