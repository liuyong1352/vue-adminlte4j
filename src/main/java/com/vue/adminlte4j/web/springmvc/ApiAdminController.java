package com.vue.adminlte4j.web.springmvc;

import com.vue.adminlte4j.model.UIModel;
import com.vue.adminlte4j.support.ModelConfigManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by bjliuyong on 2018/2/2.
 */
@Controller
public class ApiAdminController {

    @GetMapping("/admin/app_info/get")
    @ResponseBody
    public UIModel getAppInfo() {
        try {
            return new UIModel()
                    .appInfo(ModelConfigManager.getAppInfo())
                    .isLogin(true) ;
        } catch (IOException e) {

            return UIModel.fail().setMsg("system.error!");
        }
    }
}
