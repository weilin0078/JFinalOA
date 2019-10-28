package com.pointlion.mvc.admin.sys.generator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;

import com.pointlion.mvc.common.base.BaseController;


public class GeneratorController extends BaseController{
	public static final GeneratorService generatorService  = GeneratorService.me;
	
	/***
	 * 打开首页
	 */
	public void getListPage(){
		List<Record> list = generatorService.getAllTables();
		setAttr("tableList", list);
		renderIframe("list.html");
	}
	/***
	 * 自动生成
	 */
	public void doGenerate(){
		String tableName = getPara("tableName");
		HtmlGenerateBean b = new HtmlGenerateBean();
		b.setIfShowOnColA(getPara("ifShowOnColA"));//用于显示到列上
		b.setIfUserForQueryA(getPara("ifUseForQueryA"));//用于查询
		b.setIfShowOnColN(getPara("ifShowOnColN"));
		b.setIfUserForQueryN(getPara("ifUseForQueryN"));
		String result = generatorService.doGenerator(tableName,b);
		renderSuccess(result);
	}
	
	/***
	 * 获取所有列
	 */
	public void getAllCols(){
		String tableName = getPara("tableName");
		List<Record> list = generatorService.getAllCols(tableName);
		setAttr("cols", list);
		render("cols.html");
	}

}
