package com.vue.adminlte4j.annotation;

import com.vue.adminlte4j.model.form.ValidateType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by bjliuyong on 2018/3/22.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Validate {

    /**
     * 验证模式
     * @return
     */
    int type() default ValidateType.REQUIRED;

    long min() default Long.MIN_VALUE ;
    long max() default Long.MAX_VALUE ;

    String pattarn() default "" ;

}
