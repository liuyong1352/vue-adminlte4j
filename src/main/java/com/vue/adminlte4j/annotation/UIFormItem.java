package com.vue.adminlte4j.annotation;

import com.vue.adminlte4j.model.form.FormItemType;
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
    String label() default "" ;

    /**
     * 表单项类型
     * @return
     */
    int     type() default FormItemType.INPUT;
    String defVal() default "" ;
    String placeholder() default "" ;

    boolean hidden() default false ;
    boolean ignore() default false ;

    /**
     * 占多宽， 一行总款为12
     * @return
     */
    int     span() default 12 ;

    /**
     * 展示顺序
     * @return
     */
    int    order() default 1 ;

    /**
     * 验证模式
     * @return
     */
    String validate() default "" ;
}
