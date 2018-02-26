package com.pointlion.sys.mvc.admin.bumph;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.ProcessEngine;
import org.apache.commons.lang3.StringUtils;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.sys.mvc.admin.workflow.WorkFlowService;
import com.pointlion.sys.mvc.common.model.OaBumph;
import com.pointlion.sys.mvc.common.model.OaBumphOrg;
import com.pointlion.sys.mvc.common.model.SysOrg;
import com.pointlion.sys.mvc.common.model.SysUser;
import com.pointlion.sys.mvc.common.model.VTasklist;
import com.pointlion.sys.mvc.common.utils.DateUtil;
import com.pointlion.sys.mvc.common.utils.UuidUtil;
import com.pointlion.sys.plugin.activiti.ActivitiPlugin;

public class BumphService {
	public static final BumphService me = new BumphService();
	public static final WorkFlowService workFlowService = new WorkFlowService();
	/***
	 * 保存主送和抄送
	 * @param bumph
	 * @param fidstr
	 * @param secondStr
	 */
	@Before(Tx.class)
	public void save(OaBumph bumph,String fidstr ,String secondStr){
		OaBumphOrg.dao.deleteOrgByBumphId(bumph.getId());//先清空掉所有单位
		if(StrKit.notBlank(bumph.getId())){
    		bumph.update();//更新公文
    		saveOrg(BumphConstants.TYPE_ZHUSONG,bumph.getId(),fidstr);//更新主送单位
    		saveOrg(BumphConstants.TYPE_CHAOSONG,bumph.getId(),secondStr);//更新抄送单位
    	}else{
    		bumph.setId(UuidUtil.getUUID());
    		bumph.setDocNumSource("行政发");
    		bumph.setDocNum(bumph.getDocNumSource()+"["+bumph.getDocNumYear()+"]"+bumph.getDocNumN()+"号");
    		bumph.setCreateTime(DateUtil.getTime());
    		bumph.save();//保存公文
    		saveOrg(BumphConstants.TYPE_ZHUSONG,bumph.getId(),fidstr);//更新主送单位
    		saveOrg(BumphConstants.TYPE_CHAOSONG,bumph.getId(),secondStr);//更新抄送单位
    	}
	}
	
	
	/***
	 * 存储主送和抄送单位
	 * @param type
	 * @param orgidstr
	 */
	@Before(Tx.class)
	public void saveOrg(String type,String bumphId,String orgidstr){
		if(StrKit.notBlank(orgidstr)){//单位字符串
			String idarr[] = orgidstr.split(",");
			for(String id :idarr){
				OaBumphOrg org = new OaBumphOrg();
				org.setId(UuidUtil.getUUID());
				org.setBumphId(bumphId);
				org.setOrgid(id);
				org.setOrgname(SysOrg.dao.getById(id).getName());
				org.setType(type);
				org.save();
			}
		}
	}
	
	/***
	 * 删除
	 */
	@Before(Tx.class)
	public void delete(String id){
		OaBumph.dao.findById(id).delete();
		OaBumphOrg.dao.deleteOrgByBumphId(id);
	}
	
	/***
	 * 启动流程
	 */
	@Before(Tx.class)
	public void startProcess(String id){
		WorkFlowService service = new WorkFlowService();
		Map<String, Object> var = new HashMap<String,Object>();
		//查询主送和抄送所有的用户
		List<OaBumphOrg> list = OaBumphOrg.dao.getList(id, null);//主送单位和抄送单位
		List<String> userlist = new ArrayList<String>();//需要阅读公文的人员列表
		for(OaBumphOrg org:list){
			List<SysUser> ulist = SysUser.dao.getUserListByOrgId(org.getId());//单位下的用户
			for(SysUser u:ulist){
				userlist.add(u.getUsername());
			}
		}
		var.put("userlist", userlist);//需要阅读公文的所有人员
		String procInsId = service.startProcess(id, BumphConstants.DEFKEY_BUMPH,var);
		OaBumph bumph = OaBumph.dao.findById(id);
		bumph.setProcInsId(procInsId);
		bumph.setIfSubmit(BumphConstants.IF_SUBMIT_YES);
		bumph.update();
	}
	
	@Before(Tx.class)
	public void callBack(String id){
		OaBumph bumph = OaBumph.dao.findById(id);
		String procid = bumph.getProcInsId();//流程实例id
    	bumph.setFirstLeaderAudit("");//清空审批信息
    	bumph.setSecondLeaderAudit("");
    	bumph.setProcInsId("");
    	bumph.setIfSubmit(BumphConstants.IF_SUBMIT_NO);
    	bumph.update();
    	ProcessEngine pe = ActivitiPlugin.buildProcessEngine();
    	pe.getRuntimeService().deleteProcessInstance(procid, "删除流程实例");
    	pe.getHistoryService().deleteHistoricProcessInstance(procid);
	}
	
	/***
	 * 提交任务
	 */
	@Before(Tx.class)
	public void completeTask(String pass,String comment,String taskid,OaBumph bumph){
		WorkFlowService service = new WorkFlowService();
		Map<String,Object> var = new HashMap<String,Object>();
		var.put("pass", pass);
		VTasklist task = VTasklist.dao.findById(taskid);
		if(task.getTASKDEFKEY().equals("bookersend")){//文书发文的任务
			bumph.setSendTime(DateUtil.getTime());
		}
    	service.completeTask(taskid,comment,var);//提交任务
    	bumph.update();
	}
	
	/***
	 * 获取经办人 历史
	 */
	public Page<Record> getPartList(int curr, int pageSize, String title,String username){
		String sql =" FROM  oa_bumph b ,act_hi_identitylink i ,act_hi_procinst p where i.PROC_INST_ID_=p.ID_ AND i.PROC_INST_ID_ = b.proc_ins_id ";
		if(StrKit.notBlank(title)){
			sql += "and b.title like '%"+title+"%'";
		}
		if(StrKit.notBlank(username)){
			sql += " and i.USER_ID_='"+username+"' ";
		}
			sql +=" order by b.create_time desc ";
		Page<Record> page = Db.paginate(curr, pageSize, "SELECT DISTINCT b.*,p.PROC_DEF_ID_ defid ", sql);
		return page;
	}
	
	/***
	 * 导出
	 * @throws Exception 
	 */
	public File bumphExport(String id,HttpServletRequest request) throws Exception{
		String path = request.getSession().getServletContext().getRealPath("")+"\\WEB-INF\\admin\\bumph\\template\\";
		OaBumph bumph = OaBumph.dao.findById(id);
		Map<String , Object > data = new HashMap<String , Object>();
		data.put("doc_num_n", bumph.getDocNumN());
		data.put("sender_orgname", bumph.getSenderOrgname());
		data.put("doc_num", bumph.getDocNum());
		String sendtime = bumph.getSendTime();
		if(StrKit.notBlank(sendtime)){//发文时间
			data.put("sendDate", DateUtil.dateToString(DateUtil.StringToDate(sendtime),7));
		}
		String receivetime = bumph.getSendTime();
		if(StrKit.notBlank(receivetime)){//收文时间
			data.put("receiveDate",DateUtil.dateToString(DateUtil.StringToDate(receivetime),7));
		}
		data.put("docName", bumph.getTitle());
		data.put("firstAuditComment", bumph.getFirstLeaderAudit());
		data.put("secondAuditComment", bumph.getSecondLeaderAudit());
		List<Record> list = workFlowService.getHisTaskParter(bumph.getProcInsId());
		List<String> receivelist = new ArrayList<String>();
		for(Record r : list){
			receivelist.add(r.getStr("name"));
		}
		data.put("receiver", StringUtils.join(receivelist,','));
		String exportURL = path+bumph.getTitle()+bumph.getId()+".docx";
		OaBumph.dao.bumphExport(data, path+"bumph_"+bumph.getDocType()+".docx", exportURL);
		File file = new File(exportURL);
		if(file.exists()){
			return file;
		}else{
			return null;
		}
	}



}
