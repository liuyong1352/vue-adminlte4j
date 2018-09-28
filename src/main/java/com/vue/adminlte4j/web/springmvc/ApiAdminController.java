package com.vue.adminlte4j.web.springmvc;

import com.vue.adminlte4j.model.AppInfo;
import com.vue.adminlte4j.model.Menu;
import com.vue.adminlte4j.model.UIModel;
import com.vue.adminlte4j.service.DefaultServiceRegister;
import com.vue.adminlte4j.util.Utils;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * Created by bjliuyong on 2018/2/2.
 */
public class ApiAdminController extends DefaultServiceRegister implements AppInfoApiController ,MenuApiController , ModelApiController {

    @RequestMapping(value = "/admin/app_info/get_all" , method = RequestMethod.GET)
    @ResponseBody
    public UIModel layoutInfo(HttpServletRequest request) {
        return Utils.call(() -> {
            AppInfo appInfo = getAppInfoService().get() ;
            UIModel uiModel = UIModel.success().appInfo(appInfo) ;
            uiModel.setUserName(getUserName(request));
            List<Menu> menuList = _listMenu() ;
            uiModel.menu(menuList) ;
            return uiModel;
        }) ;
    }

}
