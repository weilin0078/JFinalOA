package com.pointlion.mvc.admin.sys.mobilemessage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.mvc.common.model.SysMobileMessage;
import com.pointlion.mvc.common.model.SysUser;
import com.pointlion.mvc.common.model.VTasklist;
import com.pointlion.mvc.common.utils.DateUtil;
import com.pointlion.mvc.common.utils.ListUtil;
import com.pointlion.mvc.common.utils.StringUtil;
import com.pointlion.mvc.common.utils.UuidUtil;
import com.pointlion.plugin.shiro.ShiroKit;

public class SysMobileMessageService{
	public static final SysMobileMessageService me = new SysMobileMessageService();
	public static final String TABLE_NAME = SysMobileMessage.tableName;

//	public static void main(String args[]){
//		List<String> a = new ArrayList<String>();
//		a.add("18678837411");
//		me.sendMeesageByEXin(a,"","","","");
//	}
	/***
	 * 发送短信入口
	 */
	public void sendMessage(String insid){
		List<VTasklist> list = VTasklist.dao.find("select * from v_tasklist t where t.INSID='"+insid+"'");//查询待办
		List<String> mobileList = new ArrayList<String>();
		List<String> idList = new ArrayList<String>();
		List<String> nameList = new ArrayList<String>();
		List<String> usernameList = new ArrayList<String>();
		for(VTasklist t : list){
			String username = t.getASSIGNEE();
			if(StrKit.isBlank(username)){//办理人
				username=t.getCANDIDATE();//候选人
			}
			if(StrKit.notBlank(username)){
				SysUser user = SysUser.dao.getByUsername(username);
				if(user!=null){//能取到用户
					String mobile = user.getMobile();
					if(StrKit.notBlank(mobile)){//能取到手机号
						mobileList.add(mobile);
						idList.add(user.getId());
						usernameList.add(user.getUsername());
						nameList.add(user.getName());
					}
				}
			}
		}
		if(mobileList.size()>0){
			sendMeesageByEXin(mobileList,
					StringUtils.join(idList,","),
					StringUtils.join(mobileList,","),
					StringUtils.join(mobileList,","),
					"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		}
	}
	
	/***
	 * 使用e信发送短信
	 */
	@SuppressWarnings("unchecked")
	public void sendMeesageByEXin(List<String> mobileList,String toUserid,String toUsername,String toName,String message){
		mobileList = ListUtil.removeDuplicate(mobileList);//去掉重复数据
		String mobile = StringUtils.join(mobileList,",");
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost("https://smsapi.ums86.com:9600/sms/Api/Send.do");
		// 创建参数队列
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		formParams.add(new BasicNameValuePair("MessageContent", message));//短息内容。最大402个字或字符，短信发送必须按照短信模板，否则就会报模板不符，短信模板说明见3.1.1。
		formParams.add(new BasicNameValuePair("UserNumber", mobile));//手机号码(多个号码用”,”分隔)，最多1000个号码
		formParams.add(new BasicNameValuePair("SerialNumber", StringUtil.addZeroForNum(DateUtil.format(new Date(), "yyyyMMddHHmmssSSS"), 20,"right")));//流水号，20位数字，每个请求流水号要求唯一（规则自定义,建议时间格式精确到毫秒）必填参数，与回执接口中的流水号一一对应，不传后面回执接口无法查询数据。
		formParams.add(new BasicNameValuePair("ScheduleTime", ""));//预约发送时间，格式:yyyyMMddHHmmss,如‘20090901010101’，立即发送请填空（预约时间要写当前时间5分钟之后的时间，若预约时间少于5分钟，则为立即发送。）
		formParams.add(new BasicNameValuePair("ExtendAccessNum", ""));//接入号扩展号（默认不填，扩展号为数字，扩展位数由当前所配的接入号长度决定，整个接入号最长20位）
		formParams.add(new BasicNameValuePair("f", "1"));//提交时检测方式1 --- 提交号码中有效的号码仍正常发出短信，无效的号码在返回参数faillist中列出。不为1 或该参数不存在 --- 提交号码中只要有无效的号码，那么所有的号码都不发出短信，所有的号码在返回参数faillist中列出
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formParams, "gbk");
			httppost.setEntity(uefEntity);
			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				String result = EntityUtils.toString(entity, "gbk");//result=28&description=发送内容与模板不符
				if (entity != null) {
					System.out.println("--------------------------------------");
					System.out.println("发送短信结果: " + result);
					System.out.println("--------------------------------------");
					//**********存入发送短信发送记录表
					SysMobileMessage m = new SysMobileMessage();
					m.setId(UuidUtil.getUUID());
					m.setSendTime(DateUtil.getCurrentTime());
					m.setMessage(message);
					m.setMessagePlatfrom("E信");
					m.setToName(toName);
					m.setToUsername(toUsername);
					m.setToUserid(toUserid);
					m.setType("1");
					String code = result.substring(result.indexOf("=")+1, result.indexOf("&"));
					System.out.println("--------------------------------------");
					System.out.println("发送短信结果编码: " + code);
					System.out.println("--------------------------------------");
					if("0".equals(code)){
						m.setIfSuccess("1");//成功
					}else{
						m.setIfSuccess("0");//失败
					}
					m.setCode(code);
					String des = result.substring(result.indexOf("description=")+12);
					System.out.println("--------------------------------------");
					System.out.println("发送短信结果编码: " + des);
					System.out.println("--------------------------------------");
					m.setResDes(des);
					m.save();
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}



//		String info = null;
//		try{
//			CloseableHttpClient httpClient = HttpClients.createDefault();
////			BTW,4.3版本不设置超时的话，一旦服务器没有响应，等待时间N久(>24小时)。
//			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();//设置请求和传输超时时间
//			HttpGet httpget = new HttpGet("http://112.65.228.39:8897/sms/Api/Send.do");
//			httpget.getParams().setParameter(CoreConnectionPNames, "");
////			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"gbk");
////			post.addParameter("SpCode", "1");
////			post.addParameter("LoginName", "1");
////			post.addParameter("Password", "1");
////			post.addParameter("MessageContent", "你好");
////			post.addParameter("UserNumber", "18616330318");
////			post.addParameter("SerialNumber", "");
////			post.addParameter("ScheduleTime", "");
////			post.addParameter("ExtendAccessNum", "");
////			post.addParameter("f", "1");
////			httpclient.executeMethod(post);
////			info = new String(post.getResponseBody(),"gbk");
//			CloseableHttpResponse response = httpget..execute(httpget);
//			System.out.println(info);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	/***
	 * 根据主键查询
	 */
	public SysMobileMessage getById(String id){
		return SysMobileMessage.dao.findById(id);
	}
	
	/***
	 * 获取分页
	 */
	public Page<Record> getPage(int pnum,int psize){
		String sql  = " from "+TABLE_NAME+" o LEFT JOIN act_hi_procinst p ON o.proc_ins_id=p.ID_  where o.userid='"+ShiroKit.getUserId()+"'  order by o.create_time desc";
		return Db.paginate(pnum, psize, " select * ", sql);
	}
	
	/***
	 * 删除
	 * @param ids
	 */
	@Before(Tx.class)
	public void deleteByIds(String ids){
		String idarr[] = ids.split(",");
		for(String id : idarr){
			SysMobileMessage o = me.getById(id);
			o.delete();
		}
	}
	
}