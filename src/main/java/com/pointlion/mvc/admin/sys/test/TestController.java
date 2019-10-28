/**

 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.mvc.admin.sys.test;

import com.fasterxml.jackson.databind.node.BooleanNode;
import com.jfinal.aop.Clear;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import com.pointlion.mvc.common.base.BaseController;
import com.pointlion.mvc.common.utils.UuidUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/***
 * 首页控制器
 */
@Clear
public class TestController extends BaseController {
	
	
	/***
	 * 测试事务
	 */
	public void shiwu() throws Exception{
	
//		Db.tx(new IAtom() {
//			public boolean run() throws SQLException {
		
		
					for(int i=0;i<1000;i++){
						final int j = 1 ;
							Db.tx(new IAtom() {
								public boolean run() throws SQLException {
										
													
													if( ifexist2(j+"")){
														try {
															Thread.sleep(5000);
														} catch (InterruptedException e) {
															e.printStackTrace();
														}
														Db.update("insert into sys_test(id,name,content) VALUES('"+ UuidUtil.getUUID() +"','"+j+"','"+j+"')");
													}
										
									return true;
								}
							});
					}
		
//				return true;
//			}
//		});
				renderSuccess();
	}
	
	public synchronized Boolean ifexist(String j){
		return Db.findFirst("select * from sys_test t where t.name='"+j+"'")==null;
	}
	
	public Boolean ifexist2(String j){
		System.out.println(j);
		return Db.findFirst("select * from sys_test t where t.name='"+j+"'")==null;
	}
	
	
	/****
	 * 获取国家税务总局数据
	 * @throws IOException
	 */
	public void getPolicyData() throws Exception {
		Connection conn = Jsoup.connect("http://www.chinatax.gov.cn/n810341/n810755/index.html").timeout(5000);
		conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		conn.header("Accept-Encoding", "gzip, deflate, sdch");
		conn.header("Accept-Language", "zh-CN,zh;q=0.8");
		conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
		Document doc = conn.get();
		Element element = doc.getElementById("comp_3849171");
		if(element!=null){
			Elements list = element.getElementsByTag("dd");
			for(Element e:list){
				Element aE = e.getElementsByTag("a").first();
				if(aE!=null){
					String href = aE.attr("href");
					String url = "http://www.chinatax.gov.cn/"+href.replace("../../","");
					Document d = Jsoup.connect(url).get();
					Element titleE = d.getElementsByClass("sv_blue24").first();
					String title = titleE.text();
					System.out.println(url);
					System.out.println(title);
					Thread.sleep(2000);
				}
			}
		}
		renderSuccess();
	}
}
