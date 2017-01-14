package com.lion.sys.plugin.shiro.ext;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.jfinal.kit.StrKit;
import com.lion.sys.plugin.shiro.ext.CaptchaRender;


public class ShiroDbRealm extends AuthorizingRealm {
    
    public ShiroDbRealm(){
        setAuthenticationTokenClass(CaptchaUsernamePasswordToken.class);
    }

    /**
     * 认证回调函数,登录时调用.
     */    
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token){
        CaptchaUsernamePasswordToken authcToken = (CaptchaUsernamePasswordToken) token;
        String accountName = authcToken.getUsername();
        if (StrKit.isBlank(accountName)) {
            throw new AuthenticationException("用户名不可以为空");
        }
        boolean isCaptchaBlank = StrKit.isBlank(authcToken.getCaptcha());
        if (isCaptchaBlank) {
            throw new IncorrectCaptchaException("验证码不可以为空!");
        }
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
        User user = User.DAO.findByUsername(accountName);
        if (null == user) {
            throw new AuthenticationException("用户名或者密码错误");
        }
        if (user.getBoolean("is_locked")) {
            throw new LockedAccountException("该用户已被锁定");
        }
        return  new SimpleAuthenticationInfo(new SimpleUser(user.getId(),user.getStr("username"),user.getStr("description"), user.getStr("type")), user.getStr("password"), getName());
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //User user = (User) principals.fromRealm(getName()).iterator().next();
    	SimpleUser simpleUser = (SimpleUser) principals.fromRealm(getName()).iterator().next();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if( null == simpleUser){
        	return info;
        }
        User user = User.DAO.findById(simpleUser.getId());
        if( null == user){
        	return info;
        }
        List<Role> roles = user.getRoles();
        if(ArrayKit.isNotEmpty(roles)){
            for(Role role : roles){
                //角色的名称及时角色的值
                info.addRole(role.getStr("name"));
                addResourceOfRole(role,info);
            }
        }
        return info;
    }
    
    private void addResourceOfRole(Role role, SimpleAuthorizationInfo info){
    	List<Resource> resources = role.getResources();
        if(ArrayKit.isNotEmpty(resources)){
            for(Resource resource : resources ){
                //资源代码就是权限值，类似user：list
                info.addStringPermission(resource.getStr("code"));
            }
        }
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
