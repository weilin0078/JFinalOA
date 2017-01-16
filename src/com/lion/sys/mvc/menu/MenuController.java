package com.lion.sys.mvc.menu;

import com.lion.sys.mvc.base.BaseController;


public class MenuController extends BaseController {
	
    public void list(){
    	render("/WEB-INF/admin/menu/list.html");
    }
}
