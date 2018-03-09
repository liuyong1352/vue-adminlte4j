package com.vue.adminlte4j.support.store.menu;

import com.vue.adminlte4j.model.Menu;
import com.vue.adminlte4j.service.MenuService;
import java.util.List;

/**
 * Created by bjliuyong on 2018/3/9.
 */
public class MenuStore implements MenuService {

    @Override public void save(Menu menu) {

    }

    @Override public boolean delete(String menuId) {
        return false;
    }

    @Override public void update(Menu menu) {

    }

    @Override public Menu findById(String menuId) {
        return null;
    }

    @Override public List<Menu> findAll() {
        return null;
    }
}
