/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.sys.mvc.mobile.chat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pointlion.sys.mvc.common.base.BaseController;
import com.pointlion.sys.mvc.common.model.SysUser;
import com.pointlion.sys.mvc.common.utils.Base64Util;
import com.pointlion.sys.mvc.common.utils.DateUtil;
import com.pointlion.sys.mvc.common.utils.UuidUtil;


/***
 * 通知公告控制器（web）
 * @author Administrator
 *
 */

public class ChatController extends BaseController {
	
	/***
	 * 发送消息
	 */
	public void sendMessage(){
		String userid = getPara("userid");
		String message = getPara("message");
		String orgid = getPara("orgid");
		ChatHistory chat = new ChatHistory();
		chat.setId(UuidUtil.getUUID());
		chat.setCreateTime(DateUtil.getTime());
		chat.setSendUserId(userid);
		chat.setReceiveOrgId(orgid);
		chat.setMessage(message);
		chat.setType("message");
		chat.save();
		renderSuccess();
	}
	
	/***
	 * 发送图片
	 */
	public void sendImg(){
		String userid = getPara("userid");
		String orgid = getPara("orgid");
		ChatHistory chat = new ChatHistory();
		chat.setId(UuidUtil.getUUID());
		chat.setCreateTime(DateUtil.getTime());
		chat.setSendUserId(userid);
		chat.setReceiveOrgId(orgid);
		chat.setType("img");
		String base64 = getPara("file");
		String basepath = this.getRequest().getSession().getServletContext().getRealPath("");
		String path = "/upload/chat/"+DateUtil.getYear()+"/"+DateUtil.getMonth();
		String filename = UuidUtil.getUUID()+".png";
		Base64Util.GenerateImage(base64,basepath+path,filename);
		chat.setImg(path+"/"+filename);
		chat.save();
		renderSuccess();
	}
	
	
	/***
	 * 初始化数据
	 */
	public void initData(){
		String orgid = getPara("orgid");
		String pageNum = getPara("pageNum");
		String pageCount = getPara("pageCount");
		String newTime = getPara("newTime");
		Integer num=Integer.parseInt(pageNum);
		Integer count=Integer.parseInt(pageCount);
		Integer a = num*count-count;
		List<ChatHistory> list = ChatHistory.dao.find("select * from (select h.*,u.name,u.img head from chat_history h,sys_user u where h.receive_org_id='"+orgid+"' AND h.send_user_id = u.username and h.create_time>'"+newTime+"' order by h.create_time DESC limit "+a+","+count+") a order by a.create_time");
		renderSuccess(list,"查询成功");
	}
	public void more(){
		String orgid = getPara("orgid");
		String pageNum = getPara("pageNum");
		String pageCount = getPara("pageCount");
		Integer num=Integer.parseInt(pageNum);
		Integer count=Integer.parseInt(pageCount);
		Integer a = num*count-count;
		List<ChatHistory> list = ChatHistory.dao.find("select * from (select * from chat_history h where h.receive_org_id='"+orgid+"' order by h.create_time DESC limit "+a+","+count+") a order by a.create_time");
		renderSuccess(list,"查询成功");
	}
	/***
	 * 群聊人员
	 */
	public void openGroup(){
		String orgid = getPara("orgid");
		String userid = getPara("userid");
		SysUser user = SysUser.dao.getByUsername(userid);
		Map<String , Object> data = new HashMap<String ,Object>();
		List<SysUser> list = SysUser.dao.getUserListByOrgId(orgid);
		data.put("list", list);
		data.put("me", user);
		renderSuccess(data,"查询成功");
	}
}
