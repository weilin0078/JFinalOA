/**
 * Copyright (C) 2014 陕西威尔伯乐信息技术有限公司
 * @Package com.wellbole.web.sys.login  
 * @Title: CaptchaUsernamePasswordToken.java  
 * @Description: 在用户名和密码的基础上添加验证码的Token  
 * @author 李飞 (lifei@wellbole.com)    
 * @date 2014年9月11日  下午1:20:33  
 * @since V1.0.0 
 *
 * Modification History:
 * Date         Author      Version     Description
 * -------------------------------------------------------------
 * 2014年9月11日      李飞                       V1.0.0        新建文件   
 */
package com.lion.admin.common.plugin.shiro.simple;

import org.apache.shiro.authc.UsernamePasswordToken;

/**  
 * @ClassName: CaptchaUsernamePasswordToken  
 * @Description: 在用户名和密码的基础上添加验证码的Token  
 * @author 李飞 (lifei@wellbole.com)   
 * @date 2014年9月11日 下午1:20:33
 * @since V1.0.0  
 */
public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {
    private static final long serialVersionUID = 4676958151524148623L;
    
    private String captcha;
    
    public CaptchaUsernamePasswordToken(String username, String password, boolean rememberMe, String host, String captcha) {
        super(username, password, rememberMe, host);
        this.captcha = captcha;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
