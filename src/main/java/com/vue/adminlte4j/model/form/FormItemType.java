package com.vue.adminlte4j.model.form;

/**
 * Created by bjliuyong on 2018/3/7.
 */
public enum FormItemType {

    DEFAULT(-1, null) ,


    /**
     * 普通输入
     */
    INPUT(0 , "input") ,

    /**
     * 单选按钮
     */
    RADIO(3,"radio" ),

    /**
     * 复选框
     */
    CHECKBOX(4, "checkbox" ) ,

    /**
     * 开关
     */
    SWITCH(5 , "switch") ,

    /**
     * select
     */
    SELECT(6 , "select" ) ,

    /**
     * 图标选择器
     */
    ICON_SELECTOR(10 , "") ,

    /**
     * 日期时间
     */
    DATE(12 )

    ;


    FormItemType(int key) {
        this.key = key;
    }

    FormItemType(int key, String text) {
        this.key = key;
        this.text = text;
    }

    FormItemType(int key, String text ,int prop) {
        this.key = key;
        this.text = text;
        this.prop = prop;
    }

    private int key ;
    private String text ;
    private int prop ;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getProp() {
        return prop;
    }

    public void setProp(int prop) {
        this.prop = prop;
    }
}
