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
    private boolean ignore = false ;

    /**
     * 表单类型是否为inline
     */
    private boolean inline = false ;

    private List<FormItem> formItems = new ArrayList<>();

    /** getter and setter ****/
    public List<FormItem> getFormItems() {
        return formItems;
    }

    public FormItem createFormItem(String key) {
        FormItem formItem = new FormItem() ;
        formItem.setKey(key);
        formItem.setLabel(key);
        formItem.setSpan(span);
        formItem.setHidden(hidden);
        formItems.add(formItem) ;
        return formItem ;
    }

    public FormItem get(String key) {
        for (FormItem formItem : formItems) {
            if(formItem.getKey().equals(key))
                return  formItem ;
        }
        return  null ;
    }

    public List<FormItem> cloneFormItems() {
        List<FormItem> ret = new ArrayList<>() ;
        this.formItems.forEach( item-> ret.add(item.clone()));
        return ret ;
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

    public boolean isInline() {
        return inline;
    }

    public void setInline(boolean inline) {
        this.inline = inline;
    }
}
