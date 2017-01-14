package com.lion.sys.plugin.shiro.ext;

import java.io.Serializable;



public class SimpleUser implements Serializable{
	private static final long serialVersionUID = 1L;
	private final Long id;
	private final String username;
	private final String description;
	private final String type;
	private String roleName;
	public SimpleUser(Long id, String username, String description, String type) {
		super();
		this.id = id;
		this.username = username;
		this.description = description;
		this.type = type;
	}
	public final Long getId() {
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
	 * @Title: getType  
	 * @Description: 获得用户类型（web（前台）／admin（后台））  
	 * @return 
	 * @since V1.0.0
	 */
	public final String getType() {
		return type;
	}
	/**
	 * @Title: getDescription  
	 * @Description: 获得用户描述（一般为真名）
	 * @return 
	 * @since V1.0.0
	 */
	public final String getDescription() {
		return description;
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
}
