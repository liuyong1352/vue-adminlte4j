package com.vue.adminlte4j.web.config;

import com.vue.adminlte4j.model.Menu;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bjliuyong on 2018/2/2.
 */
public class MenuConfig {

    public static AtomicInteger order = new AtomicInteger(0) ;

    public static List<Menu> mergeAdminMenu(List<Menu> inputMenu) {

        Menu menu = new Menu("m1" ,"应用配置" ,"#" ,"" ,order.incrementAndGet() ) ;
        inputMenu.add(menu) ;
        menu.addChildMenu(new Menu("m1" ,"应用信息配置" ,"/admin/config/app_info.html" ,"" ,order.get()));

        return  inputMenu ;
    }
}
