package com.vue.adminlte4j.web.config;

import com.vue.adminlte4j.model.Menu;
import com.vue.adminlte4j.service.MenuService;
import com.vue.adminlte4j.support.AdminRuntimeException;
import java.util.List;

/**
 * Created by bjliuyong on 2018/6/5.
 */
public interface MenuApiConfig extends  BaseConfig{

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
        _checkMenuCanModified(menu.getId());
        getMenuService().update(menu);
    }

    default void _deleteMenu(String menuId) {
        _checkMenuCanModified(menuId);
        List<Menu> children = getMenuService().findChildren(menuId) ;
        if(children != null && !children.isEmpty())
            throw new AdminRuntimeException("您选择的菜单有子菜单 ， 不能进行删除操作！") ;

        getMenuService().delete(menuId) ;
    }

    default void _upMenuLevel(String menuId) {
        _checkMenuCanModified(menuId);
        getMenuService().upOrder(menuId);
    }

    default void _checkMenuCanModified(String menuId) {
        if(getMenuService().findById(menuId) == null )
            throw new AdminRuntimeException("您选择的菜单，不能通过界面进行修改操作！") ;
    }

}
