package com.vue.adminlte4j.model.builder;

import com.vue.adminlte4j.annotation.UIFormItem;
import com.vue.adminlte4j.model.form.FormItem;
import com.vue.adminlte4j.model.form.FormModel;
import com.vue.adminlte4j.util.AnnotationUtils;
import com.vue.adminlte4j.util.ReflectUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bjliuyong on 2018/3/8.
 */
public class FormModelUtils {

    private static Map<Class , FormModel> formModelCache = new HashMap<>() ;

    public static Map<Class , FormModel> getFormModelCache() {
        return formModelCache ;
    }

    public static FormModel getFormModel(Object object) {
        return getOrCreate(object.getClass()) ;
    }

    public static FormModel getFormModel(Class clazz) {
        return getOrCreate(clazz) ;
    }

    private   static FormModel getOrCreate(Class clazz) {
        FormModel formModel = formModelCache.get(clazz) ;
        if(formModel == null ) {
            formModel = newFormModel(clazz) ;
            formModelCache.put(clazz , formModel) ;
        }
        return formModel ;
    }

    private static FormModel newFormModel(Class cType) {
        FormModel formModel = new FormModel() ;

        List<FormItem> formItems = new ArrayList<>() ;
        formModel.setFormItems(formItems);

        List<Field> fieldList = ReflectUtils.findAllField(cType);
        fieldList.forEach(field -> {
            int mod = field.getModifiers() ;
            if(!Modifier.isFinal(mod) && !Modifier.isStatic(mod))
                formItems.add(configFormItem(field)) ;
        });

        return  formModel ;
    }

    private static FormItem configFormItem(Field field ) {

        FormItem formItem = new FormItem() ;
        formItem.setLabel(field.getName());
        formItem.setKey(field.getName());
        formItem.setPlaceholder("Enter ... ");

        formItem.config(AnnotationUtils.findAnnotation(field , UIFormItem.class));

        return formItem ;
    }

}
