/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.lion.sys.mvc.operate;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.lion.sys.mvc.base.BaseController;
import com.lion.sys.mvc.module.SysModule;
import com.lion.sys.tool.UuidUtil;

/***
 * 菜单管理控制器
 */
public class OperateController extends BaseController {
	/***
	 * 获取列表页面
	 */
    public void getListPage(){
    	render("/WEB-INF/admin/operate/list.html");
    }
    /***
     * 获取编辑页面
     */
    public void getEditPage(){
    	//添加和修改
    	String id = getPara("id");
    	if(StringUtils.isNotBlank(id)){
    		SysOperate oper = SysOperate.dao.getById(id);
    		SysModule module = SysModule.dao.getById(oper.getModuleId());
    		setAttr("oper", oper);
    		setAttr("module",module);
    	}
    	render("/WEB-INF/admin/operate/edit.html");
    }
    /***
     * 获取选择父级页面
     */
    public void getSelectPage(){
    	render("/WEB-INF/admin/operate/selectModule.html");
    }

    /***
     * 获取分页数据
     */
    public void listData(){
    	String curr = getPara("pageIndex");
    	String pageSize = getPara("pageSize");
    	String mid = getPara("mid");
    	if("#root".equals(mid)){
    		mid = "";
    	}
    	Page<Record> page = SysOperate.dao.getPage(Integer.valueOf(curr),Integer.valueOf(pageSize),mid);
    	renderPage(page.getList(),"" ,page.getTotalRow());
    }
    /***
     * 保存
     */
    public void save(){
    	SysOperate module = getModel(SysOperate.class);
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
    		SysOperate oper = SysOperate.dao.getById(id);
    		if(oper!=null){
    			oper.delete();
    		}
    	}
    	renderSuccess();
    }
}
