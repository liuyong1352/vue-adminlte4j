package com.vue.adminlte4j.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Payload;

/**
 * Created by bjliuyong on 2018/3/22.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Form {

    int span() default 12 ;

    boolean ignore() default true ;

    boolean hidden() default true ;

    boolean inline() default  false ;

    /**
     * 引用类的配置信息
     * @return
     */
    Class ref() default Void.class;

}
