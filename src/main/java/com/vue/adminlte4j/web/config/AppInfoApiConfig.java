package com.vue.adminlte4j.web.config;



import com.vue.adminlte4j.model.AppInfo;
import com.vue.adminlte4j.model.UIModel;
import com.vue.adminlte4j.service.AppInfoService;
import com.vue.adminlte4j.util.Utils;


public class AppInfoApiConfig {

    public static UIModel getAppInfo(AppInfoService appInfoService ) {
        return Utils.call(() -> UIModel.success().formData(appInfoService.get()))  ;
    }

    public static UIModel updateAppInfo(AppInfoService appInfoService , AppInfo appInfo) {
        return Utils.call(()->{
            appInfoService.update(appInfo);
            return UIModel.success().setMsg("修改成功！");
        }) ;
    }
}
