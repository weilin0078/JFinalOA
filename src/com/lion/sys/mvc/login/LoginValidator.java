package com.lion.sys.mvc.login;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;


public class LoginValidator extends Validator {

	@Override
	protected void validate(Controller c) {
        validateRequired("username", "msg", "用户名不能为空");
        validateRequired("password", "msg", "密码不能为空");
	}

	@Override
	protected void handleError(Controller c) {
		c.keepPara("username");
        c.createToken("loginToken");
        c.render("/templates/account/login.html");
	}

}
