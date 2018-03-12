package com.vue.adminlte4j.support;


import com.vue.adminlte4j.model.AppInfo;
import com.vue.adminlte4j.model.TableData;

import java.io.IOException;
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
    }


    public static AppInfo getAppInfo() {
        return ModelConfigFactory.getInstance().loadAppInfo() ;
    }

    public static void storeAppInfo(AppInfo appinfo) throws IOException {
         ModelConfigFactory.getInstance().storeAppInfo(appinfo);

    }



}
