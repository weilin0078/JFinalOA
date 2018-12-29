package com.pointlion.sys.mvc.admin.sys.attachment;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.pointlion.sys.interceptor.MainPageTitleInterceptor;
import com.pointlion.sys.mvc.common.base.BaseController;
import com.pointlion.sys.mvc.common.model.SysAttachment;
import com.pointlion.sys.mvc.common.model.SysOrg;
import com.pointlion.sys.mvc.common.model.SysUser;
import com.pointlion.sys.mvc.common.utils.DateUtil;
import com.pointlion.sys.mvc.common.utils.UuidUtil;
import com.pointlion.sys.plugin.shiro.ShiroKit;

@Before(MainPageTitleInterceptor.class)
public class AttachmentController extends BaseController {
	public static final AttachmentService service = AttachmentService.me;
	/***
	 * 列表页面
	 */
	public void getListPage(){
		setBread("功能名称",this.getRequest().getServletPath(),"管理");
    	render("list.html");
    }
	/***
     * 获取分页数据
     **/
    public void listData(){
    	String curr = getPara("pageNumber");
    	String pageSize = getPara("pageSize");
    	Page<Record> page = service.getPage("",Integer.valueOf(curr),Integer.valueOf(pageSize));
    	renderPage(page.getList(),"",page.getTotalRow());
    }
    
    /***
	 * 业务数据上传附件
	 */
	public void getBusinessUploadListPage(){
		String busid = getPara("busid","");
		String view = getPara("view","");
		setAttr("busid", busid);
		setAttr("view", view);
    	render("businessUploadList.html");
    }
    
	/***
	 * 获取数据列表
	 */
	public void getBusinessUploadList(){
		String curr = getPara("pageNumber");
    	String pageSize = getPara("pageSize");
    	String busid = getPara("busid","");
    	Page<Record> page = service.getPage(busid,Integer.valueOf(curr),Integer.valueOf(pageSize));
    	renderPage(page.getList(),"",page.getTotalRow());
    }
    
	/***
	 * 获取业务附件数量
	 */
	public void getBusinessAttachmentCount(){
		String busid = getPara("busid");
		Integer c = service.getBusinessAttachmentCount(busid);
		renderSuccess(c, "查询成功");
	}

    /***
     * 删除
     * @throws Exception
     */
    public void delete() throws Exception{
		String ids = getPara("ids");
    	//执行删除
		service.deleteByIds(ids);
    	renderSuccess("删除成功!");
    }
    
    /***
	 * 文件上传
	 */
	public void attachmentUpload(){
		String busid = getPara("busid","");
		String moduelFrom = getPara("moduelFrom","notknowfrom");//来源
		String key = getPara("key","notknowkey");
		String path = "/attachment/"+moduelFrom+"/"+key+"/"+DateUtil.getYear()+"/"+DateUtil.getMonth()+"/"+DateUtil.getDay();//保存路径
		UploadFile file = getFile("file",path);
		String savePath = file.getUploadPath();//实际保存的路径
		String bathPath = savePath.replace(path, "");//根路径upload目录
		String filename = file.getOriginalFileName();//文件实际名字 
		String stuf = filename.substring(filename.lastIndexOf(".")+1);//扩展名
		String newUrl = path+"/"+UuidUtil.getUUID()+"."+stuf;//新文件相对路径
		String newRealFileUrl = bathPath+newUrl;//文件实际存储路径
		file.getFile().renameTo(new File(newRealFileUrl));//文件重命名
		SysAttachment attachment = new SysAttachment();
		attachment.setId(UuidUtil.getUUID());
		attachment.setUrl(newUrl);
		attachment.setRealUrl(newRealFileUrl);
		SysUser user = SysUser.dao.getById(ShiroKit.getUserId());
		SysOrg org = SysOrg.dao.getById(user.getOrgid());
		attachment.setCreateUserId(user.getId());
		attachment.setCreateUserName(user.getName());
		attachment.setCreateOrgId(org.getId());
		attachment.setCreateOrgName(org.getName());
		attachment.setCreateTime(DateUtil.getTime());
		attachment.setSuffix(stuf);
		attachment.setFileName(filename);
		attachment.setBusinessId(busid);
		attachment.save();
		renderSuccess("上传成功");
	}
    
	/***
	 * 下载文件
	 */
	public void downloadFile(){
		String id = getPara("id");
		SysAttachment attachment = SysAttachment.dao.getById(id);
		String fileUrl = attachment.getUrl();
		String baseUrl = this.getRequest().getSession().getServletContext().getRealPath("");
		File file = new File(baseUrl+"/upload"+fileUrl);
		renderFile(file);
	}
    /**************************************************************************/
	public void setBread(String name,String url,String nowBread){
		Map<String,String> pageTitleBread = new HashMap<String,String>();
		pageTitleBread.put("pageTitle", name);
		pageTitleBread.put("url", url);
		pageTitleBread.put("nowBread", nowBread);
		this.setAttr("pageTitleBread", pageTitleBread);
	}
}