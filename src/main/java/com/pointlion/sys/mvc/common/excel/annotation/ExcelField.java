package com.pointlion.sys.mvc.common.excel.annotation;

import java.lang.annotation.*;

/**
 * Created by Jin on 2017/4/10.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelField {

    String value() default "";

    String dateFormat() default "";

    boolean isOnlyImport() default false;

    String isNullDefaultValue() default "N/A";
}
