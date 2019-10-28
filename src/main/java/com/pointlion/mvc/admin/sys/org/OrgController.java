/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.mvc.admin.sys.org;

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
import com.pointlion.mvc.common.model.SysOrg;
import com.pointlion.mvc.common.model.SysRole;
import com.pointlion.mvc.common.utils.ContextUtil;
import com.pointlion.mvc.common.utils.UuidUtil;
import com.pointlion.plugin.shiro.ShiroKit;

/***
 * 菜单管理控制器
 */

public class OrgController extends BaseController {
	/***
	 * 获取列表页面
	 */
	public void getListPage(){
//		String userParentChildCompanyId = ShiroKit.getUserParentChileCompanyId();//上级子公司id
		setAttr("pid",ShiroKit.getUserOrgId());
    	renderIframe("list.html");
    }
    /***
     * 获取树
     */
    public void getOrgTree(){
    	String orgid = getPara("orgid");
    	String ifAllChild = "1";//默认查询孩子
    	Boolean open = false;//是否展开所有
    	Boolean ifOnlyLeaf = false;//是否只选叶子
    	if(StrKit.notBlank(getPara("ifAllChild"))){//是否查询所有孩子
    		ifAllChild = getPara("ifAllChild");
    	}
    	if(StrKit.notBlank(getPara("ifOpen"))){//是否查询所有孩子
    		if("1".equals(getPara("ifOpen"))){
    			open = true;
    		}
    	}
    	if(StrKit.notBlank(getPara("ifOnlyLeaf"))){//是否查询所有孩子
    		if("1".equals(getPara("ifOnlyLeaf"))){
    			ifOnlyLeaf = true;
    		}
    	}
    	if(SysRole.dao.ifSuperAdmin(ShiroKit.getUserId())){//如果是超级管理员
    		List<SysOrg> menuList = SysOrg.dao.getChildrenAllTree("#root");
        	List<ZtreeNode> nodelist = SysOrg.dao.toZTreeNode(menuList,open,ifOnlyLeaf);//数据库中的菜单
        	List<ZtreeNode> rootList = new ArrayList<ZtreeNode>();//页面展示的,带根节点
        	//声明根节点
        	SysOrg rootOrg = SysOrg.dao.getById("#root");
        	ZtreeNode root = new ZtreeNode();
        	root.setId("#root");
        	if(rootOrg==null){
        		root.setName("公司组织结构");
        	}else{
        		root.setName(rootOrg.getName());
        	}
        	root.setChildren(nodelist);
        	root.setOpen(true);
        	root.setIcon(ContextUtil.getCtx()+"/common/plugins/zTree_v3/css/zTreeStyle/img/diy/9.png");
        	rootList.add(root);
        	renderJson(rootList);
    	}else{
    		if(StrKit.isBlank(orgid)){//如果没传递过来机构id，使用子公司id
    			orgid = ShiroKit.getUserParentChileCompanyId();
    		}
    		if(StrKit.isBlank(orgid)){//如果没传递过来机构id，使用子公司id
    			orgid = ShiroKit.getUserOrgId();
    		}
    		List<SysOrg> menuList = new ArrayList<SysOrg>();
    		if("1".equals(ifAllChild)){
    			menuList = SysOrg.dao.getChildrenAllTree(orgid);
    		}else{
    			menuList = SysOrg.dao.getChildrenByPid(orgid, "0");
    		}
        	List<ZtreeNode> nodelist = SysOrg.dao.toZTreeNode(menuList,open,ifOnlyLeaf);//数据库中的菜单
        	List<ZtreeNode> rootList = new ArrayList<ZtreeNode>();//页面展示的,带根节点
        	SysOrg org = SysOrg.dao.getById(orgid);//传进来的id
        	ZtreeNode root = new ZtreeNode();
        	if(org!=null){
        		root.setId(org.getId());
        		root.setName(org.getName());
        	}
        	root.setChildren(nodelist);
        	root.setOpen(true);
        	root.setIcon(ContextUtil.getCtx()+"/common/plugins/zTree_v3/css/zTreeStyle/img/diy/1_open.png");
        	rootList.add(root);
        	renderJson(rootList);
    	}
    }
    public void getOrgTreeOnlyLeaf(){
    	List<SysOrg> menuList = SysOrg.dao.getChildrenAllTree("#root");
    	List<ZtreeNode> nodelist = SysOrg.dao.toZTreeNode(menuList,true,true);//数据库中的菜单
    	List<ZtreeNode> rootList = new ArrayList<ZtreeNode>();//页面展示的,带根节点
    	//声明根节点
    	ZtreeNode root = new ZtreeNode();
    	root.setId("#root");
    	root.setName("公司部门组织结构");
    	root.setChildren(nodelist);
    	root.setOpen(true);
    	root.setNocheck(true);
    	rootList.add(root);
    	renderJson(rootList);
    }
    /***
     * 获取分页数据
     **/
    public void listData(){
    	String curr = getPara("pageNumber");
    	String pageSize = getPara("pageSize");
    	String pid = getPara("pid");
    	Page<Record> page = SysOrg.dao.getChildrenPageByPid(Integer.valueOf(curr),Integer.valueOf(pageSize),pid);
    	renderPage(page.getList(),"" ,page.getTotalRow());
    }
    
    /***
     * 获取编辑页面
     */
    public void getEditPage(){
    	setAttr("type", getPara("type"));
    	//添加和修改
    	String id = getPara("id");
    	if(StrKit.notBlank(id)){//修改
    		SysOrg o = SysOrg.dao.getById(id);
    		SysOrg parent = SysOrg.dao.getById(o.getParentId());
    		setAttr("o", o);
    		setAttr("p", parent);
    		setAttr("type", o.getType());
    		if("1".equals(parent.getType())){//父级就是公司
    			setAttr("parentCompany", parent);//父级子公司
    		}else if("0".equals(parent.getType())){//父级是部门
    			String parentCompanyId = parent.getParentChildCompanyId();//该部门所属子公司
        		SysOrg parentCompany = SysOrg.dao.getById(parentCompanyId);
        		setAttr("parentCompany", parentCompany);//父级子公司
    		}
    	}
    	String parentid = getPara("parentid");
    	if(StrKit.notBlank(parentid)){
    		SysOrg parent = SysOrg.dao.getById(parentid);
    		setAttr("p", parent);//父级机构
    		if("1".equals(parent.getType())){//父级就是公司
    			setAttr("parentCompany", parent);//父级子公司
    		}else if("0".equals(parent.getType())){//父级是部门
    			String parentCompanyId = parent.getParentChildCompanyId();//该部门所属子公司
        		SysOrg parentCompany = SysOrg.dao.getById(parentCompanyId);
        		setAttr("parentCompany", parentCompany);//父级子公司
    		}
    	}
    	renderIframe("edit.html");
    }
    /***
     * 保存
     */
    public void save(){
    	SysOrg o = getModel(SysOrg.class);
    	if(StrKit.notBlank(o.getId())){
    		o.update();
    	}else{
    		o.setId(UuidUtil.getUUID());
    		o.save();
    	}
    	renderSuccess();
    }
    /***
     * 获取选择树页面
     * */
    public void getSelectOneOrgPage(){
    	String orgid = getPara("orgid");
    	String ifAllChild = getPara("ifAllChild");
    	String ifOpen = getPara("ifOpen");
    	String ifOnlyLeaf = getPara("ifOnlyLeaf");
    	setAttr("orgid", orgid);
    	setAttr("ifAllChild", ifAllChild);
    	setAttr("ifOpen", ifOpen);
    	setAttr("ifOnlyLeaf", ifOnlyLeaf);
    	renderIframe("/WEB-INF/admin/sys/org/selectOneOrg.html");
    }
    public void getSelectManyOrgPage(){
    	renderIframe("/WEB-INF/admin/sys/org/selectManyOrg.html");
    }
    
    /***
     * 删除
     * @throws Exception
     */
    public void delete() throws Exception{
		String ids = getPara("ids");
    	String idarr[] = ids.split(",");
    	for(String id : idarr){
    		List<SysOrg> list = SysOrg.dao.getChildrenByPid(id,null);
    		if(list.size()>0){
    			renderError("有子菜单,不允许删除!");
    			return;
    		}
    	}
    	//执行删除
    	SysOrg.dao.deleteByIds(ids);
    	renderSuccess("删除成功!");
    }
    /***
     * 同级别的组织结构下，不允许重名
     */
    public void validOrgname(){
    	SysOrg org = getModel(SysOrg.class);
    	if(org!=null){
    		String parentId = getPara("parentId");
    		String orgId = getPara("orgId");
    		if(StrKit.isBlank(orgId)){//新增
    			String name = org.getName();
        		if(StrKit.notBlank(parentId)&&StrKit.notBlank(name)){
        			List<SysOrg> list = SysOrg.dao.find("select * from sys_org o where o.parent_id='"+parentId+"' and o.name='"+name+"' ");
        			if(list!=null&&list.size()>0){//如果有值，则校验失败
        				renderValidFail();
        			}else{
        				renderValidSuccess();
        			}
        		}else{
        			renderValidFail();
        		}
    		}else{//更新
    			String name = org.getName();//当前存入的名字
        		if(StrKit.notBlank(parentId)&&StrKit.notBlank(name)){
        			List<SysOrg> list = SysOrg.dao.find("select * from sys_org o where o.parent_id='"+parentId+"' and o.name='"+name+"' and o.id!='"+orgId+"' ");
        			if(list!=null&&list.size()>0){//如果有值，则校验失败
        				renderValidFail();
        			}else{
        				renderValidSuccess();
        			}
        		}else{
        			renderValidFail();
        		}
    		}
    		
    	}else{
    		renderValidSuccess();
    	}
    }

}
