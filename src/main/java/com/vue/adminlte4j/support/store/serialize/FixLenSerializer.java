package com.vue.adminlte4j.support.store.serialize;

import com.vue.adminlte4j.model.builder.FormModelUtils;
import com.vue.adminlte4j.support.store.SerializeUtil;
import com.vue.adminlte4j.util.ReflectUtils;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by bjliuyong on 2018/10/10.
 */
public class FixLenSerializer implements Serializer {

    private final static String PROTO_HEADER = "len_val1.0" ;

    /**
     * 判断是否支持， 解码
     *
     * @param bytes
     * @return
     */
    @Override
    public boolean isSupport(byte[] bytes) {
        byte[] headerBytes = PROTO_HEADER.getBytes(StandardCharsets.UTF_8) ;
        return isSupport(headerBytes , bytes);
    }

    /**
     * 编码
     *
     * @param obj
     * @return
     */
    @Override
    public byte[] encode(Object obj) {
        Objects.requireNonNull(obj , "encode obj can not be null !") ;
        List<Object> objList = obj instanceof List ? (List)obj : Arrays.asList(obj)  ;
        Object first = objList.get(0) ;

        List<Field> fields = new ArrayList<>() ;

        ReflectUtils.traverse(first.getClass() , f -> {
            if(FormModelUtils.isConfigurable(f)) {
                fields.add(f) ;
            }
        });

        if(fields.isEmpty())
            return new byte[0] ;

        StringBuilder sb = new StringBuilder() ;
        sb.append(PROTO_HEADER + "\n") ;
        for(Field field : fields) {
            sb.append(field.getName()).append(",") ;
        }
        sb.append('\n') ;

        for(Object e : objList ) {
            List<LenValue> lenValues = converter(e ,fields) ;
            for(LenValue lenValue : lenValues) {
                sb.append(lenValue.toString()) ;
            }
            sb.append('\n') ;
        }
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    private List<LenValue> converter(Object e , List<Field> fields) {
        List<LenValue> lenValue = new ArrayList<>() ;

        for(Field field : fields) {
            Object val = ReflectUtils.getValue(field , e) ;
            if(val == null ) {
                lenValue.add(new LenValue("")) ;
                continue;
            }

            if(ReflectUtils.isDateOrTime(field.getType())) {
                String s = ((Date)val).getTime() + "";
                lenValue.add(new LenValue(s)) ;
            } else {
                String s = val.toString() ;
                lenValue.add(new LenValue(s)) ;
            }

        }
        return  lenValue ;
    }

    /**
     * 解码
     *
     * @param bytes
     * @return
     */
    @Override
    public  <T> List<T> decode(byte[] bytes , Class<T> tClass) {
        Objects.requireNonNull(bytes) ;
        String s = new String(bytes , StandardCharsets.UTF_8) ;
        if(!s.startsWith(PROTO_HEADER))
            throw new IllegalStateException("version is not consistence!") ;
        List<Field> header = new ArrayList<>() ;
        int len = s.length() ;
        int i = PROTO_HEADER.length() + 1 ;
        if(s.charAt(i) == '\n')
            i++ ;
        //first get field name
        int start = i ;
        for(; i < len ; i++ ) {
            char c = s.charAt(i) ;
            if(c == ',') {
                String fieldName = s.substring(start , i) ;
                try {
                    Field field = tClass.getDeclaredField(fieldName);
                    header.add(field) ;
                } catch (NoSuchFieldException e) {
                    header.add(null) ;
                }
                start = i + 1 ;
            } else if(c == '\n') {
                i++ ;
                break;
            }

        }
        start = i ;


        List<T> objs = new ArrayList<>() ;
        int fieldSize = header.size() ;
        int parseCount = 0 ;
        T target = null ;
        //second parse value
        for(; i < len ; ) {

            char c = s.charAt(i) ;
            if(c == ':') {
                String lenStr = s.substring(start , i) ;
                int l = Integer.valueOf(lenStr) ;
                i++ ; //skip :
                String val = null ;
                if(l != 0 ) {
                    val = s.substring(i , i+l) ;
                    i += l ; //skip len
                }
                i++ ;  //skip comma
                start = i ;
                //init object
                if(parseCount == 0 )
                    target = ReflectUtils.newInstance(tClass) ;
                set(target ,header.get(parseCount) ,val);
                parseCount++ ;
                //解析完一行
                if(parseCount == fieldSize) {
                    parseCount = 0 ;
                    c = s.charAt(i) ;
                    i++ ; //skip \r or \n
                    if(c == '\r' ) {
                        i++ ; //skip \n
                    }
                    objs.add(target) ;
                }
            } else {
                i++ ;
            }
        }
        return objs;
    }

    private static void set(Object target , Field field , String value) {
        //the field already not exist
        if(field == null || value == null)
            return;

        if(field.getType().isPrimitive()) {
            SerializeUtil.setPrimitiveValue(field , value , target) ;
        } else if(ReflectUtils.isDateOrTime(field.getType()))  {
            Date newDate = new Date(Long.valueOf(value));
            ReflectUtils.setValue(field , target , newDate);
        } else {
            ReflectUtils.setValue(field , target , value);
        }

    }

    static class LenValue {

        int len ;
        String v  ;

        LenValue(String v) {
            this.len = v == null ? 0 : v.length();
            this.v = v;
        }

        @Override
        public String toString() {
            return len + ":" + v + ",";
        }
    }
}
