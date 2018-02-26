/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.sys.mvc.admin.bumph;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.pointlion.sys.mvc.admin.workflow.WorkFlowService;
import com.pointlion.sys.mvc.common.base.BaseController;
import com.pointlion.sys.mvc.common.model.OaBumph;
import com.pointlion.sys.mvc.common.model.OaBumphOrg;
import com.pointlion.sys.mvc.common.model.SysOrg;
import com.pointlion.sys.mvc.common.model.SysUser;
import com.pointlion.sys.mvc.common.utils.DateUtil;
import com.pointlion.sys.mvc.common.utils.StringUtil;
import com.pointlion.sys.plugin.shiro.ShiroKit;

/***
 * 用户管理控制器
 * @author Administrator
 *
 */
public class BumphController extends BaseController {
	static BumphService service = BumphService.me;
	static WorkFlowService wfservice = WorkFlowService.me;
	
	/***************************内部发文---开始***********************/
	/***
	 * 获取公文起草页面
	 */
	public void getDraftListPage(){
		setBread("内部发文",this.getRequest().getServletPath(),"管理");
		render("/WEB-INF/admin/bumph/draft/list.html");
    }
	/***
	 * 公文起草列表数据
	 */
    public void draftListData(){
    	String curr = getPara("pageNumber");
    	String pageSize = getPara("pageSize");
    	Page<OaBumph> page = OaBumph.dao.getPage(Integer.valueOf(curr),Integer.valueOf(pageSize));
    	renderPage(page.getList(),"" ,page.getTotalRow());
    }
    
	/***
	 * 编辑公文起草页面
	 */
	public void getDraftEditPage(){
		String parentPath = this.getRequest().getServletPath().substring(0,this.getRequest().getServletPath().lastIndexOf("/")+1); 
		setBread("公文起草",parentPath+"getDraftListPage","管理");
		//是否是查看详情页面
		String view = getPara("view");
		if("detail".equals(view)){
			setAttr("view", view);
		}
		//添加和修改
    	String id = getPara("id");
    	if(StrKit.notBlank(id)){//修改
    		OaBumph o = OaBumph.dao.findById(id);
    		setAttr("o", o);
    		//获取主送和抄送单位
    		setAttrFirstSecond(o);
    	}else{//新增
    		OaBumph o = new OaBumph();
    		String userId = ShiroKit.getUserId();//用户主键
    		SysUser user = SysUser.dao.getById(userId);//用户对象
    		SysOrg org = SysOrg.dao.getById(user.getOrgid());//单位对象
    		o.setDocNumYear(DateUtil.getYear());
    		o.setSenderId(userId);
    		o.setSenderName(user.getName());
    		o.setSenderOrgid(org.getId());
    		o.setSenderOrgname(org.getName());
    		setAttr("o",o);
    	}
    	render("/WEB-INF/admin/bumph/draft/edit.html");
	}
	/***
	 * 导出
	 */
	public void export(){
		String id = getPara("id");
		File file = null;
		try {
			file = service.bumphExport(id, this.getRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderFile(file);
	}
	/***
	 * 获取主送和抄送单位
	 */
	public void setAttrFirstSecond(OaBumph o){
		//获取所有主送单位
		List<OaBumphOrg> flist = OaBumphOrg.dao.getList(o.getId(),BumphConstants.TYPE_ZHUSONG);
		List<String> f = new ArrayList<String>();
		List<String> fn = new ArrayList<String>();
		for(OaBumphOrg fo : flist){
			f.add(fo.getOrgid());
			fn.add(fo.getOrgname());
		}
		setAttr("firstOrgId", StringUtil.join(f,","));
		setAttr("firstOrgName", StringUtil.join(fn,","));
		//获取所有抄送单位
		List<OaBumphOrg> slist = OaBumphOrg.dao.getList(o.getId(),BumphConstants.TYPE_CHAOSONG);
		List<String> s = new ArrayList<String>();
		List<String> sn = new ArrayList<String>();
		for(OaBumphOrg so : slist){
			s.add(so.getOrgid());
			sn.add(so.getOrgname());
		}
		setAttr("secondOrgId", StringUtil.join(s,","));
		setAttr("secondOrgName", StringUtil.join(sn,","));
	}
	
    
    /***
     * 公文起草保存
     */
    public void draftSave(){
    	OaBumph o = getModel(OaBumph.class);
    	String firstOrgId = getPara("firstOrgId");//主送单位
    	String secondOrgId = getPara("secondOrgId");//抄送单位
    	service.save(o, firstOrgId, secondOrgId);
    	renderSuccess();
    }
    /***
     * 删除公文
     */
    public void delete(){
    	String id = getPara("ids");
    	service.delete(id);
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
    	setBread("内部发文",this.getRequest().getServletPath(),"待办任务");
    	render("/WEB-INF/admin/bumph/audit/list.html");
    }
    
	/***
	 * 公文待办列表数据
	 */
    public void bumphToDoListData(){
    	String curr = getPara("pageNumber");
    	String pageSize = getPara("pageSize");
    	Page<Record> page = wfservice.getToDoPageByKey(Integer.valueOf(curr),Integer.valueOf(pageSize),BumphConstants.BUSINESS_TABLENAME,BumphConstants.DEFKEY_BUMPH, ShiroKit.getUsername());
    	renderPage(page.getList(),"",page.getTotalRow());
    }
    /***
     * 取到办理页面
     */
    public void getDoTaskPage(){
    	String parentPath = this.getRequest().getServletPath().substring(0,this.getRequest().getServletPath().lastIndexOf("/")+1); 
    	String taskid = getPara("taskid");
    	String id = getPara("id");
    	OaBumph bumph = OaBumph.dao.findById(id);
    	Record task = wfservice.getTaskRecord(taskid);
		//获取主送和抄送单位
    	setAttrFirstSecond(bumph);
    	setAttr("o", bumph);
    	setAttr("task", task);
    	setBread("内部发文",parentPath+"getToDoPage","办理任务");
    	render("/WEB-INF/admin/bumph/audit/dotask.html");
    }
    /***
     * 办理任务
     */
    public void bumphSubmit(){
    	try{
    		String comment = getPara("comment");
    		service.completeTask(getPara("pass"),comment,getPara("taskid"), getModel(OaBumph.class));
    		renderSuccess("提交成功");
    	}catch(Exception e){
    		e.printStackTrace();
    		renderError("提交失败");
    	}
    }
    
    
    /***
     * 获取历史数据内部发文
     */
    public void getHistoryBumphPage(){
    	setBread("内部发文",this.getRequest().getServletPath(),"收文历史");
    	render("/WEB-INF/admin/bumph/history/list.html");
    }
    
	/***
	 * 获取所有收文历史数据-----获取所有经办数据
	 */
    public void partListData(){
    	String curr = getPara("pageNumber");
    	String pageSize = getPara("pageSize");
    	Page<Record> page = service.getPartList(Integer.valueOf(curr),Integer.valueOf(pageSize), null, ShiroKit.getUsername());
    	renderPage(page.getList(),"" ,page.getTotalRow());
    }
    /************************内部发文---结束*************************************************/
    
    /**************************************************************************/
	public void setBread(String name,String url,String nowBread){
		Map<String,String> pageTitleBread = new HashMap<String,String>();
		pageTitleBread.put("pageTitle", name);
		pageTitleBread.put("url", url);
		pageTitleBread.put("nowBread", nowBread);
		this.setAttr("pageTitleBread", pageTitleBread);
	}
}
