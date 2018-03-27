package com.vue.adminlte4j.service;

import com.vue.adminlte4j.model.AppInfo;

/**
 * Created by bjliuyong on 2018/3/13.
 */
public interface AppInfoService {

    /**
     * 获取AppInfo
     * @return
     */
    AppInfo get() ;

    /**
     * 修改AppInfo
     * @param appInfo
     */
    void  update(AppInfo appInfo);


}
