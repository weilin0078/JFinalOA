package com.pointlion.mvc.admin.sys.schjob;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import com.pointlion.mvc.common.base.BaseController;
import com.pointlion.mvc.common.model.SchJob;
import com.pointlion.mvc.common.model.SchJobExecute;
import com.pointlion.mvc.common.utils.StringUtil;
import com.pointlion.mvc.common.utils.UuidUtil;
import com.pointlion.plugin.quartz.QuartzPlugin;

import java.util.Date;


public class SchJobController extends BaseController {
	public static final SchJobService service = SchJobService.me;
	/***
	 * 列表页面
	 */
	public void getExeListPage(){
    	renderIframe("exeList.html");
    }
	/***
     * 获取分页数据
     **/
    public void exeListData(){
    	String curr = getPara("pageNumber");
    	String pageSize = getPara("pageSize");
    	Page<Record> page = service.getExePage(Integer.valueOf(curr),Integer.valueOf(pageSize));
    	renderPage(page.getList(),"",page.getTotalRow());
    }
    /***
     * 保存
     */
    public void saveExecute(){
    	SchJobExecute o = getModel(SchJobExecute.class);
    	if(StrKit.notBlank(o.getID())){
    		o.update();
    	}else{
    		o.setID(UuidUtil.getUUID());
    		o.setCreateTime(new Date());
    		o.save();
			QuartzPlugin.me.reStart(false);//重启QuartZ插件
    	}
    	renderSuccess();
    }
    /***
     * 获取编辑页面
     */
    public void getExeEditPage(){
    	String id = getPara("id");
    	String view = getPara("view");
		setAttr("view", view);
    	if(StrKit.notBlank(id)){//修改
		    SchJobExecute o = service.getExeById(id);
		    SchJob job = SchJob.dao.getById(o.getJobId());
		    o.put("JOB_NAME",job!=null?job.getJobName():"");
		    setAttr("o", o);
	    }else{
		    SchJobExecute o = new SchJobExecute();
		    o.setIsAutoRun("1");
		    o.setIsEnable("1");
		    setAttr("o", o);
    	}
    	setAttr("formModelName",StringUtil.toLowerCaseFirstOne(SchJobExecute.class.getSimpleName()));//模型名称
	    renderIframe("exeEdit.html");
    }
    /***
     * 删除
     * @throws Exception
     */
    public void deleteExe() throws Exception{
		String ids = getPara("ids");
    	//执行删除
		service.deleteExeByIds(ids);
    	renderSuccess("删除成功!");
    }
    
    
	/*****************任务字典管理功能***********************/
	/***
	 * 任务字典页面
	 */
    public void getJobListPage(){
        renderIframe("jobList.html");
    }
	
	
	/***
	 * 选择任务字段页面
	 */
	public void getSelectJobDctPage(){
		render("selectJobList.html");
	}
	/***
	 * 获取分页数据
	 **/
	public void jobListData(){
		String curr = getPara("pageNumber");
		String pageSize = getPara("pageSize");
		Page<Record> page = service.getJobPage(Integer.valueOf(curr),Integer.valueOf(pageSize));
		renderPage(page.getList(),"",page.getTotalRow());
	}
	/***
	 * 获取任务编辑页面
	 */
	public void getJobEditPage(){
		String id = getPara("id");
		String view = getPara("view");
		setAttr("view", view);
		if(StrKit.notBlank(id)){//修改
			SchJob o = service.getJobById(id);
			setAttr("o", o);
		}else{
		
		}
		setAttr("formModelName",StringUtil.toLowerCaseFirstOne(SchJob.class.getSimpleName()));//模型名称
		renderIframe("jobEdit.html");
	}
	
	/***
	 * 保存
	 */
	public void saveJob(){
		SchJob o = getModel(SchJob.class);
		if(StrKit.notBlank(o.getID())){
			o.update();
		}else{
			o.setID(UuidUtil.getUUID());
			o.setCreateTime(new Date());
			o.save();
		}
		renderSuccess();
	}
	
	/***
	 * 删除
	 * @throws Exception
	 */
	public void deleteJob() throws Exception{
		String ids = getPara("ids");
		//执行删除
		service.deleteJobByIds(ids);
		renderSuccess("删除成功!");
	}

}