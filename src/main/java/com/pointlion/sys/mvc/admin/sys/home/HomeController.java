/**

 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.sys.mvc.admin.sys.home;

import com.pointlion.sys.mvc.admin.oa.notice.NoticeService;
import com.pointlion.sys.mvc.admin.oa.workflow.WorkFlowService;
import com.pointlion.sys.mvc.admin.oa.workflow.flowtask.FlowTaskService;
import com.pointlion.sys.mvc.admin.sys.login.SessionUtil;
import com.pointlion.sys.mvc.common.base.BaseController;
import com.pointlion.sys.mvc.common.model.*;
import com.pointlion.sys.mvc.common.utils.Constants;
import com.pointlion.sys.mvc.common.utils.ContextUtil;
import com.pointlion.sys.plugin.shiro.ShiroKit;
import com.pointlion.sys.plugin.shiro.ext.SimpleUser;

import java.util.ArrayList;
import java.util.List;

//import com.pointlion.sys.mvc.admin.apply.resget.OaResGetConstants;
//import com.pointlion.sys.mvc.admin.bumph.BumphConstants;
//import com.pointlion.sys.mvc.admin.login.SessionUtil;
//import com.pointlion.sys.mvc.admin.notice.NoticeService;
//import com.pointlion.sys.mvc.admin.workflow.WorkFlowService;

/***
 * 首页控制器
 */
public class HomeController extends BaseController {
	static WorkFlowService workflowService = WorkFlowService.me;
	static NoticeService noticeService = new NoticeService();
	static FlowTaskService commonFlowService = FlowTaskService.me;
    /***
     * 首页
     */
    public void getHomePage(){
//    	SimpleUser user = ShiroKit.getLoginUser();
    	//获取首页通知公告
    	render("/WEB-INF/admin/home/homePage.html");
    }
	
	/***
	 * 登录成功获取首页
	 */
    public void index(){
    	SimpleUser user = ShiroKit.getLoginUser();
    	String username = user.getUsername();
    	SysUser u = SysUser.dao.getByUsername(username);
    	SessionUtil.setUsernameToSession(this.getRequest(), username);
    	//加载个性化设置
    	SysCustomSetting setting = SysCustomSetting.dao.getCstmSettingByUsername(username);
    	if(setting==null){
    		setAttr("setting", SysCustomSetting.dao.getDefaultCstmSetting());
    	}else{
    		setAttr("setting", setting);
    	}
    	List<SysUser> friends = SysFriend.dao.getUserFriend(u.getId());
    	setAttr("friends", friends);//我的好友
    	setAttr("user", u);//当前用户
    	setAttr("userName", user.getName());//我的姓名
    	setAttr("userEmail", user.getEmail());//我的邮箱
    	//获取首页通知公告
//    	setAttr("NoticeList",noticeService.getMyNotice(user.getId()));
    	List<SysMenu> mlist = new ArrayList<SysMenu>();
    	if(user.getUsername().equals(Constants.SUUUUUUUUUUUUUPER_USER_NAME)){//特殊入口
    		mlist=SysMenu.dao.getAllMenu();
    	}else{
    		//查询所有有权限的菜单
        	mlist = SysRole.dao.getRoleAuthByUserid(u.getId(), "1","#root");//规定只有四级菜单 在这里暂定为A,B,C,D
        	for(SysMenu a : mlist){
        		List<SysMenu> blist = SysRole.dao.getRoleAuthByUserid(u.getId(), "1",a.getId());//A下面的菜单
        		a.setChildren(blist);
        		for(SysMenu b : blist){
        			List<SysMenu> clist = SysRole.dao.getRoleAuthByUserid(u.getId(), "1",b.getId());//B下面的菜单
        			b.setChildren(clist);
        			for(SysMenu c : clist){
            			List<SysMenu> dlist = SysRole.dao.getRoleAuthByUserid(u.getId(), "1",c.getId());//B下面的菜单
            			c.setChildren(dlist);
            		}
        		}
        	}
    	}
    	setAttr("mlist", mlist);
    	setAttr("contextUrl", ContextUtil.getContextUrl(this.getRequest()));
    	render("/WEB-INF/admin/home/index.html");
    }
    
    /***
     * 设定已办数据
     */
    public void setAttrHavedoneList(String username){
    	List<String> havedoneKeyList = commonFlowService.getHavedoneDefkeyList(ShiroKit.getUsername());
    	setAttr("havedoneKeyList", havedoneKeyList);
    }
    
    /***
     * 首页内容页
     */
    public void getMyHome(){
    	render("/WEB-INF/admin/home/myHome.html");
    }
    /**
     * 内容页
     * */
    public void content(){
    	render("/WEB-INF/admin/home/content.html");
    }
    /***
     * 获取消息中心最新消息
     */
    public void getSiteMessageTipPage(){
    	render("/WEB-INF/admin/home/siteMessageTip.html");
    }
    
}
