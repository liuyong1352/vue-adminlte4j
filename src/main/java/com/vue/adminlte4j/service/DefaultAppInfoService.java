package com.vue.adminlte4j.service;

import com.vue.adminlte4j.model.AppInfo;
import com.vue.adminlte4j.support.store.BaseStore;
import java.io.IOException;

/**
 * Created by bjliuyong on 2018/3/13.
 */
public class DefaultAppInfoService implements AppInfoService , BaseStore {

    private AppInfo appInfo ;

    /**
     * 获取AppInfo
     *
     * @return
     */
    @Override public AppInfo get() {
        if(appInfo == null )
            appInfo = load() ;
        return appInfo;
    }

    private synchronized AppInfo load() {
        try {
            AppInfo appInfo = readObject(APP_INFO_FILE , AppInfo.class);
            return (appInfo == null ? new AppInfo() : appInfo) ;
        } catch (Exception e) {
            throw new RuntimeException(e) ;
        }
    }

    /**
     * 修改AppInfo
     *
     * @param appInfo
     */
    @Override public synchronized void update(AppInfo appInfo) {
        try {
            this.appInfo = appInfo ;
            writeObject(this.appInfo , APP_INFO_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e) ;
        }
    }
}
