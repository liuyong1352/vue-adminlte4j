package com.vue.adminlte4j.model.form;

import com.vue.adminlte4j.annotation.UIDate;
import java.util.HashMap;

/**
 * Created by bjliuyong on 2018/3/26.
 */
public class ExtInfo extends HashMap<String,Object> {

    public static final String FORMAT = "format" ;
    public static final String TYPE  = "type" ;
    public static final String RANGE = "range" ;

    public void setDateInfo(UIDate uiDate){

        if(uiDate == null)
            return;

        put(TYPE , uiDate.type().ordinal());
        put(FORMAT , uiDate.format());
        put(RANGE , uiDate.range());

    }

}
