/**
 * 
 */
package com.pointlion.mvc.admin.oa.workflow.model;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.jfinal.plugin.activerecord.Page;

import com.pointlion.mvc.admin.oa.workflow.WorkFlowService;
import com.pointlion.mvc.admin.oa.workflow.WorkFlowUtil;
import com.pointlion.mvc.common.base.BaseController;
import com.pointlion.mvc.common.model.ActReModel;
import com.pointlion.mvc.common.model.SysDct;
import com.pointlion.plugin.flowable.FlowablePlugin;

/**
 * @author Lion
 * @date 2017年2月16日 下午4:04:25
 * @qq 439635374
 */

public class ModelController extends BaseController{
	/***
	 * 获得列表页
	 */
	public void getCustomListPage(){

    	renderIframe("customList.html");
    }
	/***
	 * 获得列表页
	 */
	public void getAbsoluteListPage(){

    	renderIframe("absoluteList.html");
    }
	
	/***
	 * 获得启动自定义流程列表页，菜单中的
	 */
	public void getStartCustomFlowList(){

    	renderIframe("startCustomFlowList.html");
	}
	/***
	 * 获得新增页面
	 */
	public void getAddPage(){

		renderIframe("add.html");
	}
	/***
	 * 保存模型--新建新模型
	 */
	public void save(){
		String name = getPara("name");
		String key = getPara("key");
		try {
			if(WorkFlowUtil.ifHaveThisFlow(key)){//已经有固定流程了
				renderError("该流程已经存在，请输入其他流程Key值！");
			}else{
				WorkFlowService service = new WorkFlowService();
				service.createModel(FlowablePlugin.buildProcessEngine(), name, key);
				renderSuccess();
			}
		} catch (UnsupportedEncodingException e) {
			renderError();
			e.printStackTrace();
		}
	}
    /***
     * 查询分页数据
     */
    public void listCustomData(){
    	String curr = getPara("pageNumber");
    	String pageSize = getPara("pageSize");
    	Page<ActReModel> page = ActReModel.dao.getCustomModelPage(Integer.valueOf(curr),Integer.valueOf(pageSize));
    	renderPage(page.getList(),"" ,page.getTotalRow());
    }
    
    /***
     * 查询分页数据
     */
    public void listAbsoluteData(){
    	String curr = getPara("pageNumber");
    	String pageSize = getPara("pageSize");
    	Page<ActReModel> page = ActReModel.dao.getAbsoluteModelPage(Integer.valueOf(curr),Integer.valueOf(pageSize));
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
	
	
	/***
	 * 获取流程变量，候选人
	 */
	public void getSelectCandidateUserPage(){
		List<SysDct> list = SysDct.dao.getByType("WORKFLOW_VAR_CANDIDATE");
		setAttr("var",list);
		renderIframe("selectCandidateUserPage.html");
	}

}
