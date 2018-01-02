package com.vue.adminlte4j.support;

import com.vue.adminlte4j.model.TableData;

import java.util.List;

/**
 * Created by bjliuyong on 2017/12/26.
 */
public interface IModelConfig {

    List<TableData.Column> configModelColumn(Class type) ;

   /* List<Class> list() ;

    void edit(Class type , TableData.Column column) ;*/

}
