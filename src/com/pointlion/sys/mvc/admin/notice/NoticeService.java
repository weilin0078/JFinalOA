package com.pointlion.sys.mvc.admin.notice;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.sys.mvc.common.model.OaNotice;
import com.pointlion.sys.mvc.common.model.OaNoticeUser;
import com.pointlion.sys.mvc.common.model.SysUser;
import com.pointlion.sys.mvc.common.utils.DateUtil;
import com.pointlion.sys.mvc.common.utils.UuidUtil;

/***
 * web端通知公告调用服务
 * @author Administrator
 *
 */
public class NoticeService {
	
	public static final NoticeService me = new NoticeService();
	private final OaNotice dao = new OaNotice().dao();
	private final OaNoticeUser nudao = new OaNoticeUser().dao();
	
	@Before(Tx.class)
	public void save(OaNotice notice){
		if(StrKit.notBlank(notice.getId())){//更新
			notice.update();//更新公共
		}else{//保存
			notice.setId(UuidUtil.getUUID());
			notice.setCreateTime(DateUtil.getTime());
			notice.save();//保存公告
		}
		//根据推送单位，保存通知到的人
		deleteNoticeUserByNoticeId(notice.getId());//删除该通知所有通知到人
		String orgidarr[] = notice.getToOrgId().split(",");
		for(String orgid : orgidarr){
			List<SysUser> userlist = SysUser.dao.getUserListByOrgId(orgid);//查询机构下所有人员
			for(SysUser user : userlist){
				OaNoticeUser noticeuser = new OaNoticeUser();
				noticeuser.setId(UuidUtil.getUUID());
				noticeuser.setUserId(user.getId());
				noticeuser.setUserName(user.getName());
				noticeuser.setNoticeId(notice.getId());
				noticeuser.save();
			}
		}
	}
	
	/***
	 * 删除公告下所有通知到人
	 * @param noticeid
	 */
	public void deleteNoticeUserByNoticeId(String noticeid){
		Db.update("delete from oa_notice_user where notice_id='"+noticeid+"'");//删除该通知所有通知到人
	} 
	
	/***
	 * 删除通知公告
	 * @param id
	 */
	@Before(Tx.class)
	public void deleteNotice(String id){
		OaNotice notice = OaNotice.dao.findById(id);
		if(notice!=null){
			notice.delete();
		}
		deleteNoticeUserByNoticeId(id);
	}
	
	/***
	 * 发布
	 */
	public void publish(String id){
		OaNotice notice = OaNotice.dao.findById(id);
		notice.setIfPublish(NoticeConstants.NOTICE_IF_PUBLISH_YES);
		notice.setPublicTime(DateUtil.getTime());
		notice.update();
	}
	
	/***
	 * 取回
	 */
	@Before(Tx.class)
	public void callBack(String id){
		OaNotice notice = OaNotice.dao.findById(id);
		notice.setIfPublish(NoticeConstants.NOTICE_IF_PUBLISH_NO);
		notice.setPublicTime("");
		notice.update();
		//将所有被通知人签收状态改为未签收
		List<OaNoticeUser> list = OaNoticeUser.dao.getNoticeUserListByNoticeId(id);
		for(OaNoticeUser o : list){
			o.setIfSign(NoticeConstants.NOTICE_IF_SIGN_NO);
			o.setSignTime("");
			o.update();
		}
	}
	
	/***
	 * 获取我的通知公告
	 * @param userid
	 * @return
	 */
	public List<OaNotice> getMyNotice(String userid){
		return dao.find("select DISTINCT n.*,u.if_sign from oa_notice n ,oa_notice_user u where n.id=u.notice_id and u.user_id='"+userid+"' and n.if_publish='"+NoticeConstants.NOTICE_IF_PUBLISH_YES+"'");
	}
	/***
	 * 签收公告
	 * @param userid
	 * @param noticeid
	 */
	public void sign(String userid,String noticeid){
		OaNoticeUser u = nudao.findFirst("select * from oa_notice_user u where u.notice_id = '"+noticeid+"' and u.user_id='"+userid+"'");
		u.setIfSign(NoticeConstants.NOTICE_IF_SIGN_YES);
		u.setSignTime(DateUtil.getTime());
		u.update();
	}
}
