package com.vue.adminlte4j.annotation;

import com.vue.adminlte4j.model.form.DateType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by bjliuyong on 2018/3/26.
 */
@Target(ElementType.FIELD )
@Retention(RetentionPolicy.RUNTIME)
@UIFormItem
public @interface UIDate {

    /**
     * 日期类型
     * @return
     */
    DateType type() default DateType.DATE_TIME;

    /**
     * 日期格式
     * @return
     */
    String format() default "" ;

    /**
     * 是否范围选择
     * @return
     */
    boolean range() default  false ;

}
