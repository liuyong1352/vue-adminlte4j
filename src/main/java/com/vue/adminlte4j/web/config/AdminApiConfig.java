package com.vue.adminlte4j.web.config;

import com.vue.adminlte4j.model.Menu;
import com.vue.adminlte4j.model.UIModel;
import com.vue.adminlte4j.service.MenuService;
import com.vue.adminlte4j.support.ModelConfigManager;
import com.vue.adminlte4j.util.MenuUtils;

import java.util.List;

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

    /**
     * config menu
     * @param uiModel
     */
    default void configureMenu(UIModel uiModel) {
        List<Menu> customMenus = getMenuService().getTreeData();
        uiModel.menu(customMenus) ;
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
