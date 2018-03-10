package com.vue.adminlte4j.service;

import com.vue.adminlte4j.model.Menu;
import com.vue.adminlte4j.support.store.menu.MenuStore;

import java.util.List;
import java.util.Map;

/**
 * Created by bjliuyong on 2018/3/9.
 */
public class DefaultMenuService implements MenuService {

    private MenuStore delegate = new MenuStore() ;

    /**
     * 添加菜单
     *
     * @param menu
     */
    @Override public void save(Menu menu) {
        delegate.save(menu);
    }

    /**
     * 删除对应的菜单
     *
     * @param menuId
     * @return
     */
    @Override public boolean delete(String menuId) {
        return delegate.delete(menuId);
    }

    /***
     * 修改菜单

     * @param menu
     */
    @Override public void update(Menu menu) {
        delegate.update(menu);
    }

    /**
     * 根据Id 查出菜单
     *
     * @param menuId
     * @return
     */
    @Override public Menu findById(String menuId) {
        return delegate.findById(menuId);
    }

    /***
     * 查询所有菜单
     * @return
     */
    @Override public List<Menu> findAll() {
        return delegate.findAll();
    }

    @Override
    public Map<String,Menu> findMap() {
        return delegate.findMap() ;
    }


}
