package com.vue.adminlte4j.model.builder;

import com.vue.adminlte4j.model.form.FormItem;
import com.vue.adminlte4j.model.form.FormItemType;
import com.vue.adminlte4j.model.form.FormModel;
import com.vue.adminlte4j.util.ReflectUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjliuyong on 2018/3/8.
 */
public interface FormModelBuilder<E> {

    FormModelBuilder DEF_BUILDER = new FormModelBuilder() {} ;

    default void configItem(FormModel formModel , FormItem formItem) {

    }

    default void configFormModel(FormModel formModel , E e) {
        //nothing
    }

    default List<FormItem> transform(FormModel formModel , E e ) {
        List<FormItem> formItems = new ArrayList<>() ;

        List<Field> fieldList = ReflectUtils.findAllField(e.getClass());
        fieldList.forEach(field -> {
            FormItem formItem = new FormItem() ;
            formItem.setLabel(field.getName());
            formItem.setKey(field.getName());
            Object val = ReflectUtils.getValue(field , e) ;
            if(val != null )
                formItem.setDefVal(val);
            else
                formItem.setPlaceholder("Enter ... ");
            formItem.setType(FormItemType.INPUT);
            configItem(formModel , formItem) ;
            formItems.add(formItem) ;
        });

        return  formItems ;
    }



}
