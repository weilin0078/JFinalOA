package com.pointlion.config.routes;

import com.jfinal.config.Routes;
import com.pointlion.mvc.mobile.common.MobileController;
import com.pointlion.mvc.mobile.login.MobileLoginController;

public class MobileRoutes extends Routes{

	@Override
	public void config() {
		//手机端接口
		add("/mobile/common",MobileController.class);
		add("/mobile/login",MobileLoginController.class);
	}

}
