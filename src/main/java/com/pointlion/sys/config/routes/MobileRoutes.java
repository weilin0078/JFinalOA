package com.pointlion.sys.config.routes;

import com.jfinal.config.Routes;
import com.pointlion.sys.mvc.mobile.common.MobileController;
import com.pointlion.sys.mvc.mobile.login.MobileLoginController;

public class MobileRoutes extends Routes{

	@Override
	public void config() {
		//手机端接口
		add("/mobile/common",MobileController.class);
		add("/mobile/login",MobileLoginController.class);
	}

}
