package com.pointlion.mvc.admin.oa.workflow.flowtask;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.pointlion.mvc.admin.oa.common.OAConstants;
import com.pointlion.mvc.admin.oa.notice.NoticeService;
import com.pointlion.mvc.admin.oa.workflow.WorkFlowService;
import com.pointlion.mvc.admin.oa.workflow.WorkFlowUtil;
import com.pointlion.mvc.common.base.BaseController;
import com.pointlion.mvc.common.model.OaApplyCustom;
import com.pointlion.mvc.common.model.SysUser;
import com.pointlion.mvc.common.model.VTasklist;
import com.pointlion.mvc.common.utils.StringUtil;
import com.pointlion.plugin.shiro.ShiroKit;
import com.pointlion.plugin.shiro.ext.SimpleUser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlowTaskController extends BaseController{
	static final FlowTaskService service = FlowTaskService.me;
	static WorkFlowService wfservice = WorkFlowService.me;
	static NoticeService noticeService = new NoticeService();
	/***
	 * 获取待办页面
	 */
	public void getToDoPage(){
		SimpleUser user = ShiroKit.getLoginUser();
    	String username = user.getUsername();
    	setAttrToDoList(username);//获取待办
		renderIframe("todoList.html");
	}
	/***
	 * 获取已办页面
	 */
	public void getHaveDonePage(){
		SimpleUser user = ShiroKit.getLoginUser();
		String username = user.getUsername();
    	setAttrHavedoneList(username);//获取已办
		setAttr("NoticeList",noticeService.getMyNotice(user.getId()));//获取首页通知公告
		renderIframe("havedoneList.html");
	}
	/****
	 * 获取申请办理任务页面
	 * 
	 * defkey~！！！！！！！！！！！待整理  
	 * 
	 * 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws ClassNotFoundException 
	 */
	public void getDoTaskPage() throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		String defKey = getPara("defkey");
		String defName = WorkFlowUtil.getDefNameByDefKey(defKey);

		String taskid = getPara("taskid");
		setAttr("title", defName);
		
		if(StrKit.notBlank(taskid)){
			//组织都需要的参数
			VTasklist task = VTasklist.dao.getTaskRecord(taskid);
			try {
				if("ReEdit".equals(task.getTASKDEFKEY())){//判断是否要重新编辑
					String className = WorkFlowUtil.getClassFullNameByDefKey(task.getDEFKEY());
					Class<?> userClass = Class.forName(className);
					setAttr("formModelName",StringUtil.toLowerCaseFirstOne(userClass.getSimpleName()));//模型名称
					setAttr("view", "reEdit");
				}else{
					setAttr("view", "detail");
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			setAttr("task", task);
			Record o = new Record();//单据对象
			if(StrKit.notBlank(WorkFlowUtil.getTablenameByDefkey(defKey))){//如果属于固定流程
				o = service.getTaskObject(taskid,defKey);
			}else{
				o = service.getCustomTaskObject(taskid,defKey);
			}
			if(o!=null){
    			setAttr("o", o);
    			String procInsId = o.getStr("proc_ins_id");
    			if(StrKit.notBlank(procInsId)){
    				setAttr("procInsId", procInsId);
    				setAttr("defId", wfservice.getDefIdByInsId(procInsId));
    			}
    			String userid = o.getStr("userid");
    			if(StrKit.notBlank(userid)){
    				SysUser user = SysUser.dao.findById(userid);
    				setAttr("user", user);
    			}
    		}
			setPageUrl(defKey,o,task);//设置不同业务需要渲染的页面，以及所需要的属性
			renderIframe("doTask.html");
    	}
	}

	/***
	 * 设置不同业务需要渲染的页面，以及所需要的属性（task可能为null，注意空指针）
	 * @param defKey
	 * @param o
	 * @param task
	 */
	public void setPageUrl(String defKey,Record o,VTasklist task){
		if(StrKit.notBlank(WorkFlowUtil.getTablenameByDefkey(defKey))){//如果属于固定流程
			setAttr("pageUrl", "/WEB-INF/admin/oa/apply/"+defKey.toLowerCase()+"/editForm.html");
		}else{//自定义流程
			setAttr("defName", wfservice.getDefNameByDefKey(defKey));
			setAttr("pageUrl", "/WEB-INF/admin/oa/apply/custom/editForm.html");
		}
	}
	/***
	 * 获取已办列表数据
	 */
	public void getHaveDoneTaskDataList(){
		String defkey = getPara("defkey");
		String username = ShiroKit.getUsername();
		String curr = getPara("pageNumber");
    	String pageSize = getPara("pageSize");
    	Page<Record> page = wfservice.getHavedonePage(Integer.valueOf(curr),Integer.valueOf(pageSize),defkey, username,null);
		renderPage(page.getList(),"",page.getTotalRow());
	}
	
	/***
	 * 获取已办打开已办任务详情页面
	 */
	public void openHavedoneBusinessPage(){
		String defKey = getPara("defkey");
		String defName = WorkFlowUtil.getDefNameByDefKey(defKey);

		setAttr("title", defName);
		setAttr("view", "detail");
		String id = getPara("id");
		if(StrKit.notBlank(id)){
    		Record o = service.getBusinessObject(id,defKey);
    		if(o!=null){
    			setAttr("o", o);
    			String procInsId = o.getStr("proc_ins_id");
    			if(StrKit.notBlank(procInsId)){
    				setAttr("procInsId", procInsId);
    				setAttr("defId", wfservice.getDefIdByInsId(procInsId));
    			}
    			String userid = o.getStr("userid");//申请人
    			if(StrKit.notBlank(userid)){
    				SysUser user = SysUser.dao.findById(userid);
    				setAttr("user", user);
    			}
    		}
    		setPageUrl(defKey,o,new VTasklist());//设置渲染的页面
    	}
		renderIframe("havedoneBusinessPage.html");
	}
	
	/***
	 * 提交任务
	 */
	@SuppressWarnings("rawtypes")
	public void submitTask(){
		try{
			//正常办理
			String taskid = getPara("taskId");
			VTasklist task = VTasklist.dao.getTaskRecord(taskid);
			Map<String,Object> var = new HashMap<String,Object>();
			if(task!=null){
				if("ReEdit".equals(task.getTASKDEFKEY())){//如果是重新编辑
					String className = WorkFlowUtil.getClassFullNameByDefKey(task.getDEFKEY());
					Class<?> userClass = Class.forName(className);
					Model o = (Model) getModel(userClass);
					o.update();
					var = wfservice.getVar(o, var, o.getStr("id"), ShiroKit.getUsername(), task.getDEFKEY());
				}
			}
			String comment = getPara("comment");
			var.put("pass", getPara("pass"));

			//是否需要其他人会签审批
			String ifNeedAddAssignee = getPara(OAConstants.WORKFLOW_OPEN_ADD_ASSIGNEE,"0");
			var.put(OAConstants.WORKFLOW_OPEN_ADD_ASSIGNEE, ifNeedAddAssignee);//是否需要添加其他人办理。会签。
			if("1".equals(ifNeedAddAssignee)){
				String usernames = getPara("addOtherPersonAuditUsernames","");
				List jointlyUsers = Arrays.asList(usernames.split(","));
				var.put(OAConstants.WORKFLOW_VAR_JOINTLY_USERLIST, jointlyUsers);
			}
			wfservice.completeTask(taskid,ShiroKit.getUsername(), comment, var);
			renderSuccess();
		}catch(Exception e){
			e.printStackTrace();
			renderError();
		}
		
	}


	
	/**
	 * 
	 */
	 /***
     * 设定，待办，数据
     * @param username
     */
    private void setAttrToDoList(String username){
    	Map<String,List<Record>> todoMap = new HashMap<String,List<Record>>();
    	int todoListCount = 0; 
    	List<VTasklist> defkList = VTasklist.dao.find("select DEFKEY from v_tasklist t where (t.ASSIGNEE='"+username+"' or t.CANDIDATE='"+username+"') GROUP BY t.DEFKEY");
    	for(VTasklist t:defkList){
    		String defkey = t.getDEFKEY();
    		String tablename = WorkFlowUtil.getTablenameByDefkey(defkey);
    		if(StrKit.notBlank(tablename)){//如果属于固定流程
    			List<Record> todolist = wfservice.getToDoListByKey(tablename,defkey,username);
    			if(todolist!=null&&todolist.size()>0){
    				todoListCount = todoListCount + todolist.size();
    				todoMap.put(defkey, todolist);
    			}
    		}else{//自定义流程
    			List<Record> todolist = wfservice.getToDoListByKey(OaApplyCustom.tableName,defkey,username);
    			if(todolist!=null&&todolist.size()>0){
    				todoListCount = todoListCount + todolist.size();
    				todoMap.put(defkey, todolist);
    			}
    		}
    	}
    	setAttr("todoListCount", todoListCount);
    	setAttr("todoMap", todoMap);
    	
    	
//    	List<Record> todoList = workflowService.getToDoListByUsername(username);
//    	setAttr("todoList", todoList);
    	
    	
//    	//内部发文待办
//    	List<Record> bumphList = workflowService.getToDoListByKey(OaBumph.tableName,OAConstants.DEFKEY_BUMPH,username);
//    	setAttr("bumphList", bumphList);
//    	//物品领用待办
//    	List<Record> resGetList = workflowService.getToDoListByKey(OaApplyResGet.tableName,OAConstants.DEFKEY_APPLY_RESGET,username);
//    	setAttr("resGetList", resGetList);
//    	//名片印刷申请
//    	List<Record> businessCardList = workflowService.getToDoListByKey(OaApplyBusinessCard.tableName,OAConstants.DEFKEY_APPLY_BUSINESSCARD,username);
//    	setAttr("businessCardList", businessCardList);
//    	//花费申请
//    	List<Record> costList = workflowService.getToDoListByKey(OaApplyCost.tableName,OAConstants.DEFKEY_APPLY_COST,username);
//    	setAttr("costList", costList);
//    	//礼物申请
//    	List<Record> giftList = workflowService.getToDoListByKey(OaApplyGift.tableName,OAConstants.DEFKEY_APPLY_GIFT,username);
//    	setAttr("giftList", giftList);
//    	//宾馆申请
//    	List<Record> hotelList = workflowService.getToDoListByKey(OaApplyHotel.tableName,OAConstants.DEFKEY_APPLY_HOTEL,username);
//    	setAttr("hotelList", hotelList);
//    	//会议室申请
//    	List<Record> meetRoomList = workflowService.getToDoListByKey(OaApplyMeetroom.tableName,OAConstants.DEFKEY_APPLY_MEETROOM,username);
//    	setAttr("meetRoomList", meetRoomList);
//    	//办公用品申请
//    	List<Record> officeObjectList = workflowService.getToDoListByKey(OaApplyOfficeObject.tableName,OAConstants.DEFKEY_APPLY_OFFICEOBJECT,username);
//    	setAttr("officeObjectList", officeObjectList);
//    	//汽车借用申请
//    	List<Record> useCarList = workflowService.getToDoListByKey(OaApplyUseCar.tableName,OAConstants.DEFKEY_APPLY_USE_CAR,username);
//    	setAttr("useCarList", useCarList);
//    	//公章申请
//    	List<Record> sealList = workflowService.getToDoListByKey(OaApplySeal.tableName,OAConstants.DEFKEY_APPLY_SEAL,username);
//    	setAttr("sealList", sealList);
//    	//车船票申请
//    	List<Record> trainTicketList = workflowService.getToDoListByKey(OaApplyTrainTicket.tableName,OAConstants.DEFKEY_APPLY_TRAINTICKET,username);
//    	setAttr("trainTicketList", trainTicketList);
//    	//私车公用申请
//    	List<Record> userCarWorkList = workflowService.getToDoListByKey(OaApplyUsercarWork.tableName,OAConstants.DEFKEY_APPLY_USERCARWORK,username);
//    	setAttr("userCarWorkList", userCarWorkList);
//    	//调岗申请
//    	List<Record> userChangeStationList = workflowService.getToDoListByKey(OaApplyUserChangeStation.tableName,OAConstants.DEFKEY_APPLY_USERCHANGESTATION,username);
//    	setAttr("userChangeStationList", userChangeStationList);
//    	//转正申请
//    	List<Record> userRegularList = workflowService.getToDoListByKey(OaApplyUserRegular.tableName,OAConstants.DEFKEY_APPLY_USERREGULAR,username);
//    	setAttr("userRegularList", userRegularList);
//    	//辞职申请
//    	List<Record> userDimissionList = workflowService.getToDoListByKey(OaApplyUserDimission.tableName,OAConstants.DEFKEY_APPLY_USERDIMISSION,username);
//    	setAttr("userDimissionList", userDimissionList);
//    	//请假/公出申请
//    	List<Record> leaveList = workflowService.getToDoListByKey(OaApplyLeave.tableName,OAConstants.DEFKEY_APPLY_LEAVE,username);
//    	setAttr("leaveList", leaveList);
//    	//项目立项申请
//    	List<Record> projectList = workflowService.getToDoListByKey(OaProject.tableName,OAConstants.DEFKEY_PROJECT,username);
//    	setAttr("projectList", projectList);
    }
    
    /***
     * 设定已办数据
     */
    public void setAttrHavedoneList(String username){
    	List<String> havedoneKeyList = service.getHavedoneDefkeyList(ShiroKit.getUsername());
    	setAttr("havedoneKeyList", havedoneKeyList);
    }
}
