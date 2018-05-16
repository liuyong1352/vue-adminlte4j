package com.vue.adminlte4j.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by bjliuyong on 2018/3/7.
 */
public class ReflectUtils {

    private static Set<Class> supportedType = new HashSet<>();

    static {
        supportedType.add(Boolean.class) ;
        supportedType.add(Byte.class) ;
        supportedType.add(Short.class) ;
        supportedType.add(Integer.class) ;
        supportedType.add(Float.class) ;
        supportedType.add(Double.class) ;
        supportedType.add(Long.class) ;
        supportedType.add(String.class) ;
        supportedType.add(Date.class) ;

    }

    public static List<Field> findAllField(Class<?> clazz) {

        List<Field> fieldList = new ArrayList<>();

        Class<?> searchType = clazz;
        while (!Object.class.equals(searchType) && searchType != null) {
            Field[] fields = searchType.getDeclaredFields();
            for (Field field : fields) {
                fieldList.add(field);
            }
            searchType = searchType.getSuperclass();
        }
        return fieldList;
    }

    public static boolean isPrimitiveOrString(Class clsType ) {
        return  clsType.isPrimitive() || supportedType.contains(clsType) ;
    }

    public static boolean isDateOrTime(Class clsType) {
        return  clsType.getSimpleName().equals("Date") ;
    }

    public static Object getValue(Field field , Object target) {
        if (!field.isAccessible())
            field.setAccessible(true );
        try {
            return  field.get(target) ;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e) ;
        }
    }

    public static void setValue(Field field , Object target , Object val) {
        if (!field.isAccessible())
            field.setAccessible(true );
        try {
            field.set(target ,val);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e) ;
        }
    }

    public static Object staticInvoke(Method method , Object ... args) {
        try {
            return method.invoke(null , args) ;
        } catch (Exception e) {
            throw new RuntimeException(e) ;
        }
    }
}
