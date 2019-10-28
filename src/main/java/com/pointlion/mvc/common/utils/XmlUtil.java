package com.pointlion.mvc.common.utils;

import com.jfinal.log.Log;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.net.URL;

/**
 * @author 丶Lion
 * @mail 439645473@qq.com
 * @qq 439635374
 * @date 2019/2/28 10:54
 */
public class XmlUtil extends cn.hutool.core.util.XmlUtil {
	private static final Log LOG = Log.getLog(XmlUtil.class);
	
	/***
	 * 读取文件
	 * @param filename
	 * @return
	 */
	public static Document load(String filename) {
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(new File(filename)); // 读取XML文件,获得document对象
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}
	
	/***
	 * 读取文件
	 * @param url
	 * @return
	 */
	public static Document load(URL url) {
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(url); // 读取XML文件,获得document对象
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}
	
	/***
	 * xml写出到文件
	 * @param document
	 * @param filename
	 * @return
	 */
	public static boolean doc2XmlFile(Document document, String filename) {
		boolean flag = true;
		try {
			XMLWriter writer = new XMLWriter(new OutputStreamWriter(
					new FileOutputStream(filename), "UTF-8"));
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		System.out.println(flag);
		return flag;
	}



//	public void writeTo(OutputStream out, String encoding) throws UnsupportedEncodingException, IOException {
//		OutputFormat format = OutputFormat.createPrettyPrint();
//		format.setEncoding("gb2312");
//		XMLWriter writer = new XMLWriter(System.out, format);
//		writer.write(doc);
//		writer.flush();
//		return;
//	}


//	public boolean isOnly(String catNameEn, HttpServletRequest request,String xml) {
//		boolean flag = true;
//		String path = request.getRealPath("");
//		Document doc = load(path + "/" + xml);
//		Element root = doc.getRootElement();
//		for (Iterator i = root.elementIterator(); i.hasNext();) {
//			Element el = (Element) i.next();
//			if (catNameEn.equals(el.elementTextTrim("engName"))) {
//				flag = false;
//				break;
//			}
//		}
//		return flag;
//	}
	
	
	
	public static void main(String args[]) {
		String fileName = "d:/text.xml";
		Document document = DocumentHelper.createDocument();// 建立document对象，用来操作xml文件
		Element booksElement = document.addElement("books");// 建立根节点
		booksElement.addComment("This is a test for dom4j ");// 加入一行注释
		Element bookElement = booksElement.addElement("book");// 添加一个book节点
		bookElement.addAttribute("show", "yes");// 添加属性内容
		Element titleElement = bookElement.addElement("title");// 添加文本节点
		titleElement.setText("ajax in action");// 添加文本内容
		try {
			FileWriter fw = new FileWriter(fileName);
			OutputFormat format = OutputFormat.createPrettyPrint();//缩减型格式
			//OutputFormat format = OutputFormat.createCompactFormat();//紧凑型格式
			format.setEncoding("gb2312");//设置编码
			format.setTrimText(false);//设置text中是否要删除其中多余的空格
			XMLWriter writer = new XMLWriter(fw, format);
			writer.write(document);
			writer.close();
//				<?xml version="1.0" encoding="UTF-8"?>
//				<books>
//					<!--This is a test for dom4j -->
//					<book show="yes">
//						<title>ajax in action</title>
//					</book>
//				</books>
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
