package com.vue.adminlte4j.model.form;

import java.util.HashMap;

/**
 * Created by bjliuyong on 2018/3/22.
 */
public class Validate extends HashMap {

    private static final String TYPE = "type" ;
    private static final String MIN = "min" ;
    private static final String MAX = "max" ;
    private static final String SIZE = "size" ;

    public void setType(int type) {
        put(TYPE , type) ;
    }
}
