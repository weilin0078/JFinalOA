package com.pointlion.mvc.admin.sys.tool.file;

import com.jfinal.kit.StrKit;
import com.pointlion.mvc.common.base.BaseController;

import java.io.File;

/**
 * @author 丶Lion
 * @mail 439645473@qq.com
 * @qq 439635374
 * @date 2019/3/16 10:16
 */
public class FileController extends BaseController {


    /***
     * 批量替换，path文件夹下的名字
     * 将c替换成replace
     */
    public void getReplaceFilesNameToolPage(){
        renderIframe("replaceFilesName.html");
    }

    /***
     * 批量替换，path文件夹下的名字
     * 将c替换成replace
     */
    public void replaceFilesNameTool(){
        String c = getPara("c","");
        String path =  getPara("path","");
        String replace = getPara("replace","");
        if(StrKit.isBlank(path)){
            renderError("文件夹目录不能为空");
            return;
        }
        File f = new File(path);
        if(f.exists()&&f.isDirectory()){
            String[] list = f.list();
            for(String filename :list){
                File file = new File(path + "\\" + filename);
                if(file.isFile()) {
                    String newName = filename.replace(c,replace);
                    if(!filename.equals(newName)){
                        file.renameTo(new File(path+"\\"+newName));
                    }
                }
            }
        }
        renderSuccess();
    }
}
