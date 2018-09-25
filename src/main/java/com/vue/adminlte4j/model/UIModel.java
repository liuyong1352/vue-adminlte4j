package com.vue.adminlte4j.model;

import com.vue.adminlte4j.model.builder.FormModelUtils;
import com.vue.adminlte4j.model.builder.TableBuilder;
import com.vue.adminlte4j.model.builder.TreeNodeBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by bjliuyong on 2017/12/13.
 */
public class UIModel extends HashMap implements Map {

    /** 成功返回的code   ***/
    public static final int SUCCESS = 1 ;

    /** 失败返回的code   ***/
    public static final int FAIL    = 0 ;

    /**
     *  未登陆code
     */
    public static final int UNAUTHORIZED = 401 ;

    /**
     *  未登陆的跳转url
     */
    public static final String LOCATION = "location" ;

    /** 菜单列表Key     ***/
    public static final String MENU         = "menu_items" ;

    /** 应用信息key     ***/
    public static final String APP_INFO     = "app_info" ;

    /*** 普通数据的Key ****/
    public static final String DATA         = "data" ;

    /** 表格数据的默认key ***/
    public static final String TABLE_DATA   = "tableData" ;

    /**
     * 数据字典数据
     */
    public static final String DICT_DATA    = "dictData" ;

    /** 树形数据的默认key ***/
    public static final String TREE_DATA    = "treeData" ;

    /** 是否登录的key    ***/
    public static final String IS_LOGIN     = "is_login" ;
    /** 登录地址URL的key    ***/
    public static final String LOGIN_URL    = "login_url" ;

    /** 返回值code码的key    ***/
    public static final String CODE         = "code"  ;
    /** 返回信息的key        ***/
    public static final String MSG          = "msg" ;

    /**
     * FormModel key
     */
    public static final String FORM_MODEL   = "formModel";

    /**
     *  用户名称对应的Key
     */
    public static final String USER_NAME    = "user_name" ;

    /**
     * 返回菜单列表
     * @return
     */
    public List<Menu> menu() {
        return (List<Menu>) get(MENU) ;
    }

    /**
     * 设置或合并菜单信息
     * @param inputMenus
     * @return
     */
    public UIModel menu(List<Menu> inputMenus) {

        if(inputMenus == null || inputMenus.isEmpty())
            return this ;

        List<Menu> menus = (List<Menu>) get(MENU) ;
        if(menus == null) {
            menus = new ArrayList<>() ;
            put(MENU , menus) ;
        }
        menus.addAll(inputMenus) ;
        return this ;
    }

    /**
     * 设置或合并菜单信息
     * @param inputMenus
     * @return
     */
    public UIModel menu(Menu inputMenus) {
        List<Menu> menus = (List<Menu>) get(MENU) ;
        if(menus == null) {
            menus = new ArrayList<>() ;
            put(MENU , menus) ;
        }
        menus.add(inputMenus) ;
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

    /**
     * 设置展示的用户名称
     * @param userName
     * @return
     */
    public UIModel setUserName(String userName) {
        return put(USER_NAME , userName) ;
    }

    public UIModel code(int code) {
        return put(CODE , code) ;
    }

    /**
     * 设置提示消息
     * @param msg
     * @return
     */
    public UIModel msg(String msg) {
        return put(MSG , msg) ;
    }

    @Deprecated
    public UIModel setMsg(String msg) {
        return put(MSG , msg) ;
    }

    public UIModel put(Object value) {
        String key = value.getClass().getSimpleName() ;
        return put(key , value) ;
    }

    public UIModel put(String key , Object value) {
        super.put(key , value) ;
        return  this ;
    }

    /**
     * 获取key=data对应的值
     * @return
     */
    public Object data() {
        return get(DATA) ;
    }

    /**
     * 存放key=data value=bean对
     * @param bean
     * @return
     */
    public UIModel data(Object bean) {
        put(DATA , bean) ;
        return  this ;
    }


    public UIModel formData(Object bean) {
        if(bean == null )
            throw new NullPointerException("formData param can not be null!") ;
        return formData(bean , bean.getClass()) ;
    }

    public UIModel formData(Object bean , Class clsType) {
        return put(FormModelUtils.getFormModel(clsType)).data(bean) ;
    }

    public UIModel tableData(TableBuilder builder){
        return put(TABLE_DATA , builder.build());
    }

    public UIModel tableData(TableData tableData) {
        return put(TABLE_DATA , tableData) ;
    }

    /**
     * 存放key=treeData, value为转换后的treeNode结构数据列表
     * @param elements
     * @return
     */
    public UIModel treeData(List<? extends ITreeNode> elements) {
        return  treeData(elements , TreeNodeBuilder.INSTANCE);
    }

    /**
     * 存放key=treeData, value为转换后的treeNode结构数据列表
     * @param elements
     * @param treeNodeBuilder
     * @return
     */
    public UIModel treeData(List<? extends Object> elements,TreeNodeBuilder treeNodeBuilder) {
        return  put(TREE_DATA , treeNodeBuilder.transform(elements));
    }

    public static UIModel success() {
        return newInstance().code(SUCCESS);
    }

    public static UIModel fail() {
        return newInstance().code(FAIL);
    }

    /**
     * 创建一个未登陆实例返回
     * @return
     */
    public static UIModel unauthorized(String location) {
        return newInstance().code(UNAUTHORIZED).put(LOCATION , location) ;
    }

    public static UIModel newInstance() {
        return new UIModel() ;
    }

    /**
     * simple toJsonString, 不对其进行转义， 只能在简单的字符串情况下用 ， 对象不进行处理
     * @return
     */
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
