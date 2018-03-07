package com.vue.adminlte4j.model.form;

/**
 * Created by bjliuyong on 2018/3/7.
 */
public class FormItem {

    private String label ;
    private String key   ;
    private Object defVal ;
    private String placeholder ;
    private int    type ;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getDefVal() {
        return defVal;
    }

    public void setDefVal(Object defVal) {
        this.defVal = defVal;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }
}
