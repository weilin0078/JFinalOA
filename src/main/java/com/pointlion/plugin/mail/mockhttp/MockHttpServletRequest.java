package com.pointlion.plugin.mail.mockhttp;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by farmer on 16/8/31.
 *
 * @author 1256810099@qq.com
 */
public class MockHttpServletRequest implements InvocationHandler {

	@SuppressWarnings("rawtypes")
	private Map dataMap =  new HashMap();
	
    @SuppressWarnings("unchecked")
	public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if ("getAttributeNames".equals(method.getName())) {
            return Collections.enumeration(dataMap.keySet());
        }
        else if ("setAttribute".equals(method.getName())) {
            return dataMap.put(objects[0],objects[1]);
        }
        else if ("getAttribute".equals(method.getName())) {
            return dataMap.get(objects[0]);
        }
        return null;
    }
}