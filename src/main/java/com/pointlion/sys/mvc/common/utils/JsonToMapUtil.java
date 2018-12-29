package com.pointlion.sys.mvc.common.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonToMapUtil {
	/**
	 * 将json对象转换成Map
	 * 
	 * @param jsonmap
	 * @return
	 * @author: Jerri Liu
	 * @date: 2014年3月30日下午5:17:33
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> jsonToMap(net.sf.json.JSONObject jsonmap) {
		Map<String, String> map = new HashMap<String, String>();
		Iterator<String> iterator = (Iterator<String>) jsonmap.keys();
		String key = null;
		String value = null;
		while (iterator.hasNext()) {
			key = iterator.next();
			try {
				value = jsonmap.getString(key);
			} catch (Exception e) {
				e.printStackTrace();
			}
			map.put(key, value);
		}
		return map;
	}
}
