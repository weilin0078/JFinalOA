package com.pointlion.mvc.admin.oa.apply.seal;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import com.pointlion.mvc.admin.oa.workflow.WorkFlowService;
import com.pointlion.mvc.common.base.BaseController;
import com.pointlion.mvc.common.model.OaApplySeal;
import com.pointlion.mvc.common.model.SysOrg;
import com.pointlion.mvc.common.model.SysUser;
import com.pointlion.mvc.common.utils.Constants;
import com.pointlion.mvc.common.utils.DateUtil;
import com.pointlion.mvc.common.utils.StringUtil;
import com.pointlion.mvc.common.utils.UuidUtil;
import com.pointlion.plugin.shiro.ShiroKit;


public class OaApplySealController extends BaseController {
	public static final OaApplySealService service = OaApplySealService.me;
	static WorkFlowService wfservice = WorkFlowService.me;
	/***
	 * 列表页面
	 */
	public void getApplyListPage(){

    	renderIframe("list.html");
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
    	OaApplySeal o = getModel(OaApplySeal.class);
    	if(StrKit.notBlank(o.getId())){
    		o.update();
    	}else{
    		o.setId(UuidUtil.getUUID());
    		o.setCreateTime(DateUtil.getCurrentTime());
    		o.save();
    	}
    	renderSuccess();
    }
    /***
     * 获取编辑页面
     */
    public void getEditPage(){

    	//添加和修改
    	String id = getPara("id");
    	if(StrKit.notBlank(id)){//修改
    		OaApplySeal o = service.getById(id);
    		setAttr("o", o);
    		//是否是查看详情页面
    		String view = getPara("view");//查看
    		if("detail".equals(view)){
    			setAttr("view", view);
    			if(StrKit.notBlank(o.getProcInsId())){
    				setAttr("procInsId", o.getProcInsId());
    				setAttr("defId", wfservice.getDefIdByInsId(o.getProcInsId()));
    			}
    		}
    	}else{
    		SysUser user = SysUser.dao.findById(ShiroKit.getUserId());
    		SysOrg org = SysOrg.dao.findById(user.getOrgid());
    		setAttr("user", user);
    		setAttr("org", org);
    	}
    	setAttr("formModelName",StringUtil.toLowerCaseFirstOne(OaApplySeal.class.getSimpleName()));//模型名称
    	renderIframe("edit.html");
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
    	OaApplySeal o = OaApplySeal.dao.getById(id);
    	o.setIfSubmit(Constants.IF_SUBMIT_YES);
    	String insId = wfservice.startProcess(id, o,o.getTitle(),null);
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
        	OaApplySeal o = OaApplySeal.dao.getById(id);
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
}