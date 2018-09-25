package com.vue.adminlte4j.service;


import com.vue.adminlte4j.service.impl.DefaultAppInfoService;
import com.vue.adminlte4j.service.impl.DefaultDictService;
import com.vue.adminlte4j.service.impl.DefaultMenuService;
import com.vue.adminlte4j.service.impl.DefaultModelConfigService;

/**
 * Created by bjliuyong on 2018/6/5.
 */
public class DefaultServiceRegister implements ServiceRegister {

    protected AppInfoService appInfoService ;
    protected MenuService menuService ;
    protected ModelConfigService modelConfigService ;
    protected DictService dictService ;

    public DefaultServiceRegister() {
        this.appInfoService     = new DefaultAppInfoService() ;
        this.menuService        = new DefaultMenuService() ;
        this.modelConfigService = new DefaultModelConfigService() ;
        this.dictService        = new DefaultDictService() ;
    }

    @Override
    public AppInfoService getAppInfoService() {
        return appInfoService;
    }

    @Override
    public MenuService getMenuService() {
        return menuService;
    }

    @Override
    public ModelConfigService getModelConfigService() {
        return modelConfigService;
    }

    @Override
    public DictService getDictService() {
        return dictService;
    }

}
