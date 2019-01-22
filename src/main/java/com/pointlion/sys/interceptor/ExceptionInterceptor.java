package com.pointlion.sys.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.pointlion.sys.mvc.common.dto.RenderBean;
import com.pointlion.sys.mvc.common.model.SysLog;
import com.pointlion.sys.mvc.common.utils.DateUtil;
import com.pointlion.sys.mvc.common.utils.UuidUtil;
import com.pointlion.sys.plugin.shiro.ShiroKit;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ExceptionInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
    	//组装日志数据
    	SysLog log = new SysLog();
    	Controller c = inv.getController();
    	String target = inv.getActionKey();//请求地址
    	String username = c.getPara("username");//操作用户
    	try{
    		if(StrKit.isBlank(username)){//PC端可能不会传递username，需要用shiro获取，移动端没有shiro所以可能会报错，报错直接赋值为空
    			username = ShiroKit.getUsername();
    		}
    		if(username == null){
    			username = "";
    		}
    	}catch(Exception e){
    		username = "";
    	}
    	String paraStr = "";//传递的参数
    	try{
    		Map<String, String[]> para = c.getParaMap();
    		paraStr = JSONObject.fromObject(para).toString();
    	}catch(Exception e){
    		paraStr = "";
    	}
    	String client = c.getPara("ifMobileClient");//是否是手机端
    	if("1".equals(client)){//如果是手机端
    		client = "1";//手机端参数为1
    	}else{
    		client = "0";//PC端为0。暂时只有两个client。
    	}
        log.setId(UuidUtil.getUUID());
        log.setCreateTime(DateUtil.getTime());
        log.setClient(client);
        log.setTarget(target);
        log.setUsername(username);
        log.setPara(paraStr);
        log.setClient(client);
        try{
            inv.invoke();
            log.setIfSuccess("1");//成功
        }catch (Exception e){
        	e.printStackTrace();
        	log.setIfSuccess("0");//失败
        	RenderBean renderBean = new RenderBean();
        	String s = e.getMessage();
        	log.setErrorMessage(s);
        	System.out.println("!!!!!!!!!!!!!!!!!错误信息!!!!!!!!!!!!!!!!!!!："+s);
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
