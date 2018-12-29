/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.sys.mvc.admin.sys.uidemo;

import java.util.HashMap;
import java.util.Map;

import com.pointlion.sys.mvc.common.base.BaseController;


public class UIDemoController extends BaseController {
    

    public void buttons(){
    	setBread("UIDemo",this.getRequest().getServletPath(),"按钮");
    	render("/WEB-INF/admin/uidemo/buttons.html");
    }
    
    public void panels(){
    	setBread("UIDemo",this.getRequest().getServletPath(),"面板");
    	render("/WEB-INF/admin/uidemo/panels.html");
    }
    
    public void lays(){
    	setBread("UIDemo",this.getRequest().getServletPath(),"弹层");
    	render("/WEB-INF/admin/uidemo/lays.html");
    }
    
    public void progressBars(){
    	setBread("UIDemo",this.getRequest().getServletPath(),"进度条");
    	render("/WEB-INF/admin/uidemo/progressBars.html");
    }
    
    public void pages(){
    	setBread("UIDemo",this.getRequest().getServletPath(),"分页");
    	render("/WEB-INF/admin/uidemo/pages.html");
    }
    
    
    public void paragraphs(){
    	setBread("UIDemo",this.getRequest().getServletPath(),"段落");
    	render("/WEB-INF/admin/uidemo/paragraphs.html");
    }
    
    public void lists(){
    	setBread("UIDemo",this.getRequest().getServletPath(),"列表");
    	render("/WEB-INF/admin/uidemo/lists.html");
    }
    
    public void tabs(){
    	setBread("UIDemo",this.getRequest().getServletPath(),"页签");
    	render("/WEB-INF/admin/uidemo/tabs.html");
    }
    
    public void tips(){
    	setBread("UIDemo",this.getRequest().getServletPath(),"tips");
    	render("/WEB-INF/admin/uidemo/tips.html");
    }
    
    public void formGeneral(){
    	setBread("UIDemo",this.getRequest().getServletPath(),"常用表单");
    	render("/WEB-INF/admin/uidemo/formGeneral.html");
    }
    
    public void formComp(){
    	setBread("UIDemo",this.getRequest().getServletPath(),"表单组件");
    	render("/WEB-INF/admin/uidemo/formComp.html");
    }
    
    public void markDown(){
    	setBread("UIDemo",this.getRequest().getServletPath(),"markDown");
    	render("/WEB-INF/admin/uidemo/markDown.html");
    }
    
    public void fileUpload(){
    	setBread("UIDemo",this.getRequest().getServletPath(),"文件上传");
    	render("/WEB-INF/admin/uidemo/fileUpload.html");
    }
    
    public void textEdit(){
    	setBread("UIDemo",this.getRequest().getServletPath(),"富文本");
    	render("/WEB-INF/admin/uidemo/textEdit.html");
    }
    
    public void wizard(){
    	setBread("UIDemo",this.getRequest().getServletPath(),"向导");
    	render("/WEB-INF/admin/uidemo/wizard.html");
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
