package com.vue.adminlte4j.model.form;

import com.vue.adminlte4j.annotation.UIFormItem;
import com.vue.adminlte4j.model.Dict;
import com.vue.adminlte4j.util.Constants;

import java.util.List;

/**
 * Created by bjliuyong on 2018/3/7.
 */
public class FormItem {

    private String      key   ;
    private String      label ;
    private String      defVal ;
    private String      placeholder ;
    private int         type = FormItemType.INPUT;
    private int         span = 12;
    private boolean     hidden ;
    private Validate    validate ;
    private ExtInfo     ext;

    public FormItem(String key , String label) {
        this.key = key ;
        this.label = label ;
    }

    public void merge(FormItem other) {
        if(other == null)
            return;
        this.type           =   other.type       ;
        this.label          =   other.label      ;
        this.defVal         =   other.defVal     ;
        this.placeholder    =   other.placeholder;
        this.ext            =   other.ext        ;
        this.validate       =   other.validate   ;
    }

    public void config(UIFormItem uiFormItem ) {

        if(uiFormItem == null)
            return;

        if(uiFormItem.type() != Constants.DEFAULT )
            this.type =  uiFormItem.type() ;

        this.hidden = uiFormItem.hidden() ;
        if(uiFormItem.span() != 0)
            this.span   = uiFormItem.span() ;

        String key = uiFormItem.key() ;
        if(key != null && !key.isEmpty())
            this.key = key ;

        String label = uiFormItem.label() ;
        if(label != null && !label.isEmpty())
            this.label = label ;

        String defVal = uiFormItem.defVal() ;
        if(defVal != null && !defVal.isEmpty())
            this.defVal = defVal ;

        String placeholder = uiFormItem.placeholder() ;
        if(placeholder != null && !placeholder.isEmpty())
            this.placeholder = placeholder ;
    }

    public void configValidate(com.vue.adminlte4j.annotation.Validate validate) {
        if(validate == null)
            return;
        this.validate = Validate.newValidate(validate.type()) ;
    }

    public FormItem clone() {
        FormItem clone = new FormItem(key ,label) ;
        clone.defVal        = defVal        ;
        clone.placeholder   = placeholder   ;
        clone.type          = type          ;
        clone.span          = span          ;
        clone.hidden        = hidden        ;
        clone.validate      = validate      ;
        clone.ext           = ext           ;
        return clone ;
    }

    public FormItem setDict(List<Dict> dictList) {
        this.ext = ExtInfo.newDictExt(dictList) ;
        return this ;
    }

    public boolean hasDict() {
        return  FormItemType.hasDict(type) ;
    }

    public String getLabel() {
        return label;
    }

    public FormItem setLabel(String label) {
        this.label = label;
        return this ;
    }

    public String getKey() {
        return key;
    }

    public FormItem setKey(String key) {
        this.key = key;
        return this ;
    }

    public String getDefVal() {
        return defVal;
    }

    public FormItem setDefVal(String defVal) {
        this.defVal = defVal;
        return this ;
    }

    public int getType() {
        return type;
    }

    public FormItem setType(int type) {
        this.type = type;
        return this ;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public FormItem setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        return this ;
    }

    public boolean isHidden() {
        return hidden;
    }

    public FormItem setHidden(boolean hidden) {
        this.hidden = hidden;
        return this ;
    }

    public Validate getValidate() {
        return validate;
    }

    public FormItem setValidate(Validate validate) {
        this.validate = validate;
        return this ;
    }

    public int getSpan() {
        return span;
    }

    public FormItem setSpan(int span) {
        this.span = span;
        return this ;
    }

    public ExtInfo getExt() {
        return ext;
    }

    public FormItem setExt(ExtInfo ext) {
        this.ext = ext;
        return this ;
    }
}

