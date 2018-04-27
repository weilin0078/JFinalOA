package com.pointlion.sys.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class ExceptionInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        try{
            inv.invoke();
        }catch (Exception e){
        	e.printStackTrace();
        }
    }


}
