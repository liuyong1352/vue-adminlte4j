package com.vue.adminlte4j.web.springmvc;

import com.vue.adminlte4j.model.AppInfo;
import com.vue.adminlte4j.model.UIModel;
import com.vue.adminlte4j.web.config.AppInfoApiConfig;
import com.vue.adminlte4j.web.config.BaseConfig;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author tangwei
 * @date 2018/9/24 22:05
 */
public interface AppInfoApiController extends BaseConfig {


    @RequestMapping(value = "/admin/appInfo/get" , method = RequestMethod.GET)
    @ResponseBody
    default UIModel getAppInfo() {
        return AppInfoApiConfig.getAppInfo(getAppInfoService());
    }


    @RequestMapping(value = "/admin/appInfo/update" , method = RequestMethod.POST)
    @ResponseBody
    default UIModel updateAppInfo(@RequestBody AppInfo appinfo)  {
        return AppInfoApiConfig.updateAppInfo(getAppInfoService() , appinfo);
    }
}
