/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.mvc.admin.sys.cms;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.pointlion.mvc.common.base.BaseController;
import com.pointlion.mvc.common.model.CmsContent;
import com.pointlion.mvc.common.model.CmsType;
import com.pointlion.mvc.common.model.OaNotice;
import com.pointlion.mvc.common.model.SysOrg;
import com.pointlion.mvc.common.model.SysUser;
import com.pointlion.mvc.common.utils.DateUtil;
import com.pointlion.plugin.shiro.ShiroKit;

/***
 * 通知公告控制器（web）
 * @author Administrator
 *
 */
public class CmsController extends BaseController {
	
	static CmsService service =  CmsService.me;
	static CmsContent dao = CmsContent.dao;
	
	/***************************内容管理---开始***********************/
	/***
	 * 获取通知公告发布列表页面
	 */
	public void getListPage(){

		setAttr("type", (StrKit.isBlank(getPara("type"))?"":getPara("type")));
		renderIframe("list.html");
    }
	/***
	 * 获取列表
	 */
	public void getListData(){
		String curr = getPara("pageNumber");
    	String pageSize = getPara("pageSize");
    	String type = StrKit.isBlank(getPara("type"))?"":getPara("type");
    	Page<OaNotice> page = dao.getPage(Integer.valueOf(curr),Integer.valueOf(pageSize),type);
    	renderPage(page.getList(),"" ,page.getTotalRow());
	}
	/***
	 * 获取编辑页面
	 */
	public void getEditPage(){
		String parentPath = this.getRequest().getServletPath().substring(0,this.getRequest().getServletPath().lastIndexOf("/")+1);
		String type = (StrKit.isBlank(getPara("type"))?"":getPara("type"));

		//是否是查看详情页面
		String view = getPara("view");
		if("detail".equals(view)){
			setAttr("view", view);
		}
		String id = getPara("id");
		if(StrKit.notBlank(id)){
			setAttr("o", dao.findById(id));
		}else{
			CmsContent o = new CmsContent();
    		String userId = ShiroKit.getUserId();//用户主键
    		SysUser user = SysUser.dao.getById(userId);//用户对象
    		SysOrg org = SysOrg.dao.getById(user.getOrgid());//单位对象
    		o.setWriterId(userId);
    		o.setWriterName(user.getName());
    		o.setWriterOrgid(org.getId());
    		o.setWriterOrgname(org.getName());
    		setAttr("o",o);
		}
		if("3".equals(type)){//如果是专题专栏
			//查出所有的类型
			setAttr("typeList", CmsType.dao.getAllGroup());
		}else if("1".equals(type)){
			Integer year = Integer.parseInt(DateUtil.getCurrentYear());
			List<String> yearlist = new ArrayList<String>();
			for(int i =1;i<=4;i++){
				yearlist.add(year+"");
				year--;
			}
			setAttr("yearlist", yearlist);
		}
		
		setAttr("type", type);
		renderIframe("edit.html");
	}
	/***
	 * 保存
	 */
	public void save(){
		service.save(getModel(CmsContent.class));
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
	
	
	
	/***
	 * 查看认领情况
	 */
	public void getReceiveListPage(){
		String parentPath = this.getRequest().getServletPath().substring(0,this.getRequest().getServletPath().lastIndexOf("/")+1);

		String id = getPara("id");
		List<Record> list = Db.find("select p.*,u.idcard,u.email,u.mobile,u.name,u.sex from sys_point_user p , sys_user u  where p.reason='帮扶认领' and p.userid=u.id and p.receive_content_id='"+id+"' order by  p.create_date ASC ");
		setAttr("list", list);
		renderIframe("receiveList.html");
	}
	/*****************管理通知公告结束*********************************/
	
	/***
	 * 首页查看通知公告
	 */
	public void view(){
		setAttr("notice", OaNotice.dao.findById(getPara("id")));
		renderIframe("view.html");
	}
	

}
