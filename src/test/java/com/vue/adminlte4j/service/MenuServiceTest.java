package com.vue.adminlte4j.service;

import com.vue.adminlte4j.model.Menu;
import com.vue.adminlte4j.service.impl.DefaultMenuService;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class MenuServiceTest {

    MenuService menuService = new DefaultMenuService() ;

    @Test
    public void save() {
        Menu menu = new Menu();
        menu.setDesc("test");
        menuService.save(menu);
        menuService.save(menu);

        menu.setDesc("testChild");
        menu.setPid(menu.getId());
        menuService.save(menu);

        List<Menu> menus = menuService.findAll();

        Assert.assertTrue(!menus.isEmpty());

        menus.forEach(o -> menuService.delete(o.getId()));

        menus = menuService.findAll();
        Assert.assertTrue(menus.isEmpty());

    }


}
