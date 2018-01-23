/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.sys.mvc.admin.notice;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.pointlion.sys.mvc.common.base.BaseController;
import com.pointlion.sys.mvc.common.model.OaNotice;
import com.pointlion.sys.mvc.common.model.SysOrg;
import com.pointlion.sys.mvc.common.model.SysUser;
import com.pointlion.sys.plugin.shiro.ShiroKit;

/***
 * 通知公告控制器（web）
 * @author Administrator
 *
 */
public class NoticeController extends BaseController {
	
	static NoticeService service =  NoticeService.me;
	
	/***************************通知公告---开始***********************/
	/***
	 * 获取通知公告发布列表页面
	 */
	public void getListPage(){
		setBread("通知公告",this.getRequest().getServletPath(),"管理");
		render("/WEB-INF/admin/notice/list.html");
    }
	/***
	 * 获取通知公告数据列表
	 */
	public void getListData(){
		String curr = getPara("pageNumber");
    	String pageSize = getPara("pageSize");
    	Page<OaNotice> page = OaNotice.dao.getPage(Integer.valueOf(curr),Integer.valueOf(pageSize));
    	renderPage(page.getList(),"" ,page.getTotalRow());
	}
	/***
	 * 获取通知公告起草页面
	 */
	public void getEditPage(){
		String parentPath = this.getRequest().getServletPath().substring(0,this.getRequest().getServletPath().lastIndexOf("/")+1);
		setBread("通知公告",parentPath+"getListPage","起草");
		//是否是查看详情页面
		String view = getPara("view");
		if("detail".equals(view)){
			setAttr("view", view);
		}
		String id = getPara("id");
		if(StrKit.notBlank(id)){
			setAttr("o", OaNotice.dao.findById(id));
		}else{
			OaNotice o = new OaNotice();
    		String userId = ShiroKit.getUserId();//用户主键
    		SysUser user = SysUser.dao.getById(userId);//用户对象
    		SysOrg org = SysOrg.dao.getById(user.getOrgid());//单位对象
    		o.setSenderId(userId);
    		o.setSenderName(user.getName());
    		o.setSenderOrgId(org.getId());
    		o.setSenderOrgName(org.getName());
    		setAttr("o",o);
		}
		render("/WEB-INF/admin/notice/edit.html");
	}
	/***
	 * 保存
	 */
	public void save(){
		service.save(getModel(OaNotice.class));
		renderSuccess();
	}
	/***
	 * 删除
	 */
	public void delete(){
		String ids = getPara("ids");
		service.deleteNotice(ids);
		renderSuccess();
	}
	
	
	/***
	 * 发布
	 */
	public void publish(){
		service.publish(getPara("id"));
		renderSuccess();
	}
	
	/***
	 * 取回
	 */
	public void callBack(){
		service.callBack(getPara("id"));
		renderSuccess();
	}
	/*****************管理通知公告结束*********************************/
	
	/***
	 * 首页查看通知公告
	 */
	public void viewNotice(){
		setAttr("notice", OaNotice.dao.findById(getPara("id")));
		render("/WEB-INF/admin/notice/view.html");
	}
	/***
	 * 签收公告
	 */
	public void sign(){
		service.sign(ShiroKit.getUserId(), getPara("id"));
		renderSuccess("签收成功");
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
