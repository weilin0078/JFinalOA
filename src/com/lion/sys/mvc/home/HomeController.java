package com.lion.sys.mvc.home;

import com.lion.sys.mvc.base.BaseController;


public class HomeController extends BaseController {
	
    public void index(){
    	render("/WEB-INF/admin/home/index.html");
    }
}