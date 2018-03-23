package com.vue.adminlte4j.model.form;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjliuyong on 2018/3/6.
 */
public class FormModel  {

    /**
     * 默认 表单子项， 占的宽度
     */
    private int span = 6 ;

    /**
     * 在没有配置UIFormItem, hidden取此处全局配置
     */
    private boolean hidden = true ;

    /**
     * 在没有配置UIFormItem, ignore采用此处全局配置
     */
    private boolean ignore = true ;

    private List<FormItem> formItems = new ArrayList<>();

    /** getter and setter ****/
    public List<FormItem> getFormItems() {
        return formItems;
    }

    public FormModel addFormItem(FormItem formItem) {
        formItems.add(formItem) ;
        return this ;
    }

    public FormModel clone() {
        FormModel formModel = new FormModel() ;

        return formModel ;
    }

    public int getSpan() {
        return span;
    }

    public void setSpan(int span) {
        this.span = span;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }
}
