package com.vue.adminlte4j.support;


import com.vue.adminlte4j.model.AppInfo;
import com.vue.adminlte4j.model.TableData;
import com.vue.adminlte4j.support.store.BaseStore;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by bjliuyong on 2017/12/26.
 */

public class DefaultModelConfig implements IModelConfig ,BaseStore{

    private Set<Class> typeSet   = new HashSet<>() ;

    @Override public List<TableData.Column> configModelColumn(Class type) {

        typeSet.add(type);

        Field[] fields = type.getDeclaredFields() ;

        List<TableData.Column> columns = new ArrayList<>(fields.length) ;

        for(Field field : fields) {
            columns.add(new TableData.Column(field.getName() ,field.getName()));
        }

        return columns;
    }




}
