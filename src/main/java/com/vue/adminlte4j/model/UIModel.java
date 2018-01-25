package com.vue.adminlte4j.model;

import java.util.HashMap;
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
    public static final String IS_LOGIN     = "is_login" ;
    public static final String LOGIN_URL    = "login_url" ;
    public static final String CODE         = "code"  ;
    public static final String MSG          = "msg" ;

    public UIModel menu(List<Menu> menus) {
        put(MENU, menus) ;
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


}
