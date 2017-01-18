package com.lion.sys.mvc.home;

import com.lion.sys.mvc.base.BaseController;


public class HomeController extends BaseController {
	/***
	 * 首页
	 */
    public void index(){
    	render("/WEB-INF/admin/home/index.html");
    }
    public void getMainPage(){
    	render("/WEB-INF/admin/home/main.html");
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
