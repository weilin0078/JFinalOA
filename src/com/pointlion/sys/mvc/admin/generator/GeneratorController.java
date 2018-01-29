package com.pointlion.sys.mvc.admin.generator;

import com.pointlion.sys.mvc.common.base.BaseController;

public class GeneratorController extends BaseController{
	public static final GeneratorService generate  = GeneratorService.me;
	public void doGenerate(){
		String tableName = getPara("tableName");
		generate.javaRender(tableName);;
	}
	
	
}
