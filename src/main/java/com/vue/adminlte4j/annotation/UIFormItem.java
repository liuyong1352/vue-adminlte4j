package com.vue.adminlte4j.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by bjliuyong on 2018/3/8.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UIFormItem {

    String key() default "";
    String lable() default "" ;
    String defVal() default "" ;
    String placeholder() default "" ;
    int     type() default 0 ;
}
