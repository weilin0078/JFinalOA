package com.pointlion.sys.mvc.common.utils;

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
}
