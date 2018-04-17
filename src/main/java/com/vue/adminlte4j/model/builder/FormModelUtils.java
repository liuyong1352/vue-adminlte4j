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

    private static FormModel getOrCreate(Class clazz) {
        FormModel formModel = formModelCache.get(clazz) ;
        if(formModel == null ) {
            formModel = newFormModel(clazz) ;
            formModelCache.put(clazz , formModel) ;
        }
        return formModel ;
    }

    public static FormModel getQueryModel(Class clazz) {
        FormModel formModel = new FormModel() ;
        return formModel ;
    }



    private static FormModel newFormModel(Class cType) {

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
            if(isConfigurable(field)) {
               configFormItem(field, formModel ,ref );
            }
        }
        return  formModel ;
    }


    private static FormItem configFormItem(Field field ,FormModel formModel ,FormModel ref) {

        if(formModel.isIgnore() && !AnnotationUtils.hasAnnotation(field,UIFormItem.class)) {
            return null ;
        }
        UIFormItem uiFormItem = AnnotationUtils.findAnnotation(field , UIFormItem.class) ;
        if( uiFormItem!= null && uiFormItem.ignore())
            return null;

        FormItem formItem = new FormItem() ;
        formItem.setLabel(field.getName());
        formItem.setKey(field.getName());
        formItem.setSpan(formModel.getSpan());

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
        formModel.addFormItem(formItem) ;
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
        if(!(ReflectUtils.isPrimitiveOrString(field.getType()) || ReflectUtils.isDateOrTime(field.getType())))
            return  false ;
        return true ;
    }

}
