package com.vue.adminlte4j.model.form;

import java.util.List;

/**
 * Created by bjliuyong on 2018/3/6.
 */
public class FormModel  {

    private List<FormItem> formItems ;

    public List<FormItem> getFormItems() {
        return formItems;
    }

    public void setFormItems(List<FormItem> formItems) {
        this.formItems = formItems;
    }

    public static void main(String args[]) {
        //FormModel formModel = build(Menu.class) ;
        System.out.println();
    }

    public FormModel clone() {
        FormModel formModel = new FormModel() ;

        return formModel ;
    }

}
