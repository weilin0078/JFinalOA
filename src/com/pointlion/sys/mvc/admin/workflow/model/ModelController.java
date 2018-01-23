/**
 * 
 */
package com.pointlion.sys.mvc.admin.workflow.model;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.pointlion.sys.interceptor.MainPageTitleInterceptor;
import com.pointlion.sys.mvc.admin.workflow.WorkFlowService;
import com.pointlion.sys.mvc.common.base.BaseController;
import com.pointlion.sys.mvc.common.model.ActReModel;
import com.pointlion.sys.plugin.activiti.ActivitiPlugin;

/**
 * @author Lion
 * @date 2017年2月16日 下午4:04:25
 * @qq 439635374
 */
@Before(MainPageTitleInterceptor.class)
public class ModelController extends BaseController{
	/***
	 * 获得列表页
	 */
	public void getListPage(){
    	render("/WEB-INF/admin/workflow/model/list.html");
    }
	/***
	 * 获得新增页面
	 */
	public void getAddPage(){
		render("/WEB-INF/admin/workflow/model/add.html");
	}
	/***
	 * 保存模型--新建新模型
	 */
	public void save(){
		String name = getPara("name");
		String key = getPara("key");
		try {
			WorkFlowService service = new WorkFlowService();
			service.createModel(ActivitiPlugin.buildProcessEngine(), name, key);
			renderSuccess();
		} catch (UnsupportedEncodingException e) {
			renderError();
			e.printStackTrace();
		}
	}
    /***
     * 查询分页数据
     */
    public void listData(){
    	String curr = getPara("pageNumber");
    	String pageSize = getPara("pageSize");
    	Page<ActReModel> page = ActReModel.dao.getModelPage(Integer.valueOf(curr),Integer.valueOf(pageSize));
    	renderPage(page.getList(),"" ,page.getTotalRow());
    }
    
    /***
     * 模型部署
     */
	public void deploy() {
		String id = getPara("id");
		WorkFlowService service = new WorkFlowService();
		String message = service.deploy(id);
		renderSuccess(message);
	}
    /***
     * 删除模型
     */
	public void delete() {
		String id = getPara("id");
		WorkFlowService service = new WorkFlowService();
		service.deleteModel(id);
		renderSuccess();
	}
	
	
	
	
	
	
	
	
	
	
	
    /**************************************************************************/
	private String pageTitle = "模型管理";//模块页面标题
	private String breadHomeMethod = "getListPage";//面包屑首页方法
	
	public Map<String,String> getPageTitleBread() {
		Map<String,String> pageTitleBread = new HashMap<String,String>();
		pageTitleBread.put("pageTitle", pageTitle);
		pageTitleBread.put("breadHomeMethod", breadHomeMethod);
		return pageTitleBread;
	}
}
