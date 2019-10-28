/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.mvc.admin.sys.uidemo;

import java.util.HashMap;
import java.util.Map;

import com.pointlion.mvc.common.base.BaseController;


public class UIDemoController extends BaseController {
    

    public void buttons(){

    	renderIframe("/WEB-INF/admin/uidemo/buttons.html");
    }
    
    public void panels(){

    	renderIframe("/WEB-INF/admin/uidemo/panels.html");
    }
    
    public void lays(){

    	renderIframe("/WEB-INF/admin/uidemo/lays.html");
    }
    
    public void progressBars(){

    	renderIframe("/WEB-INF/admin/uidemo/progressBars.html");
    }
    
    public void pages(){

    	renderIframe("/WEB-INF/admin/uidemo/pages.html");
    }
    
    
    public void paragraphs(){

    	renderIframe("/WEB-INF/admin/uidemo/paragraphs.html");
    }
    
    public void lists(){

    	renderIframe("/WEB-INF/admin/uidemo/lists.html");
    }
    
    public void tabs(){

    	renderIframe("/WEB-INF/admin/uidemo/tabs.html");
    }
    
    public void tips(){

    	renderIframe("/WEB-INF/admin/uidemo/tips.html");
    }
    
    public void formGeneral(){

    	renderIframe("/WEB-INF/admin/uidemo/formGeneral.html");
    }
    
    public void formComp(){

    	renderIframe("/WEB-INF/admin/uidemo/formComp.html");
    }
    
    public void markDown(){

    	renderIframe("/WEB-INF/admin/uidemo/markDown.html");
    }
    
    public void fileUpload(){

    	renderIframe("/WEB-INF/admin/uidemo/fileUpload.html");
    }
    
    public void textEdit(){

    	renderIframe("/WEB-INF/admin/uidemo/textEdit.html");
    }
    
    public void wizard(){

    	renderIframe("/WEB-INF/admin/uidemo/wizard.html");
    }



}
