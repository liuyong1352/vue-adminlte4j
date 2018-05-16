package com.vue.adminlte4j.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by bjliuyong on 2018/4/16.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DictProvider {

    /**
     * 数据字典 ，提供类
     * @return
     */
    Class type() ;

    /**
     * 调用此方法获取数据字典
     * @return
     */
    String method() default "" ;

}
