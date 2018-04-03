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
     * 复选框
     */
    CHECKBOX(4, "checkbox") ,

    /**
     * 开关
     */
    SWITCH(5 , "switch") ,

    /**
     * select
     */
    SELECT(6 , "select") ,

    /**
     * 图标选择器
     */
    ICON_SELECTOR(10 , "") ,

    /**
     * 日期类的
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

    private int key ;
    private String text ;

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
}
