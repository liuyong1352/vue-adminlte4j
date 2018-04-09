package com.vue.adminlte4j.model;

import com.vue.adminlte4j.model.form.ExtInfo;
import com.vue.adminlte4j.model.form.FormItemType;

/**
 * Created by bjliuyong on 2018/4/9.
 */
public class BaseItem {

    private String key  ;
    private String label ;
    private int  type = FormItemType.INPUT.getKey();
    private ExtInfo extInfo ;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ExtInfo getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(ExtInfo extInfo) {
        this.extInfo = extInfo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
