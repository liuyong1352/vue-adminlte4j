package com.vue.adminlte4j.support;

import com.vue.adminlte4j.model.AppInfo;
import com.vue.adminlte4j.model.Menu;
import com.vue.adminlte4j.model.TableData;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by bjliuyong on 2017/12/26.
 */
public interface IModelConfig {

    List<TableData.Column> configModelColumn(Class type) ;

    List<Menu> loadMenu();
    AppInfo loadAppInfo() throws IOException;

    void  storeAppInfo(AppInfo  appInfo) throws IOException ;

   /* List<Class> list() ;

    void edit(Class type , TableData.Column column) ;*/

}
