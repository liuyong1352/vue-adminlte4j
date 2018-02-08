package com.vue.adminlte4j.web.config;

import com.vue.adminlte4j.model.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bjliuyong on 2018/2/2.
 */
public class MenuConfig {

    public static AtomicInteger order = new AtomicInteger(0) ;

    public static List<Menu> mergeAdminMenu(List<Menu> inputMenu) {

        List<Menu> menus = new ArrayList<>() ;
        menus.addAll(inputMenu) ;

        Menu menu = new Menu(Integer.MAX_VALUE + "" ,"开发配置" ,"#" ,"fa fa-tv" ,order.incrementAndGet() ) ;
        menus.add(menu) ;
        menu.addChildMenu(new Menu(Integer.MAX_VALUE - 1 + "" ,"应用信息" ,"/admin/config/app_info.html" ,"fa fa-cog" ,order.incrementAndGet()));
        menu.addChildMenu(new Menu(Integer.MAX_VALUE - 2 + "" ,"菜单管理" ,"/admin/config/menu.html" ,"fa fa-cog" ,order.incrementAndGet()));

        return  menus ;
    }
}
