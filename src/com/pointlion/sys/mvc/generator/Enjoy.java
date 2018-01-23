//package com.pointlion.sys.mvc.generator;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//
//import com.jfinal.kit.Kv;
//import com.jfinal.kit.PathKit;
//import com.jfinal.template.Engine;
//
///***
// * jfinal魔板引擎
// * @author dufuzhong
// */
//public class Enjoy {
//
//    /**
//     * 根据具体魔板生成文件
//     * @param templateFileName  模板文件名称
//     * @param kv                渲染参数
//     * @param filePath          输出目录
//     * @return 
//     */
//    public boolean render(String templateFileName, Kv kv, StringBuilder filePath)  {
//        BufferedWriter output = null;
//        try {
//            String baseTemplatePath = new StringBuilder(PathKit.getRootClassPath())
//            .append("/")
//            .append(PathKit.getPackagePath(this))
//            .append("/tpl")
//            .toString();
//            
//            File file = new File(filePath.toString());
//            
//            File path = new File(file.getParent());
//            if ( ! path.exists() ) {
//                path.mkdirs();
//            }
//            output = new BufferedWriter(new FileWriter(file));
//            
//            Engine.use()
//            .setBaseTemplatePath(baseTemplatePath)
//            //.setSourceFactory(new ClassPathSourceFactory())
//            .getTemplate(templateFileName)
//            .render(kv, output);
//            
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }finally{
//            try { if( output != null ) output.close(); } catch (IOException e) {}
//        }
//    }
//    
//    /**
//     * 根据自定义内容生成文件
//     * @param data              自定义内容
//     * @param filePath          输出目录
//     * @return 
//     */
//    public boolean render(String data, StringBuilder filePath)  {
//        BufferedWriter output = null;
//        try {
//            
//            File file = new File(filePath.toString());
//            
//            File path = new File(file.getParent());
//            if ( ! path.exists() ) {
//                path.mkdirs();
//            }
//            output = new BufferedWriter(new FileWriter(file));
//            
//            output.write(data);
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }finally{
//            try { if( output != null ) output.close(); } catch (IOException e) {}
//        }
//    }
//
//}
