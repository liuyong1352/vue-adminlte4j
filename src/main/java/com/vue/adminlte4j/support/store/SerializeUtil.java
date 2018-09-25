package com.vue.adminlte4j.support.store;

import com.vue.adminlte4j.model.builder.FormModelUtils;
import com.vue.adminlte4j.util.ReflectUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by bjliuyong on 2018/5/28.
 */
public class SerializeUtil {

    private static final String ESCAPE_MARK   = "%25" ;
    private static final String ESCAPE_COMMA  = "%2C" ;

    /**
     * 文本格式 ，逗号分隔序列化
     */
    public static List<String> serialize(Object obj) {

        List<Object> objectList  ;
        if(obj instanceof List) {
            objectList = (List)obj ;
        } else {
            objectList = Arrays.asList(obj) ;
        }

        final List<Field> fieldList = getAllField(objectList.get(0).getClass());

        if(fieldList.isEmpty())
            return Collections.EMPTY_LIST ;

        final StringBuilder headerBuilder = new StringBuilder() ;
        final List<String> lines = new ArrayList<>() ;
        objectList.forEach(e->{
            boolean isEmpty = lines.isEmpty() ;
            StringBuilder valueBuilder = new StringBuilder() ;
            for(Field field : fieldList) {
                if(isEmpty)
                    headerBuilder.append(field.getName()).append(",") ;
                Object val = ReflectUtils.getValue(field , e) ;
                if(val != null ) {
                    if(field.getType().isPrimitive()) {
                        valueBuilder.append(val) ;
                    } else if(ReflectUtils.isDateOrTime(field.getType())) {
                        valueBuilder.append(((Date)val).getTime()) ;
                    } else {
                        valueBuilder.append(encode(val)) ;
                    }
                }

                valueBuilder.append(",") ;
            }
            if(isEmpty) {
                lines.add(headerBuilder.toString());
            }
            lines.add(valueBuilder.toString()) ;
        });

        return lines ;
    }


    /**
     * 反序列
     * @param lines
     * @return
     */
    public static <T> List<T> deSerialize(List<String> lines , Class<T> requiredType) throws Exception {
        List<T> results = new ArrayList<>() ;
        String[] headers = lines.get(0).split(",");
        for(int i = 1 ; i < lines.size() ; i++ ) {
            T o = requiredType.newInstance() ;
            String[] values = lines.get(i).split(",") ;
            for(int j = 0 ; j < headers.length && j < values.length; j++ ) {
                Field field ;
                try {
                    field = requiredType.getDeclaredField(headers[j]);
                } catch (NoSuchFieldException e) {
                    //ignore this field
                    continue;
                }
                if(field.getType().isPrimitive()) {
                    setPrimitiveValue(field , values[j] , o) ;
                } else if(ReflectUtils.isDateOrTime(field.getType()))  {
                    if(values[j].isEmpty())
                        continue;
                    Date newDate = new Date(Long.valueOf(values[j]));
                    ReflectUtils.setValue(field , o , newDate);
                } else {
                    ReflectUtils.setValue(field , o , decode(values[j]));
                }

            }
            results.add(o) ;
        }
        return  results ;
    }

    private static List<Field> getAllField(Class cls) {
        List<Field> fields = ReflectUtils.findAllField(cls) ;
        List<Field> results = new ArrayList<>() ;
        for(Field field : fields) {
            if(FormModelUtils.isConfigurable(field))
                results.add(field) ;
        }
        return results ;
    }





    private static String decode(String value) {
        if(value == null || value.isEmpty())
            return  value ;
        StringBuilder out = new StringBuilder() ;
        char[] chars = value.toCharArray() ;
        for(int i = 0 ; i < chars.length ; i ++ ) {
            if(chars[i] == '%') {
                if(chars[i+2] == '5') {
                    out.append('%') ;
                } else {
                    out.append(',');
                }
                i = i+2;
                continue;
            }
            out.append(chars[i]);
        }
        return out.toString() ;
    }

    private static String encode(Object obj) {
        if(obj == null )
            return  null ;
        String value = obj.toString() ;
        StringBuilder out = new StringBuilder() ;
        char[] chars = value.toCharArray() ;
        for(char c : chars) {
            if(c == '%')
                out.append(ESCAPE_MARK) ;
            else if (c == ',')
                out.append(ESCAPE_COMMA) ;
            else
                out.append(c) ;
        }
        return out.toString() ;
    }

    private  static void setPrimitiveValue(Field field , String val , Object o) {
        String typeName = field.getType().getName() ;
        Object v = 0 ;
        if(typeName.equals("int")  )
            v = Integer.valueOf(val) ;
        else if(typeName.equals("boolean")  )
            v = Boolean.valueOf(val) ;
        else if(typeName.equals("long"))
            v = Long.valueOf(val) ;
        ReflectUtils.setValue(field , o , v);
    }
}
