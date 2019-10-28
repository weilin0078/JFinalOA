package com.pointlion.mvc.common.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfinal.plugin.activerecord.Model;

public class ModelToMapUtil {
	/**
	 * model转换为map
	 * 
	 * @param model
	 * @return
	 * @author: Jerri Liu
	 * @date: 2014年3月30日下午5:17:33
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> ModelToMap(Model model) {
		String jmodel = model.toJson();
		Gson gson = new Gson();
		Map<String, String> jsonmap = gson.fromJson(jmodel,
				new TypeToken<Map<String, String>>() {
				}.getType());
		return jsonmap;
	}
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> ModelToMap2(Model model) {
		String jmodel = model.toJson();
		Gson gson = new Gson();
		Map<String, Object> jsonmap = gson.fromJson(jmodel,
				new TypeToken<Map<String, String>>() {
				}.getType());
		return jsonmap;
	}
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
