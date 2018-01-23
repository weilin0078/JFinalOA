/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.sys.mvc.admin.org;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.pointlion.sys.interceptor.MainPageTitleInterceptor;
import com.pointlion.sys.mvc.common.base.BaseController;
import com.pointlion.sys.mvc.common.dto.ZtreeNode;
import com.pointlion.sys.mvc.common.model.SysOrg;
import com.pointlion.sys.mvc.common.utils.UuidUtil;

/***
 * 菜单管理控制器
 */
@Before(MainPageTitleInterceptor.class)
public class OrgController extends BaseController {
	/***
	 * 获取列表页面
	 */
	public void getListPage(){
    	render("/WEB-INF/admin/org/list.html");
    }
    /***
     * 获取树
     */
    public void getOrgTree(){
    	List<SysOrg> menuList = SysOrg.dao.getChildrenAll("#root");
    	List<ZtreeNode> nodelist = SysOrg.dao.toZTreeNode(menuList,true,false);//数据库中的菜单
    	List<ZtreeNode> rootList = new ArrayList<ZtreeNode>();//页面展示的,带根节点
    	//声明根节点
    	ZtreeNode root = new ZtreeNode();
    	root.setId("#root");
    	root.setName("公司部门组织结构");
    	root.setChildren(nodelist);
    	root.setOpen(true);
    	rootList.add(root);
    	renderJson(rootList);
    }
    public void getOrgTreeOnlyLeaf(){
    	List<SysOrg> menuList = SysOrg.dao.getChildrenAll("#root");
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
    	//添加和修改
    	String id = getPara("id");
    	if(StrKit.notBlank(id)){
    		SysOrg o = SysOrg.dao.getById(id);
    		SysOrg parent = SysOrg.dao.getById(o.getParentId());
    		setAttr("o", o);
    		setAttr("p", parent);
    	}
    	//添加子模块
    	String parentid = getPara("parentid");
    	if(StrKit.notBlank(parentid)){
    		SysOrg parent = SysOrg.dao.getById(parentid);
    		setAttr("p", parent);
    	}
    	render("/WEB-INF/admin/org/edit.html");
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
    	render("/WEB-INF/admin/common/pointLion/selectOneOrg/selectOneOrg.html");
    }
    public void getSelectManyOrgPage(){
    	render("/WEB-INF/admin/common/pointLion/selectManyOrg/selectManyOrg.html");
    }
    
    /***
     * 删除
     * @throws Exception
     */
    public void delete() throws Exception{
		String ids = getPara("ids");
    	String idarr[] = ids.split(",");
    	for(String id : idarr){
    		List<SysOrg> list = SysOrg.dao.getChildrenByPid(id);
    		if(list.size()>0){
    			renderError("有子菜单,不允许删除!");
    			return;
    		}
    	}
    	//执行删除
    	SysOrg.dao.deleteByIds(ids);
    	renderSuccess("删除成功!");
    }
    
    /**************************************************************************/
	private String pageTitle = "组织机构";//模块页面标题
	private String breadHomeMethod = "getListPage";//面包屑首页方法
	
	public Map<String,String> getPageTitleBread() {
		Map<String,String> pageTitleBread = new HashMap<String,String>();
		pageTitleBread.put("pageTitle", pageTitle);
		pageTitleBread.put("breadHomeMethod", breadHomeMethod);
		return pageTitleBread;
	}
}
