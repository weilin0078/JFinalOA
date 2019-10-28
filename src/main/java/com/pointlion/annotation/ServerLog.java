package com.pointlion.annotation;

import com.pointlion.enums.BusinessType;

import java.lang.annotation.*;

/**
 * @Description: ServerLog
 * @Author: liyang
 * @Date: 2019/7/10 0010 16:34
 * @Version 1.0
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServerLog {
    /**
     * 模块
     */
    public String module() default "";

    /**
     * 功能
     */
    public BusinessType businessType() default BusinessType.OTHER;


}
