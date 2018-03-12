package com.vue.adminlte4j.web.config;

import com.vue.adminlte4j.model.Menu;
import com.vue.adminlte4j.model.UIModel;
import com.vue.adminlte4j.service.MenuService;
import com.vue.adminlte4j.support.AdminRuntimeException;
import com.vue.adminlte4j.support.ModelConfigManager;
import com.vue.adminlte4j.util.MenuUtils;

/**
 * Created by bjliuyong on 2018/3/5.
 */
public interface AdminApiConfig {

    /***
     * 获取菜单服务提供者
     * @return
     */
    default MenuService getMenuService(){
        return  MenuService.DEF_SERVICE ;
    }

    default void _addMenu(Menu menu) {
        MenuService menuService = getMenuService() ;

        if(!menu.isRoot()) {
            Menu pMenu = menuService.findById(menu.getPid()) ;
            if(pMenu == null )
                throw new AdminRuntimeException("您选中的父菜单， 不可以添加子菜单！") ;
        }
        menuService.save(menu);
    }

    default void _updateMenu(Menu menu) {
        Menu inMenu = getMenuService().findById(menu.getId()) ;
        if(inMenu == null )
            throw new AdminRuntimeException("您选择的菜单不可以通过界面配置，请联系管理员进行修改！") ;
        getMenuService().update(menu);
    }


    /**
     * config menu
     * @param uiModel
     */
    default void configureMenu(UIModel uiModel) {
        uiModel.menu(getMenuService().getTreeData()) ;
        uiModel.menu(MenuUtils.getDevelopMenus());
    }

    /**
     * config AppInfo
     * @param uiModel
     */
    default void configureAppInfo(UIModel uiModel) {
        uiModel.appInfo(ModelConfigManager.getAppInfo()) ;
    }
}
