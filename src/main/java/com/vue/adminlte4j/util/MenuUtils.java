package com.vue.adminlte4j.util;

import com.vue.adminlte4j.model.Menu;

import java.util.*;

/**
 * Created by bjliuyong on 2018/3/9.
 */
public class MenuUtils {

    private static final String PREFIX = "dev_" ;

    /**
     * 获取开发时配置的菜单列表
     * @return 菜单列表
     */
    public static List<Menu> getDevelopMenus() {
        return  MenuHolder.menus ;
    }

    public static Menu getMenu(List<Menu> menus , String menuId) {
        if(menus == null)
            return  null ;
        for(Menu menu : menus) {
            if(menu.getId().equals(menuId))
                return  menu ;
            Menu m = getMenu(menu.getChildren() , menuId) ;
            if(m != null)
                return m ;
        }
        return  null ;
    }


    public static void sortTreeData(List<Menu> menus) {
        Collections.sort(menus);
        menus.forEach(menu->sortMenu(menu));
    }

    private static void sortMenu(Menu menu) {
        if(menu.getChildren() != null)
            sortTreeData(menu.getChildren());
    }


    /**
     * lazy get menu
     */
    private static class MenuHolder {


        private static List<Menu> menus = new ArrayList<>() ;
        private static Map<String, Menu> menuMap = new HashMap<>() ;

        static {
            int menuId = 0 ;
            Menu menu = new Menu(PREFIX + menuId++ ,"开发配置" ,"#" ,"fa fa-tv" ,Integer.MAX_VALUE) ;

            menus.add(menu) ;
            menu.addChildMenu(new Menu(PREFIX + menuId++ ,"应用信息" ,"/admin/config/app_info.html" ,"fa fa-cog" ,0));
            menu.addChildMenu(new Menu(PREFIX + menuId++ ,"菜单管理" ,"/admin/config/menu.html" ,"fa fa-cog" ,1));

            menuMap.put(menu.getId() , menu) ;
            menu.getChildren().forEach(item ->menuMap.put(item.getId() , item));
        }
    }
}
