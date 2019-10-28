package com.pointlion.mvc.admin.sys.cms;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.mvc.admin.oa.notice.NoticeConstants;
import com.pointlion.mvc.common.model.CmsContent;
import com.pointlion.mvc.common.utils.DateUtil;
import com.pointlion.mvc.common.utils.UuidUtil;

/***
 * web端通知公告调用服务
 * @author Administrator
 *
 */
public class CmsService {
	
	public static final CmsService me = new CmsService();
	private final CmsContent dao = new CmsContent().dao();
	
	@Before(Tx.class)
	public void save(CmsContent content){
		if(StrKit.notBlank(content.getId())){//更新
			content.update();//更新
		}else{//保存
			content.setId(UuidUtil.getUUID());
			content.setCreateTime(DateUtil.getCurrentTime());
			content.save();//保存
		}
	}
	
	
	/***
	 * 删除通知公告
	 * @param id
	 */
	@Before(Tx.class)
	public void deleteNotice(String ids){
		String idarr[] = ids.split(",");
    	for(String id : idarr){
    		CmsContent content = dao.findById(id);
			if(content!=null){
				content.delete();//删除
			}
    	}
	}
	
	/***
	 * 发布
	 */
	public void publish(String id){
		CmsContent content = dao.findById(id);
		content.setIfPublish(NoticeConstants.NOTICE_IF_PUBLISH_YES);
		content.setPublishTime(DateUtil.getCurrentTime());
		content.update();
	}
	
	/***
	 * 取回
	 */
	@Before(Tx.class)
	public void callBack(String id){
		CmsContent content = dao.findById(id);
		content.setIfPublish(NoticeConstants.NOTICE_IF_PUBLISH_NO);
		content.setPublishTime("");
		content.update();
	}
	
	/***
	 * 获取我的通知公告
	 * @param userid
	 * @return
	 */
	public List<CmsContent> getMyNotice(String userid){
		return dao.find("select DISTINCT n.*,u.if_sign from oa_notice n ,oa_notice_user u where n.id=u.notice_id and u.user_id='"+userid+"' and n.if_publish='"+NoticeConstants.NOTICE_IF_PUBLISH_YES+"'");
	}
}
