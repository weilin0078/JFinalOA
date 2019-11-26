package com.pointlion.mvc.common.utils;

import java.util.HashSet;
import java.util.List;

public class ListUtil {
	/***
	 * list去重复
	 * @param list
	 * @return
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List removeDuplicate(List list) {   
	    HashSet h = new HashSet(list);   
	    list.clear();   
	    list.addAll(h);   
	    return list;   
    }

	/***
	 * 是否包含
	 * @param list
	 * @param c
	 * @return
	 */
    public static boolean ifContain(List<String> list,String c){
    	boolean ifContain = false;
    	for(String item:list){
    		if(c.equals(item)){
    			ifContain = true;
    			break;
			}
		}
    	return ifContain;
	}
}
