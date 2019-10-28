package com.pointlion.mvc.admin.sys.dct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.pointlion.annotation.ServerLog;
import com.pointlion.enums.BusinessType;

import com.pointlion.mvc.admin.oa.workflow.WorkFlowService;
import com.pointlion.mvc.common.base.BaseController;
import com.pointlion.mvc.common.dto.ZtreeNode;
import com.pointlion.mvc.common.model.SysDct;
import com.pointlion.mvc.common.model.SysDctGroup;
import com.pointlion.mvc.common.utils.ContextUtil;
import com.pointlion.mvc.common.utils.DateUtil;
import com.pointlion.mvc.common.utils.StringUtil;
import com.pointlion.mvc.common.utils.UuidUtil;




public class SysDctController extends BaseController {
	public static final SysDctService service = SysDctService.me;
	public static WorkFlowService wfservice = WorkFlowService.me;
	/***
	 * 列表页面
	 */
	public void getListPage(){

    	renderIframe("list.html");
    }
	/***
     * 获取分页数据
     **/
	@ServerLog(module = "字典管理",businessType = BusinessType.QUERY)
    public void listData(){
    	String curr = getPara("pageNumber");
    	String pageSize = getPara("pageSize");
    	String groupId = getPara("groupId");
    	String groupKey = getPara("groupKey");
    	if(StrKit.notBlank(groupId)){//用id去查
    		Page<Record> page = service.getPage(Integer.valueOf(curr),Integer.valueOf(pageSize),groupId);
			renderPage(page.getList(),"",page.getTotalRow());
    	}else if(StrKit.notBlank(groupKey)){//用key去查
    		SysDctGroup group = SysDctGroup.dao.getByKey(groupKey);
    		if(group!=null){
    			groupId = group.getId();
    			Page<Record> page = service.getPage(Integer.valueOf(curr),Integer.valueOf(pageSize),groupId);
    			renderPage(page.getList(),"",page.getTotalRow());
    		}else{
    			renderPage(null,"",0);
    		}
    	}else{
    		renderPage(null,"",0);
    	}
    }
    /***
     * 保存字典
     */
    public void save(){
    	SysDct o = getModel(SysDct.class);
    	if(StrKit.notBlank(o.getId())){
    		o.update();
    	}else{
    		o.setId(UuidUtil.getUUID());
    		o.setCreateTime(DateUtil.getCurrentTime());
    		o.save();
    	}
    	renderSuccess();
    }
    /***
     * 保存分组
     */
    public void saveGroup(){
    	SysDctGroup o = getModel(SysDctGroup.class);
    	if(StrKit.notBlank(o.getId())){
    		o.update();
    	}else{
    		o.setId(UuidUtil.getUUID());
    		o.save();
    	}
    	renderSuccess();
    }
    /***
     * 字典
     * 获取编辑页面
     */
    public void getEditPage(){

    	String id = getPara("id");
    	String view = getPara("view");
		setAttr("view", view);
    	if(StrKit.notBlank(id)){//修改
    		SysDct o = SysDct.dao.getById(id);
    		setAttr("o", o);
    		SysDctGroup group = SysDctGroup.dao.getById(o.getGroupId());
    		if(group!=null){
    			setAttr("group",group);
    		}
    	}else{
    		String groupId = getPara("groupId");
    		SysDctGroup group = SysDctGroup.dao.getById(groupId);
    		if(group==null){
    			group = new SysDctGroup();
    			group.setId("");
    			group.setName("");
    		}
    		setAttr("group",group);
    	}
    	setAttr("formModelName",StringUtil.toLowerCaseFirstOne(SysDct.class.getSimpleName()));//模型名称
    	renderIframe("edit.html");
    }
    /***
     * 分组
     * 获取编辑页面
     */
    public void getEditGroupPage(){

    	String id = getPara("id");
    	String view = getPara("view");
		setAttr("view", view);
    	if(StrKit.notBlank(id)){//修改
    		SysDctGroup o = SysDctGroup.dao.getById(id);
    		setAttr("o", o);
    		SysDctGroup group = SysDctGroup.dao.getById(o.getParentId());
    		if(group!=null){
    			setAttr("group",group);
    		}
    	}else{
    		String groupId = getPara("groupId");
    		SysDctGroup group = SysDctGroup.dao.getById(groupId);
    		if(group==null){
    			group = new SysDctGroup();
    			group.setId("");
    			group.setName("");
    		}
    		setAttr("group",group);
    	}
    	setAttr("formModelName",StringUtil.toLowerCaseFirstOne(SysDctGroup.class.getSimpleName()));//模型名称
    	renderIframe("editGroup.html");
    }
    /***
     * 打开一个选择分组的弹层
     */
    public void getSelectOneDctGroupPage(){
    	renderIframe("selectOneDctGroup.html");
    }
    /***
     * 删除
     * @throws Exception
     */
    public void delete() throws Exception{
		String ids = getPara("ids");
    	//执行删除
		service.deleteByIds(ids);
    	renderSuccess("删除成功!");
    }
    
    /***
     * 查询树
     */
    public void getDctGroupTree(){
		List<SysDctGroup> groupList = service.getChildrenAllTree("#root");
    	List<ZtreeNode> nodelist = service.toZTreeNode(groupList,false,false);//数据库中的菜单
    	List<ZtreeNode> rootList = new ArrayList<ZtreeNode>();//页面展示的,带根节点
    	SysDctGroup org = SysDctGroup.dao.getById("#root");
    	//声明根节点
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
    
    /***
     * 删除分组
     */
    public void deleteGroup(){
    	String groupId = getPara("groupId");
    	List<SysDct> list = SysDct.dao.getDctByGroupId(groupId);
    	List<SysDctGroup> groupList = SysDct.dao.getGroupListByParentId(groupId);
    	if(groupList!=null&&groupList.size()>0){
    		renderError("该分组下有子分组，清先清除子分组，再进行删除分组操作！");
    	}else if(list!=null&&list.size()>0){
    		renderError("该分组下有字典数据，清先清空字典数据，再进行删除分组操作！");
    	}else{
    		SysDctGroup group = SysDctGroup.dao.getById(groupId);
    		group.delete();
    		renderSuccess();
    	}
    }
    
    /****
     * 打开选择项目弹窗
     */
    public void openSelectDctDialog(){
    	String groupKey = getPara("groupKey");
    	setAttr("groupKey", groupKey);
    	renderIframe("selectOneDct.html");
    }

}