package com.pointlion.mvc.common.utils.office.word;
 
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.lowagie.text.pdf.BaseFont;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
 





import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
 
/**
 * Created by Carey on 15-2-2.
 */
public class HtmlToPdf {
 
 
    public boolean convertHtmlToPdf(String inputFile, String outputFile)
            throws Exception {
 
        OutputStream os = new FileOutputStream(outputFile);
        ITextRenderer renderer = new ITextRenderer();
        String url = new File(inputFile).toURI().toURL().toString();
        renderer.setDocument(url);
        // 解决中文支持问题
        ITextFontResolver fontResolver = renderer.getFontResolver();
        fontResolver.addFont("C:/Windows/Fonts/simsunb.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        //解决图片的相对路径问题
        renderer.getSharedContext().setBaseURL("file:/D:/test");
        renderer.layout();
        renderer.createPDF(os);
        os.flush();
        os.close();
        return true;
    }
 
    public static final String HTML = "D:/1.html";
 
//     public   static  void  main(String [] args){
//    	 HtmlToPdf html2Pdf =new HtmlToPdf();
//         try {
//             html2Pdf.convertHtmlToPdf("D:\\1.html","D:\\index.pdf");
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
     
     public static void main(String[] args) {
         try {
             
             Document document = new Document(PageSize.LETTER);
             PdfWriter pdfWriter = PdfWriter.getInstance(document,
                     new FileOutputStream("d://testpdf.pdf"));
             document.open();
             document.addAuthor("test");
             document.addCreator("test");
             document.addSubject("test");
             document.addCreationDate();
             document.addTitle("XHTML to PDF");

             XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
             worker.parseXHtml(pdfWriter, document, new FileInputStream(HTML));
             document.close();
             System.out.println("Done.");
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
}
