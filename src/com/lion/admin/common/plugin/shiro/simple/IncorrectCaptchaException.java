/**
 * Copyright (C) 2014 陕西威尔伯乐信息技术有限公司
 * @Package com.wellbole.web.sys.login  
 * @Title: IncorrectCaptchaException.java  
 * @Description: 验证码错误异常  
 * @author 李飞 (lifei@wellbole.com)    
 * @date 2014年9月11日  下午1:32:17  
 * @since V1.0.0 
 *
 * Modification History:
 * Date         Author      Version     Description
 * -------------------------------------------------------------
 * 2014年9月11日      李飞                       V1.0.0        新建文件   
 */
package com.lion.admin.common.plugin.shiro.simple;

import org.apache.shiro.authc.AuthenticationException;

/**  
 * @ClassName: IncorrectCaptchaException  
 * @Description: 验证码错误异常  
 * @author 李飞 (lifei@wellbole.com)   
 * @date 2014年9月11日 下午1:32:17
 * @since V1.0.0  
 */
public class IncorrectCaptchaException extends AuthenticationException {

    private static final long serialVersionUID = -900348704002542821L;

    public IncorrectCaptchaException() {
        super();
    }

    public IncorrectCaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectCaptchaException(String message) {
        super(message);
    }

    public IncorrectCaptchaException(Throwable cause) {
        super(cause);
    }
}
