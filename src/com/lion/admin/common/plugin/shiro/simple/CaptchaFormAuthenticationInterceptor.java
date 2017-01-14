/**
 * Copyright (C) 2014 陕西威尔伯乐信息技术有限公司
 * @Package com.wellbole.web.sys.login  
 * @Title: CaptchaFormAuthenticationInterceptor.java  
 * @Description: 带验证码的表单验证拦截器
 * @author 李飞 (lifei@wellbole.com)    
 * @date 2014年9月11日  下午1:18:00  
 * @since V1.0.0 
 *
 * Modification History:
 * Date         Author      Version     Description
 * -------------------------------------------------------------
 * 2014年9月11日      李飞                       V1.0.0        新建文件   
 */
package com.lion.admin.common.plugin.shiro.simple;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**  
 * @ClassName: CaptchaFormAuthenticationInterceptor  
 * @Description: 带验证码的表单验证拦截器  
 * @author 李飞 (lifei@wellbole.com)   
 * @date 2014年9月11日 下午1:18:00
 * @since V1.0.0  
 */
public class CaptchaFormAuthenticationInterceptor extends FormAuthenticationFilter implements Interceptor {

    private String captchaParam = "captcha";

    public String getCaptchaParam() {
        return captchaParam;
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    protected AuthenticationToken createToken(HttpServletRequest request) {
        String username = getUsername(request);
        String password = getPassword(request);
        String captcha = getCaptcha(request);
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        return new CaptchaUsernamePasswordToken(username, password, rememberMe, host, captcha);
    }
    
    @Override
    public void intercept(Invocation ai) {
        HttpServletRequest request = ai.getController().getRequest();
        AuthenticationToken authenticationToken = createToken(request);
        request.setAttribute("shiroToken", authenticationToken);
        ai.invoke();
    }

}
