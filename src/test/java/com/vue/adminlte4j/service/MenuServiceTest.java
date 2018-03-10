package com.vue.adminlte4j.service;

import com.vue.adminlte4j.model.Menu;
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

        List<Menu> treeData = menuService.getTreeData();
        System.out.println(treeData);
    }


}
