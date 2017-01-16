package com.lion.sys.mvc.menu;

import java.util.ArrayList;
import java.util.List;

import com.lion.sys.dto.LayTreeNode;
import com.lion.sys.mvc.base.BaseController;


public class MenuController extends BaseController {
	
    public void list(){
    	render("/WEB-INF/admin/menu/list.html");
    }
    
    /***
     * 返回所有菜单
     */
    public void getAllMenuTree(){
    	List<SysMenu> menuList = SysMenu.dao.getAllMenu();
    	List<LayTreeNode> nodelist = SysMenu.dao.toLayTreeNode(menuList);//数据库中的菜单
    	List<LayTreeNode> rootList = new ArrayList<LayTreeNode>();//页面展示的,带根节点
    	//声明根节点
    	LayTreeNode root = new LayTreeNode();
    	root.setId("#root");
    	root.setName("菜单管理");
    	root.setChildren(nodelist);
    	root.setSpread(true);
    	rootList.add(root);
    	renderSuccess(null, rootList, null);
    }
    
    
}
