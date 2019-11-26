/**
 * 
 */
package com.pointlion.mvc.admin.oa.workflow;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import com.pointlion.mvc.common.base.BaseController;
import com.pointlion.mvc.common.model.ActReProcdef;

/**
 * @author Lion
 * @date 2017年2月16日 下午4:04:25
 * @qq 439635374
 */
/**
 * @author Administrator
 *
 */

public class WorkFlowController extends BaseController{
	private static final WorkFlowService service = WorkFlowService.me;
	/***
	 * 流程页面
	 */
	public void getListPage(){
    	renderIframe("list.html");
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
		String message = service.updateState(state, procDefId);
		renderSuccess(message);
	}
	
	
	/***
	 * 转化为模型
	 */
	public void convertToModel(){
		String defid = getPara("defid");
		try {
			service.convertToModel(defid);
			renderSuccess("转换模型成功");
		} catch (Exception e) {
			renderError("转化模型失败");
			e.printStackTrace();
		}
	}

	/***
	 * 读取资源，通过部署ID
	 * @throws Exception
	 */
	public void resourceRead() throws Exception {
		String procDefId = getPara("procDefId");
		String proInsId = getPara("proInsId");
		String resType = getPara("resType");
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
		service.deleteDeployment(deployid);
		renderSuccess("删除成功");
	}
	
	/***
	 * 删除流程实例
	 */
	public void deleteIns(){
		String insid = getPara("insid");
		service.deleteIns(insid);
		renderSuccess();
	}
	
	/***
	 * 获取流程流转历史
	 */
	public void getWorkFlowHis(){
		String insid = getPara("insid");
		setAttr("procInsId", insid);
		setAttr("hislist",service.getHisTaskList(insid));
		renderIframe("/WEB-INF/admin/oa/workflow/taskHisList.html");
	}

	/***
	 * 清空所有流程信息!!!!!!!!!!!!!!!!!!!!
	 * 测试环境使用，正式环境，请勿调用该接口
	 */
	public void deleteAllFlowInstance(){
		List<Record> list = Db.find("select * from act_hi_procinst");//查询所有的
		if(list!=null&&list.size()>0){
			for(Record r:list){
				String procid = r.getStr("PROC_INST_ID_");
				if(StrKit.notBlank(procid)){
					service.deleteIns(procid);
				}
			}
		}
		renderSuccess();
	}
	/***
	 * 清空所有流程信息!!!!!!!!!!!!!!!!!!!!
	 * 测试环境使用，正式环境，请勿调用该接口
	 */
	public void deleteFlowInstanceById(){
		String insid = getPara("insid");
		if(StrKit.notBlank(insid)){
			service.deleteIns(insid);
		}
		renderSuccess();
	}
	/***
	 * 删除所有版本的“运行的流程”
	 * 删除多余的版本的“模型”只留下最新的一个
	 * @throws XMLStreamException 
	 * @throws UnsupportedEncodingException 
	 */
	public void deleteDefAndRemainOneModel() throws UnsupportedEncodingException, XMLStreamException{
		service.deleteAllDeployment();
		service.deleteModelRemainMaxVersion();
		renderSuccess();
	}

}
