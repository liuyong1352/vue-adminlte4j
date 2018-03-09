package com.vue.adminlte4j.util;

import com.vue.adminlte4j.model.Menu;
import com.vue.adminlte4j.support.ModelConfigManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjliuyong on 2018/3/9.
 */
public class MenuUtils {

    private static final String PREFIX = "dev_" ;

    /**
     * 获取界面配置的菜单列表
     * @return 菜单列表
     */
    public static List<Menu> getConfigMenus() {
        return ModelConfigManager.getMenu() ;
    }

    /**
     * 获取开发时配置的菜单列表
     * @return 菜单列表
     */
    public static List<Menu> getDevelopMenus() {
        return  MenuHolder.menus ;
    }

    /**
     * 提升菜单展示顺序
     * @return
     */
    public static boolean upOrder(String menuId) {
        List<Menu> menus = getConfigMenus() ;

        return  true ;
    }

    public static boolean isConfigable(String menuId) {
        if(menuId == null)
            return false  ;
        return  menuId.startsWith(PREFIX) ;
    }

    /**
     * lazy get menu
     */
    private static class MenuHolder {


        private static List<Menu> menus = new ArrayList<>() ;

        static {
            int menuId = 0 ;
            Menu menu = new Menu(PREFIX + menuId++ ,"开发配置" ,"#" ,"fa fa-tv" ,Integer.MAX_VALUE ) ;
            menus.add(menu) ;
            menu.addChildMenu(new Menu(PREFIX + menuId++ ,"应用信息" ,"/admin/config/app_info.html" ,"fa fa-cog" ,0));
            menu.addChildMenu(new Menu(PREFIX + menuId++ ,"菜单管理" ,"/admin/config/menu.html" ,"fa fa-cog" ,1));
        }
    }
}
