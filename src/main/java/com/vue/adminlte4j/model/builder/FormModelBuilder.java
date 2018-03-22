package com.vue.adminlte4j.model.builder;

import com.vue.adminlte4j.model.form.FormModel;
/**
 * Created by bjliuyong on 2018/3/8.
 */
public interface FormModelBuilder {

    /**
     * 配置formModel , 编程式配置formModel
     * @return
     */
    default void config(FormModel formModel){
        //nothing
    }
}
