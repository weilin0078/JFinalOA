/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.lion.sys.mvc.menu;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.lion.sys.dto.LayTreeNode;
import com.lion.sys.mvc.base.BaseController;

/***
 * 菜单管理控制器
 */
public class MenuController extends BaseController {
	
    public void getListPage(){
    	render("/WEB-INF/admin/menu/list.html");
    }
    
    /***
     * 返回所有菜单
     */
    public void getAllMenuTree(){
    	List<SysMenu> menuList = SysMenu.dao.getAllMenu();
    	List<LayTreeNode> nodelist = SysMenu.dao.toLayTreeNode(menuList,false);//数据库中的菜单
    	List<LayTreeNode> rootList = new ArrayList<LayTreeNode>();//页面展示的,带根节点
    	//声明根节点
    	LayTreeNode root = new LayTreeNode();
    	root.setId("#root");
    	root.setName("根目录");
    	root.setChildren(nodelist);
    	root.setSpread(true);
    	rootList.add(root);
    	renderSuccess(rootList, null);
    }
    /***
     * 获取分页数据
     */
    public void listData(){
    	String curr = getPara("pageIndex");
    	String pageSize = getPara("pageSize");
    	String pid = getPara("pid");
    	if(StrKit.isBlank(pid)){
    		pid = "#root";
    	}
    	Page<SysMenu> page = SysMenu.dao.getChildrenPageByPid(Integer.valueOf(curr),Integer.valueOf(pageSize),pid);
    	renderPage(page.getList(),"" ,page.getTotalRow());
    }
    /***
     * 获取选择对应功能页面
     */
    public void getSelectOperate(){
    	render("/WEB-INF/admin/menu/selectOperate.html");
    }
}
