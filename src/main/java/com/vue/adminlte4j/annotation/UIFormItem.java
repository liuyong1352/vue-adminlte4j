package com.vue.adminlte4j.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by bjliuyong on 2018/3/8.
 */
@Target({ElementType.FIELD ,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface UIFormItem {

    String key() default "";
    String label() default "" ;

    /**
     * 表单项类型
     * @return
     */
    int     type() default -1;
    String defVal() default "" ;
    String placeholder() default "" ;

    boolean disable() default false ;

    boolean hidden() default false ;
    boolean ignore() default false ;

    /**
     * 占多宽， 一行总款为12
     * @return
     */
    int     span() default 0 ;

    /**
     * 展示顺序
     * @return
     */
    int    order() default 1 ;


}
