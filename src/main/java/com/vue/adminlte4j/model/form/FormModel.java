package com.vue.adminlte4j.model.form;

import com.vue.adminlte4j.model.Menu;
import com.vue.adminlte4j.util.ReflectUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjliuyong on 2018/3/6.
 */
public class FormModel extends ArrayList<FormItem> {

    public static FormModel build(Class<?> clazz) {
        FormModel formModel = new FormModel() ;
        List<Field> fieldList = ReflectUtils.findAllField(clazz);
        fieldList.forEach(field -> {
            FormItem formItemModel = new FormItem() ;
            formItemModel.setLabel(field.getName());
            formItemModel.setKey(field.getName());
            formItemModel.setDefVal("");
            formItemModel.setType(FormItemType.INPUT);
            formModel.add(formItemModel) ;
        });

        return  formModel ;
    }

    public static FormModel build(Object target) {
        FormModel formModel = new FormModel() ;
        List<Field> fieldList = ReflectUtils.findAllField(target.getClass());
        fieldList.forEach(field -> {
            FormItem formItemModel = new FormItem() ;
            formItemModel.setLabel(field.getName());
            formItemModel.setKey(field.getName());
            Object val = ReflectUtils.getValue(field , target) ;
            if(val != null )
                formItemModel.setDefVal(val);
            else
                formItemModel.setPlaceholder("Enter ... ");
            formItemModel.setType(FormItemType.INPUT);
            formModel.add(formItemModel) ;
        });

        return  formModel ;
    }


    public static void main(String args[]) {
        FormModel formModel = build(Menu.class) ;
        System.out.println(formModel);
    }

}
