package com.pointlion.mvc.common.utils.office.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * word 转换成html
 */
public class WordToHtml {
    public static void main(String[] args) throws IOException {
    	Word2007ToHtml("d:/Contract.docx");
	}
    /**
     * 2007版本word转换成html
     * @throws IOException
     */
    public static Boolean Word2007ToHtml(String wordPath) throws IOException {
    	if (wordPath == null) {// word文档路径为空
            return false;
        }
        final File wordFile = new File(wordPath).getAbsoluteFile(), htmlFile = new File(wordFile.getParent() + "\\1.html");
        try {
            final InputStream inputStream = new FileInputStream(wordFile);// 输入流
            final XWPFDocument document = new XWPFDocument(inputStream);// 读取word文档
            inputStream.close();// 关闭输入流
            final XHTMLOptions options = XHTMLOptions.create();// 创建选项
//            options.setImageManager(new ImageManager(wordFile.getParentFile(), "PoiImages"));// 设置图片文件夹保存的路径以及文件夹名称
            final OutputStream outputStream = new FileOutputStream(htmlFile);// 输出流
            XHTMLConverter.getInstance().convert(document, outputStream, options);// word文档转html
            System.out.println("html:" + htmlFile.getAbsolutePath());
            outputStream.close();// 关闭输出流
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }  
}