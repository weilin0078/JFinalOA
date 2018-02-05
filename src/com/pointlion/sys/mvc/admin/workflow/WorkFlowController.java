/**
 * 
 */
package com.pointlion.sys.mvc.admin.workflow;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.pointlion.sys.interceptor.MainPageTitleInterceptor;
import com.pointlion.sys.mvc.common.base.BaseController;
import com.pointlion.sys.mvc.common.model.ActReProcdef;

/**
 * @author Lion
 * @date 2017年2月16日 下午4:04:25
 * @qq 439635374
 */
/**
 * @author Administrator
 *
 */
@Before(MainPageTitleInterceptor.class)
public class WorkFlowController extends BaseController{
	private static final WorkFlowService me = WorkFlowService.me;
	/***
	 * 流程页面
	 */
	public void getListPage(){
    	render("/WEB-INF/admin/workflow/list.html");
    }
	
	/***
	 * 获取流程在线编辑器页面
	 */
	public void listData() {
		String curr = getPara("pageNumber");
    	String pageSize = getPara("pageSize");
    	Page<ActReProcdef> page = ActReProcdef.dao.getDefPage(Integer.valueOf(curr),Integer.valueOf(pageSize));
    	renderPage(page.getList(),"" ,page.getTotalRow());
	}
	
	
	/***
	 * 挂起/激活
	 */
	public void updateState(){
		String state = getPara("state");
		String procDefId = getPara("defid");
		WorkFlowService service = new WorkFlowService();
		String message = service.updateState(state, procDefId);
		renderSuccess(message);
	}
	
	
	/***
	 * 转化为模型
	 */
	public void convertToModel(){
		String defid = getPara("defid");
		WorkFlowService service = new WorkFlowService();
		try {
			service.convertToModel(defid);
		} catch (Exception e) {
			renderError("转化模型失败");
			e.printStackTrace();
		}
		renderSuccess("转换模型成功");
	}
	
	/**
	 * 读取资源，通过部署ID
	 * @param processDefinitionId  流程定义ID
	 * @param processInstanceId 流程实例ID
	 * @param resourceType 资源类型(xml|image)
	 * @param response
	 * @throws Exception
	 */
	public void resourceRead() throws Exception {
		String procDefId = getPara("procDefId");
		String proInsId = getPara("proInsId");
		String resType = getPara("resType");
		WorkFlowService service = new WorkFlowService();
		InputStream resourceAsStream = service.resourceRead(procDefId, proInsId, resType);
		byte[] b = new byte[1024];
		int len = -1;
		HttpServletResponse response = this.getResponse();
		while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
		renderNull();
	}
	
	/***
	 * 删除
	 */
	public void deleteDeployment(){
		String deployid = getPara("deployid");  
		WorkFlowService service = new WorkFlowService();
		service.deleteDeployment(deployid);
		renderSuccess("删除成功");
	}
	
	/***
	 * 删除流程实例
	 */
	public void deleteIns(){
		String insid = getPara("insid");
		me.deleteIns(insid);
		renderSuccess();
	}
	
    /**************************************************************************/
	private String pageTitle = "流程管理";//模块页面标题
	private String breadHomeMethod = "getListPage";//面包屑首页方法
	
	public Map<String,String> getPageTitleBread() {
		Map<String,String> pageTitleBread = new HashMap<String,String>();
		pageTitleBread.put("pageTitle", pageTitle);
		pageTitleBread.put("breadHomeMethod", breadHomeMethod);
		return pageTitleBread;
	}
}
