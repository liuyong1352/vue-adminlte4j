package com.vue.adminlte4j.support.store;

import com.vue.adminlte4j.util.ReflectUtils;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author tangwei
 * @date 2018/9/30 15:35
 */
public class ProtrolSerializeUtil {

    private final static String BASE_PROTOCOL = "VUE-TLE";
    private final static String FULL_PROTOCOL = "VUE-TLE v1.0";


    public static List<String> serialize(Object obj) {
        List<Object> objectList  ;
        if(obj instanceof List) {
            objectList = (List)obj ;
        } else {
            objectList = Arrays.asList(obj) ;
        }

        final List<Field> fieldList = SerializeUtil.getAllField(objectList.get(0).getClass());

        if(fieldList.isEmpty())
            return Collections.EMPTY_LIST ;

        final  StringBuilder headerBuilder = new StringBuilder() ;
        final  List<String> lines = new ArrayList<>() ;

        objectList.forEach(e->{
            boolean isEmpty = lines.isEmpty() ;
            StringBuilder valueBuilder = new StringBuilder() ;
            for(Field field : fieldList) {
                if(isEmpty)
                    headerBuilder.append(field.getName()).append(",") ;
                Object val = ReflectUtils.getValue(field , e) ;
                if(val != null ) {
                    val = val.toString().replaceAll("(\r\n|\r|\n|\n\r)", "\\\\r\\\\n");
                    int length = val.toString().length();
                    valueBuilder.append(length).append(":");
                    if(field.getType().isPrimitive()) {
                        valueBuilder.append(val) ;
                    } else if(ReflectUtils.isDateOrTime(field.getType())) {
                        valueBuilder.append(((Date)val).getTime()) ;
                    } else {
                        valueBuilder.append(val) ;
                    }
                }

                valueBuilder.append(",") ;
            }
            lines.add(FULL_PROTOCOL);  //添加头部分
            if(isEmpty) {
                lines.add(headerBuilder.toString());
            }
            lines.add(valueBuilder.toString()) ;
        });

        return lines ;
    }


    public static <T> List<T> deSerialize(List<String> lines ,Class<T> requiredType)  {
        List<T> results = new ArrayList<>() ;
        String[] protocols = lines.get(0).split(" ");
        //检查协议版本 1 走哪个 二走哪个 版本先略过
        if(!BASE_PROTOCOL.equals(protocols[0])) {
            throw new RuntimeException("协议格式不对");
        }

        String[] headers = lines.get(1).split(",");
        try {
            for(int i = 2 ; i < lines.size() ; i++ ){
                T o = requiredType.newInstance() ;
                String[] values = buildValues(lines.get(i),headers.length);
                for(int j = 0 ; j < headers.length && j < values.length; j++ ) {
                    Field field ;
                    try {
                        field = requiredType.getDeclaredField(headers[j]);
                    } catch (NoSuchFieldException e) {
                        //ignore this field
                        continue;
                    }
                    if(field.getType().isPrimitive()) {
                        SerializeUtil.setPrimitiveValue(field , values[j] , o) ;
                    } else if(ReflectUtils.isDateOrTime(field.getType()))  {
                        if(values[j].isEmpty())
                            continue;
                        Date newDate = new Date(Long.valueOf(values[j]));
                        ReflectUtils.setValue(field , o , newDate);
                    } else {
                        ReflectUtils.setValue(field , o , values[j]);
                    }
                }

                results.add(o) ;
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return results;
    }

    private static String[] buildValues(String line,int sLength){
        //思路 先取:号数字 取得到就读 然后取逗号 下一个 取不到 直接下一个
        String[] results = new String[sLength+1];
        if(line == null || line.length() == 0) {
            return results;
        }
        int resultCount = 0;   //返回结果第几元素
        char[] chars = line.toCharArray();
        int index = 0;   //chars 下标
        StringBuilder temp = new StringBuilder();
        while(index < chars.length){
            int valueLength = chars[index];

            if(valueLength == ','){
                results[resultCount++] = "";
                index++;
            }else if('0' <= valueLength && valueLength <= '9'){
                valueLength = valueLength - '0';
                temp.append(valueLength);
                index++;
            } else if(valueLength == ':'){
                valueLength = Integer.parseInt(temp.toString());
                temp.setLength(0); //最快的清空方法
                index++;
                StringBuilder result = new StringBuilder();
                for (int i = 0;i < valueLength;i++){
                    result.append(chars[index+i]);
                }
                results[resultCount++] = result.toString().replaceAll("\\\\r\\\\n","\r\n");
                index += valueLength+1; //越过逗号
            }
            else {
                throw new RuntimeException("配置格式有误,有参数未配置长度");
            }


        }
        return results;
    }
}
