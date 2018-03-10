package com.vue.adminlte4j.service;

import com.vue.adminlte4j.model.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bjliuyong on 2018/3/9.
 */
public interface MenuService {

    MenuService DEF_SERVICE = new DefaultMenuService();

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

    /**
     * 返回Map
     * @return
     */
    default Map<String,Menu> findMap() {
        return null ;
    }

    default List<Menu> getTreeData(){
        List<Menu> menus = new ArrayList<>() ;
        Map<String,Menu> menuMap = findMap() ;
        menuMap.forEach((k ,v) -> {
            String pid = v.getPid() ;
            if(pid == null || pid.isEmpty() || pid.equals("0")) {
                menus.add(v) ;
            } else {
                menuMap.get(pid).addChildMenu(v);
            }
        });
        return menus ;
    }

}
