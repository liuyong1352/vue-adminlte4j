package com.vue.adminlte4j.web.springmvc;

import com.vue.adminlte4j.model.AppInfo;
import com.vue.adminlte4j.model.UIModel;
import com.vue.adminlte4j.support.ModelConfigManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by bjliuyong on 2018/2/2.
 */
public class ApiAdminController {

    @GetMapping("/admin/app_info/get")
    @ResponseBody
    public UIModel _getAppInfo() {
        try {
            return UIModel.success().appInfo(ModelConfigManager.getAppInfo()) ;
        } catch (IOException e) {
            return UIModel.fail().setMsg("system.error!");
        }
    }

    @PostMapping("/admin/app_info/update")
    @ResponseBody
    UIModel _updateAppinfo(@RequestBody AppInfo appinfo)  {
        try {
            ModelConfigManager.storeAppInfo(appinfo);
            return UIModel.success().setMsg("修改成功！") ;
        } catch (IOException e) {
            return UIModel.fail().setMsg("修改失败!") ;
        }
    }
}
