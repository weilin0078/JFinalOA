package com.lion.sys.mvc.home;

import com.jfinal.core.ActionKey;
import com.lion.sys.mvc.base.BaseController;


public class HomeController extends BaseController {
	
//	@ActionKey("/index")
    public void index(){
    	render("/WEB-INF/admin/home/index.html");
    }
}
