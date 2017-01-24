/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.lion.sys.mvc.base;

import com.jfinal.log.Log;
import com.jfinal.validate.Validator;
import com.lion.sys.tool.ToolString;

/**
 * 扩展验证方法
 * @author 董华健  dongcb678@163.com
 */
public abstract class BaseValidator extends Validator {

	@SuppressWarnings("unused")
	private static final Log log = Log.getLog(BaseValidator.class);
	

	/**
	 * 
	 */
	public void instance(){
		
	}

	/**
	 * 验证手机号
	 */
	protected void validateMobile(String field, String errorKey, String errorMessage) {
		validateRegex(field, ToolString.regExp_mobile, false, errorKey, errorMessage);
	}

	/**
	 * 验证邮编
	 */
	protected void validatePostbody(String field, String errorKey, String errorMessage) {
		validateRegex(field, ToolString.regExp_postbody, false, errorKey, errorMessage);
	}

	/**
	 * 验证身份证
	 */
	protected void validateIdCard(String field, String errorKey, String errorMessage) {
		validateRegex(field, ToolString.regExp_idCard, false, errorKey, errorMessage);
	}

	/**
	 * 验证IP
	 */
	protected void validateIp(String field, String errorKey, String errorMessage) {
		validateRegex(field, ToolString.regExp_ip, false, errorKey, errorMessage);
	}
	
}
