package api;

import api.data.MenuApiInJvm;
import com.vue.adminlte4j.model.UIModel;
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
        //注意顺序
        customMenu(uiModel);
        super.configureMenu(uiModel) ;
        /**
         * 完全自定义
         */

    }

    private void addSystemMenu(UIModel uiModel) {
        super.configureMenu(uiModel) ;

    }

    private void customMenu(UIModel uiModel) {
        uiModel.menu(MenuApiInJvm.getMenu()) ;
    }
}
