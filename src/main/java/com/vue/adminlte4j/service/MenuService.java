package com.vue.adminlte4j.service;

import com.vue.adminlte4j.model.Menu;
import java.util.List;

/**
 * Created by bjliuyong on 2018/3/9.
 */
public interface MenuService {

    /**
     * 添加菜单
     * @param menu
     */
    void save(Menu menu) ;

    /**
     * 删除对应的菜单
     * @param menuId
     * @return
     */
    boolean delete(String menuId) ;

    /***
     * 修改菜单
     * @param menu
     */
    void update(Menu menu) ;

    /**
     * 根据Id 查出菜单
     * @param menuId
     * @return
     */
    Menu findById(String menuId) ;

    /***
     * 查询所有菜单
     * @return
     */
    List<Menu> findAll() ;

}
