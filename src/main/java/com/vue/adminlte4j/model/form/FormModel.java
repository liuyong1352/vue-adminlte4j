package com.vue.adminlte4j.model.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bjliuyong on 2018/3/6.
 */
public class FormModel extends ArrayList {

    public FormModel addField(String key , String defVal , int type ) {
        Map map = new HashMap() ;
        map.put("key" , key) ;
        map.put("value" , defVal) ;
        map.put("type" , type) ;
        add(map);
        return this ;
    }

}
