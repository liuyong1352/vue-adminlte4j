package com.vue.adminlte4j.util;

import com.vue.adminlte4j.annotation.DictProvider;
import com.vue.adminlte4j.model.Dict;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * Created by bjliuyong on 2018/8/6.
 */
public class DictUtils {

    public static List<Dict> parseDictProvider(Field field) throws Exception {
        DictProvider dictProvider = AnnotationUtils.findAnnotation(field , DictProvider.class) ;

        if(dictProvider == null)
            return null;

        return parseDictProvider(field.getName() , dictProvider.type() , dictProvider.method()) ;

    }

    public static List<Dict> parseDictProvider(String key, String providerCls  , String method) throws Exception {
        return  parseDictProvider(key , Class.forName(providerCls) , method);
    }

    public static List<Dict> parseDictProvider(String key, Class providerCls  , String method) throws Exception {

        Method mtd ;
        Object obj ;
        boolean f = false ;
        try {
            mtd = providerCls.getMethod(method);
        } catch (NoSuchMethodException e) {
            mtd = providerCls.getMethod(method , String.class);
            f = true ;
        }

        if(Modifier.isStatic(mtd.getModifiers()))
            obj = null ;
        else
            obj = providerCls.newInstance() ;

        if(f)
            return (List<Dict>) mtd.invoke(obj , key) ;

        return (List<Dict>) mtd.invoke(obj) ;
    }
}
