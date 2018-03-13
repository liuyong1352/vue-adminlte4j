package com.vue.adminlte4j.model.builder;

import com.vue.adminlte4j.model.form.FormItem;
import com.vue.adminlte4j.model.form.FormModel;
/**
 * Created by bjliuyong on 2018/3/8.
 */
public interface FormModelBuilder<E> {

    FormModelBuilder DEF_BUILDER = new FormModelBuilder() {} ;

    default void configItem(FormModel formModel , FormItem formItem) {

    }

    default void configFormModel(FormModel formModel , E e) {
        //nothing
    }
}
