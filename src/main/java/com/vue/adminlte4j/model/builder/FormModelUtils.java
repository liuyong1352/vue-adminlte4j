package com.vue.adminlte4j.model.builder;

import com.vue.adminlte4j.annotation.UIFormItem;
import com.vue.adminlte4j.model.form.FormItem;
import com.vue.adminlte4j.model.form.FormModel;
import com.vue.adminlte4j.util.AnnotationUtils;
import com.vue.adminlte4j.util.ReflectUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bjliuyong on 2018/3/8.
 */
public class FormModelUtils {

    private static Map<Class , FormModel> formModelCache = new HashMap<>() ;

    public static FormModel transform(Object e , FormModelBuilder formModelBuilder) {
        FormModel model = getOrCreate(e.getClass()) ;
        FormModel clone = model.clone() ;

        List<FormItem> formItems = new ArrayList<>() ;
        clone.setFormItems(formItems);

        model.getFormItems().forEach(formItem -> {
            FormItem itemClone = formItem.clone() ;

            formModelBuilder.configItem(clone , itemClone);
            formItems.add(itemClone) ;
        });

        return clone ;
    }

    private  static FormModel getOrCreate(Class clazz) {
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
            FormItem formItem = new FormItem() ;
            formItem.setLabel(field.getName());
            formItem.setKey(field.getName());
            formItem.setPlaceholder("Enter ... ");
            UIFormItem uiFormItem = AnnotationUtils.findAnnotation(field , UIFormItem.class) ;
            if(uiFormItem != null ) {

            }

            //configItem(formModel , formItem) ;
            formItems.add(formItem) ;
        });

        return  formModel ;
    }
}
