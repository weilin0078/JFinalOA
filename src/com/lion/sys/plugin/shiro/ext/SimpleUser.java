package com.lion.sys.plugin.shiro.ext;

import java.io.Serializable;



public class SimpleUser implements Serializable{
	private static final long serialVersionUID = 1L;
	private final String id;
	private final String username;
	private final String name;
	private String roleName;
	public SimpleUser(String id, String username,String name) {
		this.id = id;
		this.username = username;
		this.name = name;
	}
	public final String getId() {
		return id;
	}
	/**
	 * @Title: getUsername  
	 * @Description: 获得用户名 
	 * @return 
	 * @since V1.0.0
	 */
	public final String getUsername() {
		return username;
	}
	/**
	 * @Title: getRoleName  
	 * @Description: 获得角色名称 
	 * @return 
	 * @since V1.0.0
	 */
	public final String getRoleName() {
		return roleName;
	}
	/**
	 * @Title: setRoleName  
	 * @Description: 设定角色名称  
	 * @param roleName 
	 * @since V1.0.0
	 */
	public final void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getName() {
		return name;
	}
	
}
