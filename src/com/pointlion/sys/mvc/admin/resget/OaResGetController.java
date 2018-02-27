package com.pointlion.sys.mvc.admin.resget;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.pointlion.sys.interceptor.MainPageTitleInterceptor;
import com.pointlion.sys.mvc.admin.workflow.WorkFlowService;
import com.pointlion.sys.mvc.common.base.BaseController;
import com.pointlion.sys.mvc.common.model.OaResGet;
import com.pointlion.sys.mvc.common.utils.Constants;
import com.pointlion.sys.mvc.common.utils.UuidUtil;
import com.pointlion.sys.plugin.shiro.ShiroKit;

@Before(MainPageTitleInterceptor.class)
public class OaResGetController extends BaseController {
	static final OaResGetService service = OaResGetService.me;
	static WorkFlowService wfservice = WorkFlowService.me;
	/***
	 * 列表页面
	 */
	public void getDraftListPage(){
		setBread("物品领用",this.getRequest().getServletPath(),"管理");
    	render("/WEB-INF/admin/resget/list.html");
    }
	/***
     * 获取分页数据
     **/
    public void listData(){
    	String curr = getPara("pageNumber");
    	String pageSize = getPara("pageSize");
    	Page<Record> page = service.getPage(Integer.valueOf(curr),Integer.valueOf(pageSize));
    	renderPage(page.getList(),"",page.getTotalRow());
    }
    /***
     * 保存
     */
    public void save(){
    	OaResGet o = getModel(OaResGet.class);
    	if(StrKit.notBlank(o.getId())){
    		o.setIfSubmit(Constants.IF_SUBMIT_NO);
    		o.setIfComplete(Constants.IF_COMPLETE_NO);
    		o.update();
    	}else{
    		o.setId(UuidUtil.getUUID());
    		o.setIfSubmit(Constants.IF_SUBMIT_NO);
    		o.setIfComplete(Constants.IF_COMPLETE_NO);
    		o.save();
    	}
    	renderSuccess();
    }
    /***
     * 获取编辑页面
     */
    public void getEditPage(){
    	setBread("物品领用",this.getRequest().getServletPath().substring(0,this.getRequest().getServletPath().lastIndexOf("/")+1)+"getDraftListPage","领用起草");
    	//添加和修改
    	String id = getPara("id");
    	if(StrKit.notBlank(id)){//修改
    		OaResGet o = service.getById(id);
    		setAttr("o", o);
    	}
    	render("/WEB-INF/admin/resget/edit.html");
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
     * 提交
     */
    public void startProcess(){
    	String id = getPara("id");
    	service.startProcess(id);
    	renderSuccess("启动成功");
    }
    /***
     * 撤回
     */
    public void callBack(){
    	String id = getPara("id");
    	try{
    		service.callBack(id);
    		renderSuccess("撤回成功");
    	}catch(Exception e){
    		e.printStackTrace();
    		renderError("撤回失败");
    	}
    }
    
    /***
     * 获取内部发文待办列表页面
     */
    public void getToDoPage(){
    	setBread("物品领用",this.getRequest().getServletPath(),"待办任务");
    	render("/WEB-INF/admin/resget/todolist.html");
    }
    
	/***
	 * 公文待办列表数据
	 */
    public void toDoListData(){
    	String curr = getPara("pageNumber");
    	String pageSize = getPara("pageSize");
    	Page<Record> page = wfservice.getToDoPageByKey(Integer.valueOf(curr),Integer.valueOf(pageSize),OaResGetConstants.BUSINESS_TABLENAME,OaResGetConstants.DEFKEY_RESGET, ShiroKit.getUsername());
    	renderPage(page.getList(),"",page.getTotalRow());
    }
    /***
     * 取到办理页面
     */
    public void getDoTaskPage(){
    	String parentPath = this.getRequest().getServletPath().substring(0,this.getRequest().getServletPath().lastIndexOf("/")+1); 
    	String taskid = getPara("taskid");
    	String id = getPara("id");
    	OaResGet reset = OaResGet.dao.findById(id);
    	Record task = wfservice.getTaskRecord(taskid);
    	setAttr("o", reset);
    	setAttr("task", task);
    	setBread("内部发文",parentPath+"getToDoPage","办理任务");
    	render("/WEB-INF/admin/resget/dotask.html");
    }
    /***
     * 办理任务
     */
    public void bumphSubmit(){
    	try{
    		String comment = getPara("comment");
//    		service.completeTask(getPara("pass"),comment,getPara("taskid"), getModel(OaBumph.class));
    		renderSuccess("提交成功");
    	}catch(Exception e){
    		e.printStackTrace();
    		renderError("提交失败");
    	}
    }
    
    /**************************************************************************/
	private String pageTitle = "物品领用";//模块页面标题
	private String breadHomeMethod = "getListPage";//面包屑首页方法
	
//	public Map<String,String> getPageTitleBread() {
//		Map<String,String> pageTitleBread = new HashMap<String,String>();
//		pageTitleBread.put("pageTitle", pageTitle);
//		pageTitleBread.put("breadHomeMethod", breadHomeMethod);
//		return pageTitleBread;
//	}
	
	
	public void setBread(String name,String url,String nowBread){
		Map<String,String> pageTitleBread = new HashMap<String,String>();
		pageTitleBread.put("pageTitle", name);
		pageTitleBread.put("url", url);
		pageTitleBread.put("nowBread", nowBread);
		this.setAttr("pageTitleBread", pageTitleBread);
	}
}