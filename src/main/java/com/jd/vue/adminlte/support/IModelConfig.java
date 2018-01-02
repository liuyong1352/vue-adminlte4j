package com.jd.vue.adminlte.support;

import com.jd.vue.adminlte.model.TableData;
import java.util.List;

/**
 * Created by bjliuyong on 2017/12/26.
 */
public interface IModelConfig {

    List<TableData.Column> configModelColumn(Class type) ;

   /* List<Class> list() ;

    void edit(Class type , TableData.Column column) ;*/

}
