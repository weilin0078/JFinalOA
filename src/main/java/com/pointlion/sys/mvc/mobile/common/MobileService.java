package com.pointlion.sys.mvc.mobile.common;

import java.util.List;

import com.pointlion.sys.mvc.admin.notice.NoticeService;
import com.pointlion.sys.mvc.common.model.CmsContent;


/***
 * 手机端通知公告调用服务
 * @author Administrator
 *
 */
public class MobileService extends NoticeService{
	public static final MobileService me = new MobileService();
	
	/***
	 * 获取内容列表
	 * @param type
	 * @return
	 */
	public List<CmsContent> getContentList(String type){
		return CmsContent.dao.find("select * from cms_content t where t.type='"+type+"' and t.if_publish='1' order by t.publish_time desc");
	}
	public List<CmsContent> getContentPage(String type,Integer num,Integer count){
		Integer a = num*count-count;
		return CmsContent.dao.find("select * from cms_content t where t.type='"+type+"' and t.if_publish='1' order by t.publish_time desc limit "+a+","+count);
	}
	/***
	 * 获取banner
	 * @param type
	 * @return
	 */
	public List<CmsContent> getBanner(String type){
		return CmsContent.dao.find("select * from cms_content t where t.type='"+type+"' and t.if_publish='1' order by t.publish_time desc");
	}
	
	/***
	 * 获取内容
	 * @param type
	 * @return
	 */
	public CmsContent getContent(String id){
		return CmsContent.dao.findFirst("select * from cms_content t where t.id='"+id+"' order by t.publish_time desc");
	}
}
