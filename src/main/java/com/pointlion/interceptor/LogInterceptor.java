package com.pointlion.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.pointlion.annotation.ServerLog;
import com.pointlion.mvc.common.model.SysLog;
import com.pointlion.mvc.common.utils.DateUtil;
import com.pointlion.mvc.common.utils.IPUtil;
import com.pointlion.mvc.common.utils.UuidUtil;
import com.pointlion.plugin.shiro.ShiroKit;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Description: LogInterceptor
 * @Author: liyang
 * @Date: 2019/10/15 0015 16:10
 * @Version 1.0
 */
public class LogInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        Method method = inv.getMethod();
        ServerLog serverLogAnno = method.getAnnotation(ServerLog.class);
        if(serverLogAnno !=null){
            //组装日志数据
            SysLog log = new SysLog();
            //目标地址
            String target = inv.getActionKey();
            //客户端编码
            String client = controller.getPara("clientCode");
            //用户名；PC端为0。暂时只有两个client。
            String username = "";
            if("1".equals(client)){
                client = "1";
                username = controller.getPara("username");
            }else{
                client = "0";
                username = controller.getPara("username");
                if(StringUtils.isBlank(username)){
                    try{
                        username = ShiroKit.getUsername();
                    }catch (Exception e){
                        username = "";
                    }
                }
            }
            //传递的参数
            String paraStr = "";
            try{
                Map<String, String[]> para = controller.getParaMap();
                paraStr = JSONObject.fromObject(para).toString();
            }catch(Exception e){
                paraStr = "";
            }
            //执行
            try{
                inv.invoke();
                log.setIfSuccess("1");
            }catch (Exception e){
                log.setIfSuccess("0");
                log.setErrorMessage(e.getMessage());
            }
            //保存
            log.setId(UuidUtil.getUUID());
            log.setClient(client);
            log.setPara(paraStr);
            log.setTarget(target);
            log.setCreateTime(DateUtil.getCurrentTime());
            log.setIp(IPUtil.getClientIp(controller.getRequest()));
            log.setOperateMethod(inv.getMethodName());
            log.setUsername(username);
            log.setModule(serverLogAnno.module());
            log.setBusinessType(String.valueOf(serverLogAnno.businessType().toNumber()));
            log.save();
        }else{
            inv.invoke();
        }
    }
}
