package com.pointlion.sys.mvc.admin.sys.user;

import com.pointlion.sys.mvc.common.model.SysUser;

import java.util.List;


public class SysUserService {
	public static final SysUserService me = new SysUserService();
	
	/***
	 * 获取所有用户
	 * @return
	 */
	public List<SysUser> getAllUser(){
		return SysUser.dao.find("select * from sys_user");
	}
}