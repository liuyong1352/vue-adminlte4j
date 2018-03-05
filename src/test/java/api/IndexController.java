package api;

import api.data.MenuApiInJvm;
import com.vue.adminlte4j.model.UIModel;
import com.vue.adminlte4j.web.config.MenuConfig;
import com.vue.adminlte4j.web.springmvc.ApiAdminController;
import org.springframework.stereotype.Controller;

/**
 * Created by bjliuyong on 2017/12/14.
 */
@Controller
public class IndexController extends ApiAdminController {


    /**
     * config menu
     * @param uiModel
     */
    public void configureMenu(UIModel uiModel) {
        addSystemMenu(uiModel);
        /**
         * 完全自定义
         */
        //customMenu(uiModel) ;
    }

    private void addSystemMenu(UIModel uiModel) {
        super.configureMenu(uiModel) ;
        uiModel.menu(MenuApiInJvm.getMenu()) ;
    }

    private void customMenu(UIModel uiModel) {
        uiModel.menu(MenuConfig.systemMenu())
            .menu(MenuApiInJvm.getMenu()) ;

    }
}
