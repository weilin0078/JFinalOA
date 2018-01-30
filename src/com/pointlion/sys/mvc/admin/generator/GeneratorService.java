package com.pointlion.sys.mvc.admin.generator;

import java.util.List;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class GeneratorService {
	public static final GeneratorService me  = new GeneratorService();
	public static final GeneratorC generator  = GeneratorC.me;
	/***
	 * 自动生成
	 */
	public String doGenerator(String tableName,HtmlGenerateBean b){
		return generator.allRender(tableName,b);
	}
	
	/***
	 * 获取所有表名
	 */
	public List<Record> getAllTables(){
		return Db.find("select table_name from information_schema.tables where table_schema=?",PropKit.get("dbname"));
	}
	
	/***
	 * 查询所有列名
	 * @param tableName
	 * @return
	 */
	public List<Record> getAllCols(String tableName){
		return Db.find("select data_type,character_maximum_length,column_name,column_comment,table_name from INFORMATION_SCHEMA.Columns where table_name=? and table_schema=?",tableName,PropKit.get("dbname"));
	}
}
