/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.lion.sys.mvc.menu;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.lion.sys.dto.LayTreeNode;
import com.lion.sys.mvc.base.BaseController;
import com.lion.sys.tool.UuidUtil;

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
    	Page<Record> page = SysMenu.dao.getChildrenPageByPid(Integer.valueOf(curr),Integer.valueOf(pageSize),pid);
    	renderPage(page.getList(),"" ,page.getTotalRow());
    }
    /***
     * 获取选择对应功能页面
     */
    public void getSelectOperate(){
    	setAttr("mid",getPara("mid"));
    	render("/WEB-INF/admin/menu/selectOperate.html");
    }
    
    /***
     * 给菜单绑定一个功能
     */
    public void addOperateToMenu(){
    	String mid = getPara("mid");
    	String operateId = getPara("oid");
    	SysMenu m = SysMenu.dao.getById(mid);
    	m.setOperatorid(operateId);
    	m.update();
    	renderSuccess();
    }
    /***
     * 获取编辑页面
     */
    public void getEditPage(){
    	//添加和修改
    	String id = getPara("id");
    	if(StringUtils.isNotBlank(id)){
    		SysMenu menu = SysMenu.dao.getById(id);
    		SysMenu parent = SysMenu.dao.getById(menu.getParentId());
    		setAttr("menu", menu);
    		setAttr("parent", parent);
    	}
    	//添加子模块
    	String parentid = getPara("parentid");
    	if(StringUtils.isNotBlank(parentid)){
    		SysMenu parent = SysMenu.dao.getById(parentid);
    		setAttr("parent", parent);
    	}
    	render("/WEB-INF/admin/menu/edit.html");
    }
    /***
     * 获取选择父级页面
     */
    public void getSelectPage(){
    	render("/WEB-INF/admin/menu/selectMenu.html");
    }
    /***
     * 保存
     */
    public void save(){
    	SysMenu menu = getModel(SysMenu.class);
    	if(StringUtils.isNotBlank(menu.getId())){
    		menu.update();
    	}else{
    		menu.setId(UuidUtil.getUUID());
    		menu.save();
    	}
    	renderSuccess();
    }
    /***
     * 删除
     * @throws Exception
     */
    @Before(Tx.class)
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
}
