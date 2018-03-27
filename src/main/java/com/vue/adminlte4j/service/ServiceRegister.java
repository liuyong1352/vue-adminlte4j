package com.vue.adminlte4j.service;

import com.vue.adminlte4j.service.impl.DefaultAppInfoService;
import com.vue.adminlte4j.service.impl.DefaultMenuService;

/**
 * Created by bjliuyong on 2018/3/27.
 */
public interface ServiceRegister {

    ServiceRegister INSTANCE = new ServiceRegister() {

        private MenuService menuService ;
        private AppInfoService appInfoService ;

        @Override public DictService getDictService() {
            return null;
        }

        @Override public MenuService getMenuService() {
            if(menuService != null ) {
                return  menuService ;
            }
            synchronized (this) {
                if(menuService == null)
                    menuService = new DefaultMenuService() ;
            }
            return  menuService ;
        }

        @Override public AppInfoService getAppInfoService() {
            if(appInfoService != null ) {
                return  appInfoService ;
            }
            synchronized (this) {
                if(appInfoService == null)
                    appInfoService = new DefaultAppInfoService() ;
            }
            return  appInfoService ;
        }
    } ;

    DictService getDictService() ;

    MenuService getMenuService() ;

    AppInfoService getAppInfoService();
}
