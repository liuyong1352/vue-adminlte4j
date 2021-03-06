package com.vue.adminlte4j.service;

import com.vue.adminlte4j.model.Menu;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 查找所有的子菜单
     * @param pid
     * @return
     */
    default List<Menu> findChildren(String pid) {
        List<Menu> children = new ArrayList<>() ;
        if(pid == null)
            return  children ;
        findAll().forEach(menu->{
            if(pid.equals(menu.getPid()))
                children.add(menu) ;
        });
        return  children ;
    }

    /**
     * 返回Map
     * @return
     */
    default Map<String,Menu> findMap() {
        Map<String,Menu> result = new HashMap<>() ;
        findAll().forEach(v->result.put(v.getId() , v));
        return result ;
    }

    /**
     * 获取树形的数据
     * @return
     */
    default List<Menu> getTreeData(){
        List<Menu> menus = new ArrayList<>() ;
        Map<String,Menu> menuMap = findMap() ;

        menuMap.forEach((k ,v) -> {
            String pid = v.getPid() ;
            if(pid == null || pid.isEmpty() || pid.equals("0")) {
                menus.add(v) ;
            } else {
                if(menuMap.get(pid) != null ) //删除了父节点 ， 也不能让它出错
                    menuMap.get(pid).addChildMenu(v);
            }
        });

        return menus ;
    }

    /**
     * 提升展示次序
     * @param menuId
     */
    default void upOrder(String menuId) {
        Menu menu = findById(menuId) ;
        if(menu == null )
            return;
        List<Menu> brothers = findChildren(menu.getPid()) ;
        Collections.sort(brothers);
        //get last one
        Menu maxIdMenu = brothers.get(brothers.size() - 1) ;
        if(maxIdMenu.getId().equals(menuId))
            return;
        int order = maxIdMenu.getOrder() ;
        if(menu.getOrder() <= order) {
            menu.setOrder(order + 1);
        }
        update(menu);
    }

}
