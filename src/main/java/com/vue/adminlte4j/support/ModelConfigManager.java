package com.vue.adminlte4j.support;

import com.vue.adminlte4j.model.AppInfo;
import com.vue.adminlte4j.model.TableData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bjliuyong on 2017/12/26.
 */
public class ModelConfigManager {

    private static Map<Class , List<TableData.Column>> cache = new HashMap<>();

    public static List<TableData.Column> getModelConfigColumns(Class type) {

        return ModelConfigFactory.getInstance().configModelColumn(type);
        /*List<TableData.Column> columns = cache.get(type);
        if(columns == null ) {
            columns = ModelConfigFactory.getInstance().configModelColumn(type);
            cache.put(type , columns) ;
        }

        return columns  ;*/
    }


    public static AppInfo getAppInfo() {
        return ModelConfigFactory.getInstance().getAppInfo() ;
    }


}
