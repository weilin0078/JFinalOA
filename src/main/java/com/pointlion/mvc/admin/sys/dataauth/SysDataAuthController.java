package com.pointlion.mvc.admin.sys.dataauth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import com.pointlion.mvc.admin.oa.workflow.WorkFlowService;
import com.pointlion.mvc.common.base.BaseController;
import com.pointlion.mvc.common.model.SysDataAuth;
import com.pointlion.mvc.common.model.SysRole;
import com.pointlion.mvc.common.utils.StringUtil;
import com.pointlion.mvc.common.utils.UuidUtil;




public class SysDataAuthController extends BaseController {
	public static final SysDataAuthService service = SysDataAuthService.me;
	public static WorkFlowService wfservice = WorkFlowService.me;
	/***
	 * 列表页面
	 */
	public void getListPage(){

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
    	SysDataAuth o = getModel(SysDataAuth.class);
    	if(StrKit.notBlank(o.getId())){
    		if(StrKit.isBlank(o.getStatus())){//不启用
    			o.setStatus("0");
    		}
    		o.update();
    	}else{
    		o.setId(UuidUtil.getUUID());
    		o.save();
    	}
    	renderSuccess();
    }
    /***
     * 获取编辑页面
     */
    public void getEditPage(){

    	String id = getPara("id");
    	if(StrKit.notBlank(id)){//修改
    		SysDataAuth o = service.getById(id);
    		setAttr("o", o);
    		//是否是查看详情页面
    		String view = getPara("view");//查看
    		if("detail".equals(view)){
    			setAttr("view", view);
    		}
    		List<Record> colList = service.getAllCols(o.getTable());
    		setAttr("colList", colList);
    	}
    	List<Record> tableList = service.getAllTables();//所有的表
		setAttr("tableList", tableList);
		List<SysRole> roleList = SysRole.dao.getAllRole();
		setAttr("roleList", roleList);
		List<Record> colList = service.getAllCols(tableList.get(0).getStr("table_name"));
		setAttr("colList", colList);
    	setAttr("formModelName",StringUtil.toLowerCaseFirstOne(SysDataAuth.class.getSimpleName()));//模型名称
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

}