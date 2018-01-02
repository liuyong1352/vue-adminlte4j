package com.jd.vue.adminlte.support;

import com.jd.vue.adminlte.model.TableData;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by bjliuyong on 2017/12/26.
 */
public class DefaultModelConfig implements IModelConfig{

    private Set<Class> typeSet = new HashSet<>() ;
    private Path storePath  ;

    public DefaultModelConfig() {
        String userDir = System.getProperty("user.dir") ;
        storePath = Paths.get(userDir , "model") ;
    }

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
