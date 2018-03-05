package com.vue.adminlte4j.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by bjliuyong on 2017/12/13.
 */
public class UIModel extends HashMap implements Map {


    public static final int SUCCESS = 1 ;
    public static final int FAIL    = 0 ;

    public static final String MENU         = "menu_items" ;
    public static final String APP_INFO     = "app_info" ;

    public static final String TABLE_DATA   = "tableData" ;
    public static final String TREE_DATA    = "treeData" ;
    public static final String IS_LOGIN     = "is_login" ;
    public static final String LOGIN_URL    = "login_url" ;
    public static final String CODE         = "code"  ;
    public static final String MSG          = "msg" ;

    public List<Menu> getMenu() {
        return (List<Menu>) get(MENU) ;
    }

    public UIModel menu(List<Menu> menus) {
        List<Menu> _menus = (List<Menu>) get(MENU) ;
        if(_menus == null) {
            _menus = new ArrayList<>() ;
            put(MENU , _menus) ;
        }
        _menus.addAll(menus) ;
        return this ;
    }

    public UIModel menu(Menu menu) {
        List<Menu> _menus = (List<Menu>) get(MENU) ;
        if(_menus == null) {
            _menus = new ArrayList<>() ;
            put(MENU , _menus) ;
        }
        _menus.add(menu) ;
        return this ;
    }

    public UIModel appInfo(AppInfo appInfo) {
        return put(APP_INFO , appInfo) ;
    }

    public UIModel isLogin(boolean isLogin) {
        return put(IS_LOGIN , isLogin) ;
    }

    public UIModel setLoginUrl(String loginUrl) {
        return put(LOGIN_URL , loginUrl) ;
    }

    public UIModel setCode(int code) {
        return put(CODE , code) ;
    }

    public UIModel setMsg(String msg) {
        return put(MSG , msg) ;
    }

    public UIModel setCodeAndMsg(int code , String msg) {
        return setCode(code).setMsg(msg) ;
    }

    public UIModel put(String key , Object value) {
        super.put(key , value) ;
        return  this ;
    }

    public UIModel tableData(TableData tableData) {
        return put(TABLE_DATA , tableData) ;
    }

    public UIModel treeData(List<TreeNode> treeNodes) {
        return put(TREE_DATA , treeNodes) ;
    }

    public static UIModel success() {
        return newInstance(SUCCESS) ;
    }


    public static UIModel fail() {
        return newInstance(FAIL);
    }

    public static UIModel newInstance(int code) {
        UIModel uiModel = new UIModel() ;
        return uiModel.setCode(code) ;
    }

    public String toJsonString() {

        Iterator<Entry<String,Object>> i = entrySet().iterator();
        if (! i.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (;;) {
            Entry<String,Object> e = i.next();
            String key = e.getKey();
            Object value = e.getValue();
            sb.append('\"').append(key).append('\"').append(":") ;
            if(value instanceof String )
                sb.append('\"').append(value).append('\"') ;
            else
                sb.append(value) ;

            if (! i.hasNext())
                return sb.append('}').toString();
            sb.append(',').append(' ');
        }
    }

}
