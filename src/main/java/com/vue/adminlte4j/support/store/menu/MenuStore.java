package com.vue.adminlte4j.support.store.menu;

import com.vue.adminlte4j.model.Menu;
import com.vue.adminlte4j.service.MenuService;
import com.vue.adminlte4j.support.store.BaseStore;
import com.vue.adminlte4j.util.EnvUtils;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bjliuyong on 2018/3/9.
 */
public class MenuStore implements MenuService , BaseStore{

    private TreeMap<String,Menu> menuMap  ;
    private AtomicInteger menuIdGenerator ;

    {
        findAll() ;
    }

    @Override public synchronized void save(Menu menu) {
        menu.setId(getNewMenuId() + "");
        menuMap.put(menu.getId() , menu.clone()) ;
        store();
    }

    @Override public synchronized boolean delete(String menuId) {
        menuMap.remove(menuId) ;
        store();
        return true;
    }

    @Override public void update(Menu menu) {
        menuMap.put(menu.getId() , menu.clone()) ;
        store();
    }

    @Override public Menu findById(String menuId) {
        if(menuId == null)
            return  null ;
        Menu menu = menuMap.get(menuId) ;
        return menu == null ? null : menu.clone() ;
    }

    @Override public List<Menu> findAll() {
        if(menuMap == null )
            menuMap = loadMenus() ;
        List<Menu> menuList = new ArrayList<>(menuMap.size()) ;
        menuMap.forEach((k ,v)->menuList.add(v.clone()));
        return menuList ;
    }

    @Override
    public Map<String,Menu> findMap() {
        if(menuMap == null )
            menuMap = loadMenus() ;
        Map<String,Menu> result = new HashMap<>() ;
        menuMap.forEach((k ,v)->result.put(k ,v.clone()));
        return result ;
    }

    private void store()  {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(getOrCreateFile(MENU_ITEM_FILE)))) {
            outputStream.writeObject(menuMap);
        } catch (Exception e) {
            throw new RuntimeException(e) ;
        }
    }

    private synchronized TreeMap<String,Menu> loadMenus() {
        Path storePath = getStorePath(MENU_ITEM_FILE) ;

        if(storePath == null || !storePath.toFile().exists()){
            return (menuMap = new TreeMap<>());
        }
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storePath.toString())) ){
            return (menuMap = (TreeMap<String,Menu>)ois.readObject()) ;
        } catch (Exception e) {
            throw new RuntimeException(e) ;
        }
    }

    private int getNewMenuId() {
        if( menuIdGenerator == null ) {
            loadMenus() ;
            initMenuIdGenerator(menuMap);
        }

        return menuIdGenerator.getAndIncrement() ;
    }

    private void initMenuIdGenerator(TreeMap<String,Menu> treeMap) {

        Map.Entry<String ,Menu> lastEntry = treeMap.lastEntry()  ;
        int maxId = 1 ;
        if(lastEntry != null )
            maxId  = Integer.valueOf(lastEntry.getKey()) ;

        menuIdGenerator = new AtomicInteger(maxId + 1 ) ;
    }
}
