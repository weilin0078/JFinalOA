package com.pointlion.sys.mvc.admin.oa.apply.finance;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.pointlion.sys.interceptor.MainPageTitleInterceptor;
import com.pointlion.sys.mvc.admin.oa.common.BusinessUtil;
import com.pointlion.sys.mvc.admin.oa.common.FlowCCService;
import com.pointlion.sys.mvc.admin.oa.workflow.WorkFlowService;
import com.pointlion.sys.mvc.admin.oa.workflow.WorkFlowUtil;
import com.pointlion.sys.mvc.common.base.BaseController;
import com.pointlion.sys.mvc.common.model.OaApplyFinance;
import com.pointlion.sys.mvc.common.model.SysOrg;
import com.pointlion.sys.mvc.common.model.SysUser;
import com.pointlion.sys.mvc.common.utils.Constants;
import com.pointlion.sys.mvc.common.utils.DateUtil;
import com.pointlion.sys.mvc.common.utils.StringUtil;
import com.pointlion.sys.mvc.common.utils.UuidUtil;
import com.pointlion.sys.plugin.shiro.ShiroKit;



@Before(MainPageTitleInterceptor.class)
public class OaApplyFinanceController extends BaseController {
	public static final OaApplyFinanceService service = OaApplyFinanceService.me;
	public static WorkFlowService wfservice = WorkFlowService.me;
	public static FlowCCService ccService = FlowCCService.me;
	
	/***
	 * 列表页面
	 */
	public void getListPage(){
		setBread("财务类申请",this.getRequest().getServletPath(),"管理");
    	render("list.html");
    }
	/***
     * 获取分页数据
	 * @throws UnsupportedEncodingException 
     **/
    public void listData() throws UnsupportedEncodingException{
    	String curr = getPara("pageNumber");
    	String pageSize = getPara("pageSize");
    	String endTime = getPara("endTime","");
    	String startTime = getPara("startTime","");
    	String name = java.net.URLDecoder.decode(getPara("name",""),"UTF-8");
    	String c = getPara("c","");
    	if(StrKit.notBlank(c)){
    		if(c.equals("myCC")){
    			Page<Record> page = wfservice.getFlowCCPage(Integer.valueOf(curr),Integer.valueOf(pageSize), WorkFlowUtil.getDefkeyByModelName(OaApplyFinance.class.getSimpleName()), ShiroKit.getUserId(), service.getQuerySql("", name, startTime, endTime));
            	renderPage(page.getList(),"",page.getTotalRow());
    		}else if(c.equals("myTask")){
    			Page<Record> page = wfservice.getToDoPageByKey(Integer.valueOf(curr),Integer.valueOf(pageSize), WorkFlowUtil.getDefkeyByModelName(OaApplyFinance.class.getSimpleName()), ShiroKit.getUsername(), service.getQuerySql("", name, startTime, endTime));
            	renderPage(page.getList(),"",page.getTotalRow());
    		}else if(c.equals("myHaveDone")){
    			Page<Record> page = wfservice.getHavedonePage(Integer.valueOf(curr),Integer.valueOf(pageSize), WorkFlowUtil.getDefkeyByModelName(OaApplyFinance.class.getSimpleName()), ShiroKit.getUsername(), service.getQuerySql("", name, startTime, endTime));
            	renderPage(page.getList(),"",page.getTotalRow());
    		}else{
    			Page<Record> page = service.getPage(Integer.valueOf(curr),Integer.valueOf(pageSize),WorkFlowUtil.getDefkeyByModelName(OaApplyFinance.class.getSimpleName()),name,startTime,endTime);
            	renderPage(page.getList(),"",page.getTotalRow());	
    		}
    	}else{
    		Page<Record> page = service.getPage(Integer.valueOf(curr),Integer.valueOf(pageSize),"",name,startTime,endTime);
        	renderPage(page.getList(),"",page.getTotalRow());	
    	}
    }
    /***
     * 保存
     */
    public void save(){
    	OaApplyFinance o = getModel(OaApplyFinance.class);
    	String defKey = WorkFlowUtil.getDefkeyByModelName(OaApplyFinance.class.getSimpleName());
    	if(StrKit.notBlank(o.getId())){
    		o.update();
    		ccService.addFlowCC(this, o.getId(), defKey,OaApplyFinance.tableName);
    	}else{
    		String username = ShiroKit.getUsername();
    		String id = UuidUtil.getUUID();
    		o.setId(id);
    		o.setCreateTime(DateUtil.getTime());
    		Integer num = BusinessUtil.getMaxNum(o); 
    		o.setFinanceNumNum(num);//自增编号
    		o.setFinanceNum(BusinessUtil.getAddNum(num,username));
    		o.setFinanceNumYear(DateUtil.format(new Date(), "yyyyMM"));
    		o.save();
    		ccService.addFlowCC(this, id, defKey,OaApplyFinance.tableName);
    	}
    	renderSuccess();
    }
    /***
     * 获取编辑页面
     */
    public void getEditPage(){
    	String type = getPara("type");
    	setAttr("type", type);
    	setBread("财务类申请",this.getRequest().getServletPath().substring(0,this.getRequest().getServletPath().lastIndexOf("/")+1)+"getListPage","管理");
    	String id = getPara("id");
		String view = getPara("view");
		setAttr("view", view);
		//是否是查看详情页面
    	if(StrKit.notBlank(id)){//修改
    		OaApplyFinance o = service.getById(id);
    		setAttr("o", o);
    		if("detail".equals(view)){
    			if(StrKit.notBlank(o.getProcInsId())){
    				setAttr("procInsId", o.getProcInsId());
    				setAttr("defId", wfservice.getDefIdByInsId(o.getProcInsId()));
    			}
    		}
    		ccService.setAttrFlowCC(this, o.getId(), WorkFlowUtil.getDefkeyByModelName(o.getClass().getSimpleName()));
    	}else{
    		SysUser user = SysUser.dao.findById(ShiroKit.getUserId());
    		SysOrg org = SysOrg.dao.findById(user.getOrgid());
    		setAttr("user", user);
    		setAttr("org", org);
    	}
    	setAttr("formModelName",StringUtil.toLowerCaseFirstOne(OaApplyFinance.class.getSimpleName()));//模型名称
    	render("edit.html");
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
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws ClassNotFoundException 
     */
    public void startProcess() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
    	String id = getPara("id");
    	OaApplyFinance o = OaApplyFinance.dao.getById(id);
		o.setIfSubmit(Constants.IF_SUBMIT_YES);
    	String insId = wfservice.startProcess(id,o,null,null);
    	o.setProcInsId(insId);
    	o.update();
    	renderSuccess("提交成功");
    }
    /***
     * 撤回
     */
    public void callBack(){
    	String id = getPara("id");
    	try{
    		OaApplyFinance o = OaApplyFinance.dao.getById(id);
        	wfservice.callBack(o.getProcInsId());
        	o.setIfSubmit(Constants.IF_SUBMIT_NO);
        	o.setProcInsId("");
        	o.update();
    		renderSuccess("撤回成功");
    	}catch(Exception e){
    		e.printStackTrace();
    		renderError("撤回失败");
    	}
    }
    /**************************************************************************/
	public void setBread(String name,String url,String nowBread){
		Map<String,String> pageTitleBread = new HashMap<String,String>();
		pageTitleBread.put("pageTitle", name);
		pageTitleBread.put("url", url);
		pageTitleBread.put("nowBread", nowBread);
		this.setAttr("pageTitleBread", pageTitleBread);
	}
}