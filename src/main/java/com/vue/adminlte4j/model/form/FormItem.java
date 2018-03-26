package com.vue.adminlte4j.model.form;

import com.vue.adminlte4j.annotation.UIDate;
import com.vue.adminlte4j.annotation.UIFormItem;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bjliuyong on 2018/3/7.
 */
public class FormItem {

    private String label ;
    private String key   ;
    private String defVal ;
    private String placeholder ;
    private int    type = FormItemType.INPUT;
    private int     span = 12;
    private boolean hidden ;
    private Validate validate ;
    private ExtInfo ext;

    public void config(UIFormItem uiFormItem) {

        if(uiFormItem == null)
            return;

        if(uiFormItem.type() >= 0 )
            this.type =  uiFormItem.type() ;

        this.hidden = uiFormItem.hidden() ;
        if(uiFormItem.span() != 0)
            this.span   = uiFormItem.span() ;

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

    public void configDate(UIDate uiDate) {
        ext = new ExtInfo() ;
        ext.setDateInfo(uiDate);
    }

    public void configValidate(com.vue.adminlte4j.annotation.Validate validate) {
        if(validate == null)
            return;
        this.validate = new Validate() ;
        this.validate.setType(validate.type());
    }

    public FormItem clone() {
        FormItem clone = new FormItem() ;
        clone.key           = key               ;
        clone.label         = label             ;
        clone.defVal        = defVal            ;
        clone.placeholder   = placeholder       ;
        clone.type          = type              ;
        clone.validate      = validate          ;
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

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public Validate getValidate() {
        return validate;
    }

    public void setValidate(Validate validate) {
        this.validate = validate;
    }

    public int getSpan() {
        return span;
    }

    public void setSpan(int span) {
        this.span = span;
    }

    public ExtInfo getExt() {
        return ext;
    }

    public void setExt(ExtInfo ext) {
        this.ext = ext;
    }
}
