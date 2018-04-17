package com.vue.adminlte4j.model.form;

/**
 * Created by bjliuyong on 2018/4/17.
 */
public final class FormItemType {

    public static final int DEFAULT         = 0;
    public static final int INPUT           = 1;
    public static final int RADIO           = 3;
    public static final int CHECKBOX        = 4;
    public static final int SWITCH          = 5;
    public static final int SELECT          = 6;
    public static final int ICON_SELECTOR   = 10;
    public static final int DATE            = 12;

    /**
     * 判断是否需要配置字典拓展
     * @param type
     * @return
     */
    public static boolean hasDict(int type) {
        return type == FormItemType.CHECKBOX ||
            type ==    FormItemType.RADIO ||
            type ==    FormItemType.SWITCH ||
            type ==    FormItemType.SELECT ;
    }

}
