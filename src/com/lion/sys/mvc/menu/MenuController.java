package com.lion.sys.mvc.menu;

import com.lion.sys.mvc.base.BaseController;


public class MenuController extends BaseController {
	
    public void index(){
    	render("/WEB-INF/admin/home/index.html");
    }
}
