package com.vue.adminlte4j.service;

import com.vue.adminlte4j.model.Menu;
import java.util.List;

/**
 * Created by bjliuyong on 2018/3/9.
 */
public class DefaultMenuService implements MenuService {
    /**
     * 添加菜单
     *
     * @param menu
     */
    @Override public void save(Menu menu) {

    }

    /**
     * 删除对应的菜单
     *
     * @param menuId
     * @return
     */
    @Override public boolean delete(String menuId) {
        return false;
    }

    /***
     * 修改菜单

     * @param menu
     */
    @Override public void update(Menu menu) {

    }

    /**
     * 根据Id 查出菜单
     *
     * @param menuId
     * @return
     */
    @Override public Menu findById(String menuId) {
        return null;
    }

    /***
     * 查询所有菜单
     * @return
     */
    @Override public List<Menu> findAll() {
        return null;
    }
}
