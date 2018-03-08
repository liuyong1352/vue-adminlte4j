package com.vue.adminlte4j.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by bjliuyong on 2018/3/8.
 */
public class AnnotationUtils {

    public static <T> T findAnnotation(Field field ,Class<? extends Annotation> annotationType){
        Annotation[] annotations = field.getAnnotations();

        for (Annotation annotation : annotations){
            if (annotation.annotationType().equals(annotationType)){
                return (T)annotation ;
            }
        }

        return null ;
    }

}
