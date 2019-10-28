package com.pointlion.mvc.admin.oa.workflow;

import java.lang.reflect.Field;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.pointlion.mvc.common.model.SysDct;

public class WorkFlowUtil {
	/***
	 * 根据流程定义获取流程名称
	 * 模版引擎中也用到了，修改时候注意！！！！！！！
	 * @param defKey
	 */
	public static String getDefNameByDefKey(String defKey){
		Record r= Db.findFirst("select d.* from act_re_procdef d,act_re_deployment p where d.DEPLOYMENT_ID_=p.ID_ AND d.KEY_='"+defKey+"' ORDER BY p.DEPLOY_TIME_ DESC");
		if(r==null){
			return "";
		}else{
			return r.getStr("NAME_");
		}
	}
	
	/***
	 * 根据流程定义获取对应的classname
	 * @param defkey
	 * @return
	 */
	public static String getClassFullNameByDefKey(String defkey){
		String result = "";
		SysDct dct = SysDct.dao.getByKeyAndType(defkey, "FLOW_DEFKEY_TO_MODELNAME");
		if(dct!=null){
			result = "com.pointlion.mvc.common.model."+dct.getValue();
			return result;
		}else{
			throw new RuntimeException("根据流程定义Defkey没有找到对应的model，请往系统字典中添加model表单与流程定义Defkey的对应关系!");
		}
	}
	
	/***
	 * 根据model名称获取流程的defkey
	 * @param modelName
	 * @return
	 */
	public static String getDefkeyByModelName(String modelName){
		String result = "";
		SysDct dct = SysDct.dao.getByValueAndType(modelName, "FLOW_DEFKEY_TO_MODELNAME");
		if(dct!=null){
			result = dct.getKey();
			return result;
		}else{
			throw new RuntimeException("根据model没有找到对应的流程定义Defkey，请往系统字典中添加model表单与流程定义Defkey的对应关系!");
		}
		
	}
	/***
	 * 根据defkey获取表名
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static String getTablenameByDefkey(String defKey){
		String result="";
		try{
			String classname = getClassFullNameByDefKey(defKey);
			if(StrKit.notBlank(classname)){
				Class<?> clazz = Class.forName(classname);
				Field f = clazz.getField("tableName");
				if(f!=null){
					Object o = f.get(clazz);
					if(o!=null){
						result = o.toString();
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if("".equals(result)){
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!根据流程定义key没有找到对应的table!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
		return result;
	}
	
	/***
	 * 判断是否有该固定流程
	 * @param key
	 * @return
	 */
	public static Boolean ifHaveThisFlow(String key){
		Record r= Db.findFirst("select * from act_re_model r where r.KEY_='"+key+"'");
		if(r!=null){
			return true;
		}else{
			return false;
		}
	}
}
