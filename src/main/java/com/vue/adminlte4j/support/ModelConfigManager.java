package com.vue.adminlte4j.support;


import com.vue.adminlte4j.model.TableData;

import java.util.List;

/**
 * Created by bjliuyong on 2017/12/26.
 */
public class ModelConfigManager {


    public static List<TableData.Column> getModelConfigColumns(Class type) {

        return ModelConfigFactory.getInstance().configModelColumn(type);
    }





}
