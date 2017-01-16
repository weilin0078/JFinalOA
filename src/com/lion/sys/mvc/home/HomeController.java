package com.lion.sys.mvc.home;

import com.lion.sys.mvc.base.BaseController;


public class HomeController extends BaseController {
	/***
	 * 首页
	 */
    public void index(){
    	render("/WEB-INF/admin/home/index.html");
    }
    /**
     * 内容页
     * */
    public void content(){
    	render("/WEB-INF/admin/home/content.html");
    }
}
