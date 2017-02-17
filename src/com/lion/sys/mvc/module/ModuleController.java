/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.lion.sys.mvc.module;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.lion.sys.dto.LayTreeNode;
import com.lion.sys.mvc.base.BaseController;
import com.lion.sys.tool.UuidUtil;

/***
 * 菜单管理控制器
 */
public class ModuleController extends BaseController {
	/***
	 * 获取列表页面
	 */
    public void getListPage(){
    	render("/WEB-INF/admin/module/list.html");
    }
    /***
     * 获取编辑页面
     */
    public void getEditPage(){
    	//添加和修改
    	String id = getPara("id");
    	if(StringUtils.isNotBlank(id)){
    		SysModule module = SysModule.dao.getById(id);
    		SysModule parent = SysModule.dao.getById(module.getParentId());
    		setAttr("module", module);
    		setAttr("parent", parent);
    	}
    	//添加子模块
    	String parentid = getPara("parentid");
    	if(StringUtils.isNotBlank(parentid)){
    		SysModule parent = SysModule.dao.getById(parentid);
    		setAttr("parent", parent);
    	}
    	render("/WEB-INF/admin/module/edit.html");
    }
    /***
     * 获取选择父级页面
     */
    public void getSelectParentPage(){
    	render("/WEB-INF/admin/module/selectParent.html");
    }
    /***
     * 返回所有菜单
     */
    public void getAllMenuTree(){
    	List<SysModule> menuList = SysModule.dao.getAllMenu();
    	List<LayTreeNode> nodelist = SysModule.dao.toLayTreeNode(menuList,true);//数据库中的菜单
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
    	Page<SysModule> page = SysModule.dao.getChildrenPageByPid(Integer.valueOf(curr),Integer.valueOf(pageSize),pid);
    	renderPage(page.getList(),"" ,page.getTotalRow());
    }
    /***
     * 保存
     */
    public void save(){
    	SysModule module = getModel(SysModule.class);
    	if(StringUtils.isNotBlank(module.getId())){
    		module.update();
    	}else{
    		module.setId(UuidUtil.getUUID());
    		module.save();
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
    		List<SysModule> list = SysModule.dao.getChildrenByPid(id);
    		if(list.size()<=0){
    			SysModule module = SysModule.dao.getById(id);
    			module.delete();//删除
    		}else{
    			renderError("有子模块,不允许删除!");
    			return;
    		}
    	}
    	renderSuccess();
    }
}
