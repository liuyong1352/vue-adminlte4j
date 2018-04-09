package com.vue.adminlte4j.model.builder;

import com.vue.adminlte4j.model.form.FormItem;
import com.vue.adminlte4j.model.form.FormModel;
/**
 * Created by bjliuyong on 2018/3/8.
 */
public class FormModelBuilder {

    private FormModel formModel ;

    public FormModelBuilder(Class clazz) {
        formModel = new FormModel() ;
        //formModel = FormModelUtils.getFormModel(clazz) ;
    }

    public FormModelBuilder queryForm(boolean isQueryForm) {
        if(isQueryForm) {
            formModel.setSpan(3);
            formModel.setInline(true);
            formModel.setIgnore(false);
            formModel.setHidden(false);
        }
        return this ;
    }

    /*public FormModelBuilder addFormItem(FormItem ) {
        return this ;
    }*/

    public FormModel build() {
        return formModel ;
    }
}
