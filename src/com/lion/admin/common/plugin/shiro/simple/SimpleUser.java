/**
 * Copyright (C) 2014 陕西威尔伯乐信息技术有限公司
 * @Package com.wellbole.web.core.shiro  
 * @Title: SimpleUser.java  
 * @Description: 简单用户对象，用于在Session中保存用户对象 
 * @author 李飞 (lifei@wellbole.com)    
 * @date 2014年11月11日  上午4:31:55  
 * @since V1.0.0 
 *
 * Modification History:
 * Date         Author      Version     Description
 * -------------------------------------------------------------
 * 2014年11月11日      李飞                       V1.0.0        新建文件   
 */
package com.lion.admin.common.plugin.shiro.simple;

import java.io.Serializable;

/**  
 * @ClassName: SimpleUser  
 * @Description: 简单用户对象，用于在Session中保存用户对象
 * @author 李飞 (lifei@wellbole.com)   
 * @date 2014年11月11日 上午4:31:55
 * @since V1.0.0  
 */

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
