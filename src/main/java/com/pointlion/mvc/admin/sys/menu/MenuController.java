/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.mvc.admin.sys.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import com.pointlion.mvc.common.base.BaseController;
import com.pointlion.mvc.common.dto.ZtreeNode;
import com.pointlion.mvc.common.model.SysMenu;
import com.pointlion.mvc.common.utils.UuidUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/***
 * 菜单管理控制器
 */
public class MenuController extends BaseController {
	
	//@RequiresPermissions("admin:menu:list")
    public void getListPage(){
    	setAttr("menu", SysMenu.dao.getAllMenu());
    	renderIframe("list.html");
    }


	public void getFunListPage(){
		setAttr("menu", SysMenu.dao.getAllMenu());
		renderIframe("funList.html");
	}

    /***
     * 返回所有菜单
     */
    public void getAllMenuTree(){
    	List<SysMenu> menuList = SysMenu.dao.getAllMenu();
    	List<ZtreeNode> nodelist = SysMenu.dao.toZTreeNode(menuList,true);//数据库中的菜单
    	List<ZtreeNode> rootList = new ArrayList<ZtreeNode>();//页面展示的,带根节点
    	//声明根节点
    	ZtreeNode root = new ZtreeNode();
    	root.setId("#root");
    	root.setName("菜单");
    	root.setChildren(nodelist);
    	root.setOpen(true);
    	rootList.add(root);
    	renderJson(rootList);
    }
    /***
     * 获取分页数据
     */
    public void listData(){
    	String curr = getPara("pageNumber");
    	String pageSize = getPara("pageSize");
    	String pid = getPara("pid");
    	if(StrKit.isBlank(pid)){
    		pid = "#root";
    	}
    	Page<Record> page = SysMenu.dao.getChildrenPageByPid(Integer.valueOf(curr),Integer.valueOf(pageSize),pid);
    	renderPage(page.getList(),"" ,page.getTotalRow());
    }
    
    /***
     * 获取编辑页面
     */
    public void getEditPage(){

    	//添加和修改
    	String id = getPara("id");
    	if(StrKit.notBlank(id)){
    		SysMenu menu = SysMenu.dao.getById(id);
    		SysMenu parent = SysMenu.dao.getById(menu.getParentId());
    		setAttr("m", menu);
    		setAttr("p", parent);
    	}
    	//添加子模块
    	String parentid = getPara("parentid");
    	if(StrKit.notBlank(parentid)){
    		SysMenu parent = SysMenu.dao.getById(parentid);
    		setAttr("p", parent);
    	}
    	renderIframe("edit.html");
    }
	/***
	 * 获取编辑页面
	 */
	public void getFunEditPage(){
		//添加和修改
		String id = getPara("id");
		if(StrKit.notBlank(id)){
			SysMenu menu = SysMenu.dao.getById(id);
			SysMenu parent = SysMenu.dao.getById(menu.getParentId());
			setAttr("m", menu);
			setAttr("p", parent);
		}
		//添加子模块
		String parentid = getPara("parentid");
		if(StrKit.notBlank(parentid)){
			SysMenu parent = SysMenu.dao.getById(parentid);
			setAttr("p", parent);
		}
		renderIframe("funEdit.html");
	}
    /***
     * 获取选择父级页面
     */
    public void getSelectOneMenuPage(){
    	renderIframe("selectOneMenu.html");
    }
    /***
     * 保存
     */
    public void save(){
    	SysMenu menu = getModel(SysMenu.class);
    	if(StrKit.notBlank(menu.getId())){
    		if(StrKit.isBlank(menu.getIfShow())){//不显示
    			menu.setIfShow("0");
    		}
    		menu.update();
    	}else{
    		menu.setId(UuidUtil.getUUID());
    		if(StrKit.isBlank(menu.getIfShow())){//不显示
    			menu.setIfShow("0");
    		}
    		menu.save();
    	}
    	renderSuccess();
    }
    /***
     * 删除
     * @throws Exception
     */
    public void delete() throws Exception{
		String ids = getPara("ids");
    	String idarr[] = ids.split(",");
    	for(String id : idarr){
    		List<SysMenu> list = SysMenu.dao.getChildrenByPid(id);
    		if(list.size()<=0){
    			SysMenu menu = SysMenu.dao.getById(id);
    			if(menu!=null){
    				menu.delete();//删除
    			}
    		}else{
    			renderError("有子菜单,不允许删除!");
    			return;
    		}
    	}
    	renderSuccess();
    }
    
    
    /***
     * 获取更换图标的页面
     */
    public void getChangeIconPage(){
    	setAttr("mid", getPara("mid"));
    	renderIframe("changeIcon.html");
    }
    /***
     * 更换图标
     */
    public void changeIcon(){
    	String mid = getPara("mid");
    	SysMenu m = SysMenu.dao.getById(mid);
    	m.setIcon(getPara("icon"));
    	m.update();
    	renderSuccess();
    }
    
    /***
     * 更高菜单是否显示
     */
    public void changeIfShow(){
    	String id = getPara("id","");
    	String ifshow = getPara("ifshow","");
    	SysMenu menu = SysMenu.dao.getById(id);
    	if(menu!=null){
    		menu.setIfShow(ifshow);
    		menu.update();
    		renderSuccess();
    	}else{
    		renderError();
    	}
    }

	/***
	 * 更换菜单顺序
	 */
	public void changeSort(){
		String id = getPara("id","");
		String sort = getPara("sort","");
		SysMenu menu = SysMenu.dao.getById(id);
		if(menu!=null){
			menu.setSort(Integer.parseInt(sort));
			menu.update();
			renderSuccess();
		}else{
			renderError();
		}
	}

}
