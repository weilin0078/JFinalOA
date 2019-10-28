package com.pointlion.plugin.mail.mockhttp;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by farmer on 16/8/31.
 *
 * @author 1256810099@qq.com
 */
public class MockHttpServletResponse implements InvocationHandler {

	
	private PrintWriter printWriter =  new MockPrintWriter(new StringWriter());
	
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if ("getWriter".equals(method.getName())) {
            return printWriter;
        }
        return null;
    }
    
}