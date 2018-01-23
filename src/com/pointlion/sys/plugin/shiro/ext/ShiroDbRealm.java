package com.pointlion.sys.plugin.shiro.ext;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.jfinal.kit.StrKit;
import com.pointlion.sys.mvc.common.model.SysMenu;
import com.pointlion.sys.mvc.common.model.SysRole;
import com.pointlion.sys.mvc.common.model.SysUser;


public class ShiroDbRealm extends AuthorizingRealm {
    
    public ShiroDbRealm(){
        setAuthenticationTokenClass(CaptchaUsernamePasswordToken.class);
    }

    /**
     * 认证回调函数,登录时调用.
     */    
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token){
        CaptchaUsernamePasswordToken authcToken = (CaptchaUsernamePasswordToken) token;
        //验证用户名
        String username = authcToken.getUsername();
        if (StrKit.isBlank(username)) {
            throw new AuthenticationException("用户名不可以为空");
        }
        //验证验证码
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(false);
        String md5Code = null;
        if(session != null){
            md5Code = (String)session.getAttribute(CaptchaRender.DEFAULT_CAPTCHA_MD5_CODE_KEY);
        }
        boolean isRight = CaptchaRender.validate(md5Code, authcToken.getCaptcha());
        if (!isRight) {
            throw new IncorrectCaptchaException("验证码错误!");
        }
        //验证用户是否存在
        SysUser user = SysUser.dao.getByUsername(username);
        if(user==null){
        	throw new UnknownAccountException("用户不存在!");
        }else{
        	//验证密码
        	String password = String.valueOf(authcToken.getPassword());
        	PasswordService svc = new DefaultPasswordService();
        	if(svc.passwordsMatch(password, user.getPassword())){
        		return  new SimpleAuthenticationInfo(new SimpleUser(user.getId(), user.getUsername(),user.getName(),user.getEmail()), password, getName());
        	}else{
        		throw new AuthenticationException("密码错误!");
        	}
        }
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	SimpleUser simpleUser = (SimpleUser) principals.fromRealm(getName()).iterator().next();
    	SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if( null == simpleUser){
        	return info;
        }
        SysUser user = SysUser.dao.findById(simpleUser.getId());
        if( null == user){
        	return info;
        }
        List<SysMenu> authlist = SysRole.dao.getRoleAuthByUserid(user.getId(),null,null);
	    for(SysMenu o :authlist){
	    	if(StrKit.notBlank(o.getPermission())){
	    		info.addStringPermission(o.getPermission());//获取资源名称
	    	}
	    }
        return info;
    }
    

    /**
     * 更新用户授权信息缓存.
     */
    public void clearCachedAuthorizationInfo(String principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清除所有用户授权信息缓存.
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }
}
