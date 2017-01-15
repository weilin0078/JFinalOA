package com.lion.sys.mvc.user;

import com.lion.sys.mvc.base.BaseController;


public class UserController extends BaseController {
	
    public void index(){
    	render("/WEB-INF/admin/home/index.html");
    }
}
