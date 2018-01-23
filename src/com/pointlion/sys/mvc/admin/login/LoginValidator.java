/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.sys.mvc.admin.login;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;


public class LoginValidator extends Validator {

	@Override
	protected void validate(Controller c) {
        validateRequired("username", "error", "用户名不能为空!");
        validateRequired("password", "error", "密码不能为空!");
        validateRequired("captcha", "error", "验证码不能为空!");
	}

	@Override
	protected void handleError(Controller c) {
		c.keepPara("username");
        c.createToken("loginToken");
        c.render("/login.html");
	}

}
