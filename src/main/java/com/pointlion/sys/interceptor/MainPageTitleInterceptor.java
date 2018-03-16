package com.pointlion.sys.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class MainPageTitleInterceptor implements Interceptor {
	public Map<String,String> pageTitleBread(){
		Map<String,String> pageTitleBread = new HashMap<String, String>();
		pageTitleBread.put("pageTitle", "管理");
		pageTitleBread.put("breadHomeMethod", "");
		return pageTitleBread;
	};
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		Class<? extends Controller> oc = controller.getClass();
		try {
			Method method = oc.getMethod("getPageTitleBread");
			Object o = method.invoke(controller);
			//过滤
			String action = inv.getActionKey();
			Map<String, String> map = (HashMap<String,String>)o;
			String breadHomeMethod = map.get("breadHomeMethod");//首页地址
			if(breadHomeMethod!=null&&action.lastIndexOf(breadHomeMethod)==(action.length()-breadHomeMethod.length())){
				map.put("url", action);
				map.put("nowBread", "管理");
			}else{
				map.put("url", inv.getControllerKey()+"/"+breadHomeMethod);
				map.put("nowBread", "其他");
			}
			controller.setAttr("pageTitleBread", map);//设置标题和面包屑
		} catch (Exception e) {
			controller.setAttr("pageTitleBread", pageTitleBread());
		}
		inv.invoke();
	}

}
