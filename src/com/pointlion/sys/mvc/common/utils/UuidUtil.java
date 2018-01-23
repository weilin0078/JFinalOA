package com.pointlion.sys.mvc.common.utils;

import java.util.UUID;

public class UuidUtil {
	public static String getUUID(){
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		str = str.replaceAll("-", "");
		return str;
	}
}
