package com.vue.adminlte4j.support.store.serialize;

import java.util.List;

/**
 * Created by bjliuyong on 2018/10/10.
 */
public interface Serializer {

    /**
     * 判断是否支持， 解码
     * @param data
     * @return
     */
    boolean isSupport(byte[] data) ;

    /**
     * 判断是否支持， 解码
     * @param protoVersion
     * @param data
     * @return
     */
    default boolean isSupport(byte[] protoVersion, byte[] data) {
        for(int i = 0 ; i < protoVersion.length ; i++ ) {
            if(i < data.length && protoVersion[i] == data[i])
                continue;
            return false ;
        }
        return true;
    }

    /**
     * 编码
     * @param obj
     * @return
     */
    byte[] encode(Object obj) ;

    /**
     * 解码
     * @param bytes
     * @return
     */
    <T> List<T> decode(byte[] bytes, Class<T> tCls) ;
}
