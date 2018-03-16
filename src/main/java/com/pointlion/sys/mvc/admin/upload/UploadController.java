/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.sys.mvc.admin.upload;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.upload.UploadFile;
import com.pointlion.sys.mvc.common.base.BaseController;

/***
 * 通知公告控制器（web）
 * @author Administrator
 *
 */
public class UploadController extends BaseController {
	
	/***
	 * 文件上传
	 */
	public void upload(){
		UploadFile file = getFile("file","/content");
		Map<String,String> data = new HashMap<String , String>();
		data.put("filename", file.getFileName());
		data.put("path", "/content");
		renderSuccess(data,"上传成功");
	}
}
