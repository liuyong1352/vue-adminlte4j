package com.vue.adminlte4j.web.config;



import com.vue.adminlte4j.model.UIModel;
import com.vue.adminlte4j.service.ServiceRegister;
import com.vue.adminlte4j.util.MenuUtils;
import javax.servlet.http.HttpServletRequest;


public interface BaseConfig extends ServiceRegister {

    String USER_NAME = "_user_name" ;

    /**
     * 获取用户名信息 ,
     * 1. 可以重写此方法
     * 2. 拦截器往request写入key为USER_NAME
     * @param request
     * @return
     */
    default String getUserName(HttpServletRequest request) {
        return (String)request.getAttribute(USER_NAME);
    }

    /**
     * config menu
     * @param uiModel
     */
    default void configureMenu(UIModel uiModel) {
        uiModel.menu(getMenuService().getTreeData()) ;
        uiModel.menu(MenuUtils.getDevelopMenus());
    }

}
