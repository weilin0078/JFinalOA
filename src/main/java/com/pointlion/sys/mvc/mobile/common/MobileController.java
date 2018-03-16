/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.sys.mvc.mobile.common;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;

import com.jfinal.aop.Clear;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import com.pointlion.sys.mvc.common.base.BaseController;
import com.pointlion.sys.mvc.common.model.SysOrg;
import com.pointlion.sys.mvc.common.model.SysPoint;
import com.pointlion.sys.mvc.common.model.SysPointUser;
import com.pointlion.sys.mvc.common.model.SysUser;
import com.pointlion.sys.mvc.common.utils.Base64Util;
import com.pointlion.sys.mvc.common.utils.DateUtil;
import com.pointlion.sys.mvc.common.utils.UuidUtil;

/***
 * 手机首页控制器
 * @author Administrator
 *
 */
@Clear()
public class MobileController extends BaseController {
	
	static MobileService service =  MobileService.me;
	
	/***
	 * 获取新闻头条
	 */
	public void getBanner(){
		renderSuccess(service.getBanner("banner"),"请求成功");
	}
	
	/***
	 * 获取内容
	 */
	public void getContent(){
		String id = getPara("id");//主键
		renderSuccess(service.getContent(id),"请求成功");
	}
	
	/***
	 * 获取某种类型的列表
	 */
	public void getContentList(){
		String type = getPara("type");//类型
		String pageNum = getPara("pageNum");
		String pageCount = getPara("pageCount");
		if(StrKit.notBlank(pageNum)&&StrKit.notBlank(pageCount)){
			Integer num=Integer.parseInt(pageNum);
			Integer count=Integer.parseInt(pageCount);
			renderSuccess(service.getContentPage(type,num,count),"请求成功");
		}else{
			renderSuccess(service.getContentList(type),"请求成功");
		}
		
	}
	
	/*******************个人设置************************/
	public void getUser(){
		String userid = getPara("userid");
		SysUser user = SysUser.dao.getByUsername(userid);
		renderSuccess(user,"查询成功");
	}
	public void updateUser(){
		SysUser user = getModel(SysUser.class);
		if(StrKit.notBlank(user.getId())){
			user.update();
			renderSuccess("保存成功");
		}else{
			renderError();
		}
	}
	public void modifyPassword(){
		String userid = getPara("userid");
		String nowPassword = getPara("nowPassword");
		String newPassword = getPara("newPassword");
		SysUser user = SysUser.dao.getByUsername(userid);
		if(user==null){
        	renderError("数据错误");
        }else{
        	//验证密码
        	PasswordService svc = new DefaultPasswordService();
        	if(svc.passwordsMatch(nowPassword, user.getPassword())){
        		user.setPassword(svc.encryptPassword(newPassword));//加密新密码
        		user.update();
        		renderSuccess("修改密码成功");
        	}else{
        		renderError("当前密码错误");
        	}
        }
		
	}
	
	/***
	 * 获取某人签到数据
	 */
	public void getMyDaySign(){
		String userid = getPara("userid");
		SysUser user = SysUser.dao.getByUsername(userid);
		renderSuccess(SysPointUser.dao.getSignByUser(user.getId()),"查询成功");
	}
	
	/***
	 * 签到
	 */
	public void daySign(){
		final String userid = getPara("userid");
		Db.tx(new IAtom() {
            @Override  
            public boolean run() throws SQLException {  
            	SysUser user = SysUser.dao.getByUsername(userid);
        		String day = DateUtil.getDate();
        		SysPointUser pu = SysPointUser.dao.getSignByUserAndDate(user.getId(), day);
            	if(pu!=null){//已经签到
        			renderError("您已经签到过了！");
        		}else{
        			SysPoint point = SysPoint.dao.getByType("daySign");
        			Integer p = point.getPoint();
        			pu = new SysPointUser();
        			pu.setCreateDate(DateUtil.getTime());
        			pu.setUserid(user.getId());
        			pu.setSignDay(day);
        			pu.setId(UuidUtil.getUUID());
        			pu.setPoint(p);
        			pu.setUserid(user.getId());
        			pu.setReason("签到");
        			pu.save();
        			renderSuccess("签到成功");
        		}
                return true;  
            }  
        });
		
	}
	
	
	/***
	 * 查询用户积分
	 */
	public void pointQuery(){
		String userid = getPara("userid");
		SysUser user = SysUser.dao.getByUsername(userid);//用户
		Map<String , Integer> data = new HashMap<String , Integer>();
		Integer daySignCount = SysPointUser.dao.getPointByDaySign(user.getId());
		Integer receiveCount = SysPointUser.dao.getPointByReceive(user.getId());
		data.put("daySignCount", daySignCount);
		data.put("receiveCount", receiveCount);
		data.put("sumCount", daySignCount+receiveCount);
		renderSuccess(data,"查询成功");
	}
	
	
	
	/***
	 * 查询用户个人信息
	 */
	public void getUserInfo(){
		Map<String , Object> data = new HashMap<String , Object>();
		String userid = getPara("userid");
		SysUser user = SysUser.dao.getByUsername(userid);//用户
		SysOrg org = SysOrg.dao.getById(user.getOrgid());
		data.put("orgid", org.getId());//--组织建设和全程纪实用
		data.put("orgname", org.getName());//--组织建设和全程纪实用
		data.put("orgtype", org.getType());//类型，用来打开不同的组织建设页面
		data.put("user", user);
		//获取全程纪实最高权限
		Record r = Db.findFirst("select * from sys_user_role ur , sys_role r where ur.role_id=r.id and ur.user_id='"+user.getId()+"' ORDER BY field(r.key,'VillageManager','StreetManager','AreaManager') DESC ");
		if(r==null){
			data.put("auth", "none");
		}else{
			data.put("auth", r.get("key"));
		}
		
		renderSuccess(data,"查询成功");
	}
	
	
	/***
	 * 文件上传
	 */
	public void uploadHead(){
		String userid = getPara("userid");
		SysUser user = SysUser.dao.getByUsername(userid);//用户
		
		String base64 = getPara("file");
		String basepath = this.getRequest().getSession().getServletContext().getRealPath("");
		String path = "/upload/userHead/"+DateUtil.getYear()+"/"+DateUtil.getMonth();
		String filename = UuidUtil.getUUID()+".png";
		Base64Util.GenerateImage(base64,basepath+path,filename);
		user.setImg(path+"/"+filename);
		user.update();
		renderSuccess("上传成功");
	}
}
