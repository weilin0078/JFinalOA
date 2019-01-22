package com.pointlion.sys.mvc.common.utils;

import com.pointlion.sys.mvc.common.utils.office.word.POITemplateUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class ExportUtil {
	/***
	* 
	* @param params   数据
	* @param tmptUrl  模版路径
	* @param writeUrl 写出路径
	* @throws Exception
	*/
    public static void export(Map<String, Object> params,String tmptUrl,String writeUrl) throws Exception{
	      InputStream is = new FileInputStream(tmptUrl);  
	      XWPFDocument doc = new XWPFDocument(is);  
	      //替换段落里面的变量  
	      POITemplateUtil.replaceQueInPara(doc, params);  
	      //替换表格里面的变量  
	      POITemplateUtil.replaceQueInTable(doc, params);  
	      OutputStream os = new FileOutputStream(writeUrl);  
	      doc.write(os);  
	      POITemplateUtil.close(os);  
	      POITemplateUtil.close(is);  
    }
}
