package com.vue.adminlte4j.model;

/**
 * Created by bjliuyong on 2018/3/27.
 */
public class Dict {

    private String key   ;
    private String code  ;
    private String parentCode ;
    private String label ;
    private int order ;

    public static Dict build(String code , String label) {
        Dict dict = new Dict() ;
        dict.code = code ;
        dict.label = label ;
        return  dict ;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
