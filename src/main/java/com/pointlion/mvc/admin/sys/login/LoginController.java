/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.mvc.admin.sys.login;

import com.jfinal.aop.Before;
import com.jfinal.log.Log;
import com.pointlion.annotation.ServerLog;
import com.pointlion.enums.BusinessType;
import com.pointlion.mvc.common.base.BaseController;
import com.pointlion.mvc.common.model.SysCustomSetting;
import com.pointlion.mvc.common.model.SysUser;
import com.pointlion.plugin.shiro.ShiroKit;
import com.pointlion.plugin.shiro.ext.CaptchaFormAuthenticationInterceptor;
import com.pointlion.plugin.shiro.ext.CaptchaRender;
import com.pointlion.plugin.shiro.ext.CaptchaUsernamePasswordToken;
import com.pointlion.plugin.shiro.ext.IncorrectCaptchaException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;


public class LoginController extends BaseController {

    private static final Log LOG = Log.getLog(LoginController.class);
    private static final int DEFAULT_CAPTCHA_LEN = 4;//验证码长度
    private static final String LOGIN_URL = "/admin/login";
    
    /***
     * 首页或者登录
     */
    public void index(){
//    	ChineseNameUtil.generateUser();//自动生成模拟用户，每个单位下新建30个用户。并且修改组织机构名称（随机汉字单位名称）
    	this.createToken("loginToken");
    	Subject subject = ThreadContext.getSubject();
    	if(subject.isAuthenticated()){//已经登录成功
    		SessionUtil.setUsernameToSession(this.getRequest(), ShiroKit.getUsername());
    		SysCustomSetting setting = SysCustomSetting.dao.getLoginUserCstSetting();
    		if(setting!=null&&setting.getNavColl().equals("0")){
    			if(setting.getOffType().equals("push")){
            		this.redirect("/admin/home?&offcanvas=push");
            	}else if(setting.getOffType().equals("slide")){
            		this.redirect("/admin/home?&offcanvas=slide");
            	}else if(setting.getOffType().equals("reveal")){
            		this.redirect("/admin/home?&offcanvas=reveal");        		
            	}else{
            		this.redirect("/admin/home");
            	}
    		}else{
    			this.redirect("/admin/home");
    		}
    	}else{
    		this.render("/login.html");
    	}
    }
    /***
     * 没有权限访问的
     */
    public void needPermission(){
    	renderIframe("/error/needPermission.html");
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

    @ServerLog(module = "PC端用户登录",businessType = BusinessType.LOGIN)
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
