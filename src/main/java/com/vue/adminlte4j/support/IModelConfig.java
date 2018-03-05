package com.vue.adminlte4j.support;

import com.vue.adminlte4j.model.AppInfo;
import com.vue.adminlte4j.model.Menu;
import com.vue.adminlte4j.model.TableData;

import java.io.IOException;
import java.util.List;

/**
 * Created by bjliuyong on 2017/12/26.
 */
public interface IModelConfig {

    List<TableData.Column> configModelColumn(Class type) ;

    AppInfo loadAppInfo() throws IOException;

    void  storeAppInfo(AppInfo  appInfo) throws IOException ;

    List<Menu> loadMenus()  ;

    void storeMenus(List<Menu> menus) throws Exception;

    void deleteMenu(String id) throws Exception ;

    Menu addMenu(Menu menu) throws Exception ;
}
