/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.lion.sys.mvc.login;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;

import com.jfinal.aop.Before;
import com.lion.sys.mvc.base.BaseController;
import com.lion.sys.mvc.user.SysUser;
import com.lion.sys.plugin.shiro.ext.CaptchaFormAuthenticationInterceptor;
import com.lion.sys.plugin.shiro.ext.CaptchaRender;
import com.lion.sys.plugin.shiro.ext.CaptchaUsernamePasswordToken;
import com.lion.sys.plugin.shiro.ext.IncorrectCaptchaException;


public class LoginController extends BaseController {
    
    private static final Logger LOG = Logger.getLogger(LoginController.class);
    private static final int DEFAULT_CAPTCHA_LEN = 4;//验证码长度
    private static final String LOGIN_URL = "/admin/login";
    
    public void index(){
    	this.createToken("loginToken");
    	Subject subject = ThreadContext.getSubject();
    	if(subject.isAuthenticated()){//已经登陆成功
    		this.redirect("/admin/home");
    	}else{
    		this.render("/login.html");
    	}
    }
    
    /**
     * @Title: img  
     * @Description: 图形验证码   
     * @since V1.0.0
     */
    public void img(){
        CaptchaRender img = new CaptchaRender(DEFAULT_CAPTCHA_LEN);
        this.setSessionAttr(CaptchaRender.DEFAULT_CAPTCHA_MD5_CODE_KEY, img.getMd5RandonCode());
        render(img);
    }
    
    @Before({LoginValidator.class, CaptchaFormAuthenticationInterceptor.class})
    public void doLogin(){
        try {
            CaptchaUsernamePasswordToken token = this.getAttr("shiroToken");
            Subject subject = SecurityUtils.getSubject();
            ThreadContext.bind(subject);
            //进行用用户名和密码验证
            if(subject.isAuthenticated()){
            	subject.logout();
            }else{
            	subject.login(token);
            }
            //调转到主页面
            this.redirect("/admin/home");
        }catch (IncorrectCaptchaException e) {
        	LOG.error(e.getMessage());
        	setAttr("error", e.getMessage());
        	this.render("/login.html");
        } catch (LockedAccountException e) {
        	LOG.error(e.getMessage());
        	setAttr("error", "账号已被锁定!");
        	this.render("/login.html");
        } catch (AuthenticationException e) {
        	LOG.error(e.getMessage());
        	setAttr("error", e.getMessage());
        	this.render("/login.html");
        } catch (Exception e) {
            LOG.error(e.getMessage());
            setAttr("error", "系统异常!");
        	this.render("/login.html");
        }
    }
    @RequiresAuthentication
    public void logout() {
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.logout();
            this.removeSessionAttr("user");
            this.redirect(LOGIN_URL);
        } catch (SessionException ise) {
            LOG.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
        } catch (Exception e) {
            LOG.debug("登出发生错误", e);
        }
    }
    
    
    public void doReg() {  
            PasswordService svc = new DefaultPasswordService();  
            String encrypted = svc.encryptPassword("liyang");  
            SysUser user = new SysUser();
            user.set("username", "li");
            user.set("password", encrypted);
            user.save();
    }
}
