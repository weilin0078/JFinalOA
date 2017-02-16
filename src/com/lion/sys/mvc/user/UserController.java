/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.lion.sys.mvc.user;

import com.lion.sys.mvc.base.BaseController;

/***
 * 用户管理控制器
 * @author Administrator
 *
 */
public class UserController extends BaseController {
	
    public void index(){
    	render("/WEB-INF/admin/home/index.html");
    }
}
