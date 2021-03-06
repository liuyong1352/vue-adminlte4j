package com.vue.adminlte4j.model.builder;


import com.vue.adminlte4j.annotation.Form;
import com.vue.adminlte4j.annotation.UIFormItem;
import com.vue.adminlte4j.annotation.Validate;
import com.vue.adminlte4j.model.form.ExtInfo;
import com.vue.adminlte4j.model.form.FormItem;
import com.vue.adminlte4j.model.form.FormItemType;
import com.vue.adminlte4j.model.form.FormModel;
import com.vue.adminlte4j.util.AnnotationUtils;
import com.vue.adminlte4j.util.ReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bjliuyong on 2018/3/8.
 */
public class FormModelUtils {

    private static Map<String , FormModel> formModelCache = new HashMap<>() ;
    private static Map<String , FormModel> configFormModelCache = new HashMap<>() ;

    public static FormModel getFormModel(Class clazz) {
        return getOrCreate(clazz) ;
    }

    public static FormModel getFormModelByFullName(String clsName) {
        try {
            Class cls = Class.forName(clsName , false , ReflectUtils.getDefaultClassLoader()) ;
            return  getFormModel(cls) ;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null ;
    }

    public static FormModel getFormModel(String modelName) {
        return configFormModelCache.get(modelName) ;
    }

    public static void cache(String modelName ,FormModel formModel) {
        configFormModelCache.put(modelName , formModel) ;
    }


    public static void refreshModelCache(String clsName) {
        configFormModelCache.remove(clsName) ;
    }

    private static FormModel getOrCreate(Class clazz) {
        String clsName = clazz.getName() ;
        FormModel formModel = formModelCache.get(clsName) ;
        if(formModel != null )
            return  formModel ;

        try {
            formModel = newFormModel(clazz) ;
            formModelCache.put(clsName , formModel) ;
        } catch (Exception e) {
            throw new RuntimeException(e) ;
        }
        return formModel ;
    }


    public static FormModel newFormModel(Class cType) throws Exception {

        FormModel formModel = new FormModel() ;
        Form form = (Form) cType.getAnnotation(Form.class) ;
        FormModel ref = null ;
        if(form != null ) {
            if(!form.ref().getName().equals("java.lang.Void")) {
                ref = getFormModel(form.ref());
            }
            formModel.setSpan(form.span());
            formModel.setHidden(form.hidden());
            formModel.setIgnore(form.ignore());
            formModel.setInline(form.inline());
        }

        List<Field> fieldList = ReflectUtils.findAllField(cType);

        for(int i = 0 ; i < fieldList.size() ; i++) {
            Field field = fieldList.get(i) ;
            if(isConfigurable(field)) {
                configFormItem(field, formModel ,ref );
            }
        }
        return  formModel ;
    }


    private static FormItem configFormItem(Field field , FormModel formModel , FormModel ref) throws Exception {

        if(formModel.isIgnore() && !AnnotationUtils.hasAnnotation(field,UIFormItem.class)) {
            return null ;
        }
        UIFormItem uiFormItem = AnnotationUtils.findAnnotation(field , UIFormItem.class) ;
        if( uiFormItem!= null && uiFormItem.ignore())
            return null;

        FormItem formItem = formModel.createFormItem(field.getName());
        if(ReflectUtils.isDateOrTime(field.getType())) {
            formItem.setType(FormItemType.DATE);
            formItem.setPlaceholder("yyyy-MM-dd");
        }
        formItem.merge(ref != null ? ref.get(formItem.getKey()) : null) ;
        formItem.config(uiFormItem  );
        formItem.configValidate(AnnotationUtils.findAnnotation(field , Validate.class));
        ExtInfo extInfo = ExtInfo.config(formItem.getType() , field) ;
        if(extInfo != null ) {
            formItem.setExt(extInfo);
        }

        return formItem ;
    }

    /**
     * 非静态，非常量字段，原始类型字符串类型，或者日期类型， 可以进行配置
     * @param field
     * @return
     */
    public static boolean isConfigurable(Field field) {
        int mod = field.getModifiers() ;
        //静态的 final修饰的 ， 非基础类型的或字符串类型 不进行处理
        if(Modifier.isFinal(mod) || Modifier.isStatic(mod))
            return false ;
        Class clsType = field.getType() ;

        return ReflectUtils.isPrimitiveOrString(clsType)
                || ReflectUtils.isDateOrTime(clsType)
                || clsType.isEnum() ;

    }

}
