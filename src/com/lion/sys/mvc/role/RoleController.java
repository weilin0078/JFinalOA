/**
 * 
 */
package com.lion.sys.mvc.role;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.lion.sys.mvc.base.BaseController;
import com.lion.sys.mvc.menu.SysMenu;
import com.lion.sys.tool.UuidUtil;

/**
 * @author Lion
 * @date 2017年2月16日 下午4:04:25
 * @qq 439635374
 */

public class RoleController extends BaseController{
	/***
	 * 获得列表页
	 */
	public void getListPage(){
    	render("/WEB-INF/admin/role/list.html");
    }
    /***
     * 获取编辑页面
     */
    public void getEditPage(){
    	//添加和修改
    	String id = getPara("id");
    	if(StringUtils.isNotBlank(id)){
    		SysRole role = SysRole.dao.getById(id);
    		setAttr("role", role);
    	}
    	render("/WEB-INF/admin/role/edit.html");
    }
    /***
     * 保存
     */
    public void save(){
    	SysRole role = getModel(SysRole.class);
    	if(StringUtils.isNotBlank(role.getId())){
    		role.update();
    	}else{
    		role.setId(UuidUtil.getUUID());
    		role.save();
    	}
    	renderSuccess();
    }
    /***
     * 查询分页数据
     */
    public void listData(){
    	String curr = getPara("pageIndex");
    	String pageSize = getPara("pageSize");
    	Page<SysRole> page = SysRole.dao.getRolePage(Integer.valueOf(curr),Integer.valueOf(pageSize));
    	renderPage(page.getList(),"" ,page.getTotalRow());
    }
    
    /***
     * 删除
     * @throws Exception
     */
    @Before(Tx.class)
    public void delete() throws Exception{
		String ids = getPara("ids");
    	String idarr[] = ids.split(",");
    	for(String id : idarr){
			SysRole menu = SysRole.dao.getById(id);
			if(menu!=null){
				menu.delete();//删除
			}
    	}
    	renderSuccess();
    }
}
