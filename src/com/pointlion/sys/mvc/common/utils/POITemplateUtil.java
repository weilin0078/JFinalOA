package com.pointlion.sys.mvc.common.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class POITemplateUtil {
	public static String NewLine = "@@@@@@newLine@@@@@@";
	public static String mark="$";
		/***
	    * 
	    * @param params   数据
	    * @param tmptUrl  模版路径
	    * @param writeUrl 写出路径
	    * @throws Exception
	    */
	   public static void bumphExport(Map<String, Object> params,String tmptUrl,String writeUrl) throws Exception{
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
	   /** 
	    * 替换段落里面的变量 
	    * @param doc 要替换的文档 
	    * @param params 参数 
	    */  
	   public static void replaceQueInPara(XWPFDocument doc, Map<String, Object> params) {  
	      Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();  
	      XWPFParagraph para;  
	      while (iterator.hasNext()) {  
	         para = iterator.next();  
	         replaceInPara(para, params);  
	      }  
	   }
	   /** 
	    * 替换表格里面的变量 
	    * @param doc 要替换的文档 
	    * @param params 参数 
	    */  
	   public static void replaceQueInTable(XWPFDocument doc, Map<String, Object> params) {  
	      Iterator<XWPFTable> iterator = doc.getTablesIterator();  
	      XWPFTable table;  
	      List<XWPFTableRow> rows;  
	      List<XWPFTableCell> cells;  
	      List<XWPFParagraph> paras;  
	      while (iterator.hasNext()) {  
	         table = iterator.next();  
	         rows = table.getRows();  
	         for (XWPFTableRow row : rows) {  
	            cells = row.getTableCells();  
	            for (XWPFTableCell cell : cells) {  
	                paras = cell.getParagraphs();  
	                for (XWPFParagraph para : paras) {  
	                   replaceInPara(para, params);  
	                }  
	            }  
	         }  
	      }  
	   } 
	   /** 
	    * 替换段落里面的变量 
	    * @param para 要替换的段落 
	    * @param params 参数 
	    */  
	   private static void replaceInPara(XWPFParagraph para, Map<String, Object> params) {  
	      List<XWPFRun> runs;  
	      Matcher matcher;  
	      if (matcher(para.getParagraphText()).find()) {  
	         runs = para.getRuns();  
	         for (int i=0; i<runs.size(); i++) {  
	            XWPFRun run = runs.get(i);  
	            String runText = run.toString();  
	            matcher = matcher(runText);  
	            if (matcher.find()) {  
	                while ((matcher = matcher(runText)).find()) {  
	                   runText = matcher.replaceFirst(String.valueOf(params.get(matcher.group(1))==null?"":params.get(matcher.group(1))));  
	                }  
	                //直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，  
	                //所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。  
	                para.removeRun(i);  
	                XWPFRun newrun = para.insertNewRun(i);
	                String [] runTextArr = runText.split(NewLine);
	                for(int j=0;j<runTextArr.length;j++){
	                	if(runTextArr.length == 1){
	                		newrun.setFontSize(14);
		                	newrun.setText(runTextArr[j]);
	                	}else{
	                		if(runTextArr.length != 1 && j==runTextArr.length-1){
	                			newrun.setFontSize(14);
			                	newrun.setText(runTextArr[j]);
	                		}else{
	                			newrun.setFontSize(14);
			                	newrun.setText(runTextArr[j]);
				                newrun.addBreak();
	                		}
	                	}
	                }
	            }  
	         }  
	      }  
	   }
	   /** 
	    * 正则匹配字符串 
	    * @param str 
	    * @return 
	    */  
	   private static Matcher matcher(String str) {  
	      Pattern pattern = Pattern.compile("\\"+mark+"\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);  
	      Matcher matcher = pattern.matcher(str);  
	      return matcher;  
	   }
	   /** 
	    * 关闭输入流 
	    * @param is 
	    */  
	   public static void close(InputStream is) {  
	      if (is != null) {  
	         try {  
	            is.close();  
	         } catch (IOException e) {  
	            e.printStackTrace();  
	         }  
	      }  
	   }  
	    
	   /** 
	    * 关闭输出流 
	    * @param os 
	    */  
	   public static void close(OutputStream os) {  
	      if (os != null) {  
	         try {  
	            os.close();  
	         } catch (IOException e) {  
	            e.printStackTrace();  
	         }  
	      }  
	   }
}
