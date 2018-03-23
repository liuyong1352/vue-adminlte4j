package com.vue.adminlte4j.model.builder;

import com.vue.adminlte4j.annotation.Form;
import com.vue.adminlte4j.annotation.UIFormItem;
import com.vue.adminlte4j.annotation.Validate;
import com.vue.adminlte4j.model.form.FormItem;
import com.vue.adminlte4j.model.form.FormModel;
import com.vue.adminlte4j.util.AnnotationUtils;
import com.vue.adminlte4j.util.ReflectUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
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


        FormModel formModel = build(cType) ;


        List<Field> fieldList = ReflectUtils.findAllField(cType);

        /*Collections.sort(fieldList , (f1, f2)->{
            UIFormItem uiFormItem1 , uiFormItem2 ;
            if((uiFormItem1 = AnnotationUtils.findAnnotation(f1 , UIFormItem.class)) == null) {
                return  -1 ;
            } else if ((uiFormItem2 = AnnotationUtils.findAnnotation(f1 , UIFormItem.class)) == null){
                return  1 ;
            } else {
                return uiFormItem1.order() - uiFormItem2.order() ;
            }
        });*/

        for(int i = 0 ; i < fieldList.size() ; i++) {
            Field field = fieldList.get(i) ;
            int mod = field.getModifiers() ;
            //静态的 final修饰的 ， 非基础类型的或字符串类型 不进行处理
            if(Modifier.isFinal(mod) || Modifier.isStatic(mod) || (!ReflectUtils.isPrimitiveOrString(field.getType())))
                continue;
            configFormItem(field ,formModel) ;
        }
        return  formModel ;
    }

    private static FormModel build( Class cType) {
        FormModel formModel = new FormModel() ;
        Form form = (Form) cType.getAnnotation(Form.class) ;
        if(form != null ) {
            formModel.setSpan(form.span());
            formModel.setHidden(form.hidden());
            formModel.setIgnore(form.ignore());
        }
        return formModel ;
    }

    private static void configFormItem(Field field ,FormModel formModel ) {

        UIFormItem uiFormItem = AnnotationUtils.findAnnotation(field , UIFormItem.class) ;
        if( formModel.isIgnore() && uiFormItem == null)
            return;

        if(uiFormItem.ignore())
            return;

        FormItem formItem = new FormItem() ;
        formItem.setLabel(field.getName());
        formItem.setKey(field.getName());
        formItem.setPlaceholder("Enter ... ");
        formItem.setHidden(uiFormItem.hidden());
        formItem.setSpan(formModel.getSpan());

        formItem.config(uiFormItem );
        formItem.configValidate(AnnotationUtils.findAnnotation(field , Validate.class));
        formModel.addFormItem(formItem) ;

    }

}
