package com.vue.adminlte4j.model.form;

import com.vue.adminlte4j.annotation.UIFormItem;

/**
 * Created by bjliuyong on 2018/3/7.
 */
public class FormItem {

    private String label ;
    private String key   ;
    private String defVal ;
    private String placeholder ;
    private int    type ;

    public void config(UIFormItem uiFormItem) {
        if( uiFormItem == null )
            return;
        this.type = uiFormItem.type();

        String key = uiFormItem.key() ;
        if(key != null && !key.isEmpty())
            this.key =key ;
        
        String label = uiFormItem.label() ; 
        if(label != null && !label.isEmpty()) 
            this.label =label ;

        String defVal = uiFormItem.defVal() ;
        if(defVal != null && !defVal.isEmpty())
            this.defVal =defVal ;

        String placeholder = uiFormItem.placeholder() ;
        if(placeholder != null && !placeholder.isEmpty())
            this.placeholder =placeholder ;

    }

    public FormItem clone() {
        FormItem clone = new FormItem() ;
        clone.key = key ;
        clone.label = label ;
        clone.defVal = defVal ;
        clone.placeholder = placeholder ;
        clone.type = type ;
        return clone ;
    }

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

    public String getDefVal() {
        return defVal;
    }

    public void setDefVal(String defVal) {
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
