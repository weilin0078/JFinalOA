package com.pointlion.mvc.admin.sys.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.template.Engine;

/***
 * jfinal魔板引擎
 * @author dufuzhong
 */
public class Enjoy {
	
	static final Integer FAIL = -1;
	static final Integer SUCCESS = 0;
	static final Integer EXIST = 1;

    /**
     * 根据具体模板生成文件
     * @param templateFileName  模板文件名称
     * @param kv                渲染参数
     * @param filePath          输出目录
     * @return 
     * 1：已存在
     * 0：成功
     * -1：失败
     */
    public Integer render(String templateFileName, Kv kv, String filePath)  {
            String baseTemplatePath = new StringBuilder(PathKit.getRootClassPath())
            .append("/")
            .append("/template")
            .toString();
            File file = new File(filePath.toString());
            if(file.exists()){//如果已经存在了
            	return EXIST;
            }
            File path = new File(file.getParent());
            if ( ! path.exists() ) {
                path.mkdirs();
            }
            Engine.use()
            .setBaseTemplatePath(baseTemplatePath)
            //.setSourceFactory(new ClassPathSourceFactory())
            .getTemplate(templateFileName)
            .render(kv, file);
            return SUCCESS;
    }
    
    /**
     * 根据自定义内容生成文件
     * @param data              自定义内容
     * @param filePath          输出目录
     * @return 
     */
    public boolean render(String data, StringBuilder filePath)  {
        BufferedWriter output = null;
        try {
            
            File file = new File(filePath.toString());
            
            File path = new File(file.getParent());
            if ( ! path.exists() ) {
                path.mkdirs();
            }
            output = new BufferedWriter(new FileWriter(file));
            
            output.write(data);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally{
            try { if( output != null ) output.close(); } catch (IOException e) {}
        }
    }

}
