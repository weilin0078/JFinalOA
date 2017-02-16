/**
 * 
 */
package com.lion.sys.mvc.workflow.model;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.lion.sys.mvc.base.BaseController;

/**
 * @author Lion
 * @date 2017年2月16日 下午4:04:25
 * @qq 439635374
 */

public class ModelController extends BaseController{
	/***
	 * 获得列表页
	 */
	public void list(){
    	render("/WEB-INF/admin/workflow/model/list.html");
    }
    /***
     * 查询分页数据
     */
    public void listData(){
    	String curr = getPara("pageIndex");
    	String pageSize = getPara("pageSize");
    	String pid = getPara("pid");
    	if(StrKit.isBlank(pid)){
    		pid = "#root";
    	}
    	Page<ActReModel> page = ActReModel.dao.getModelPage(Integer.valueOf(curr),Integer.valueOf(pageSize));
    	renderPage(page.getList(),"" ,page.getTotalRow());
    }
}
