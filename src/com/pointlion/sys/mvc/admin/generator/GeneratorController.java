package com.pointlion.sys.mvc.admin.generator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.pointlion.sys.interceptor.MainPageTitleInterceptor;
import com.pointlion.sys.mvc.common.base.BaseController;

@Before(MainPageTitleInterceptor.class)
public class GeneratorController extends BaseController{
	public static final GeneratorService generatorService  = GeneratorService.me;
	
	/***
	 * 打开首页
	 */
	public void getListPage(){
		List<Record> list = generatorService.getAllTables();
		setAttr("tableList", list);
		render("/WEB-INF/admin/generator/list.html");
	}
	/***
	 * 自动生成
	 */
	public void doGenerate(){
		String tableName = getPara("tableName");
		String ifShowOnCol = getPara("ifShowOnCol");//用于显示到列上
		String ifUserForQuery = getPara("ifUserForQuery");//用于查询
		String result = generatorService.doGenerator(tableName,ifShowOnCol,ifUserForQuery);
		renderSuccess(result);
	}
	
	/***
	 * 获取所有列
	 */
	public void getAllCols(){
		String tableName = getPara("tableName");
		List<Record> list = generatorService.getAllCols(tableName);
		setAttr("cols", list);
		render("/WEB-INF/admin/generator/cols.html");
	}
	
    /**************************************************************************/
	private String pageTitle = "代码生成";//模块页面标题
	private String breadHomeMethod = "getListPage";//面包屑首页方法
	
	public Map<String,String> getPageTitleBread() {
		Map<String,String> pageTitleBread = new HashMap<String,String>();
		pageTitleBread.put("pageTitle", pageTitle);
		pageTitleBread.put("breadHomeMethod", breadHomeMethod);
		return pageTitleBread;
	}
}
