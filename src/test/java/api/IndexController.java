package api;

import api.data.MenuApiInJvm;
import com.vue.adminlte4j.model.UIModel;
import com.vue.adminlte4j.model.form.FormModel;
import com.vue.adminlte4j.web.config.MenuConfig;
import com.vue.adminlte4j.web.springmvc.ApiAdminController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by bjliuyong on 2017/12/14.
 */
@Controller
public class IndexController extends ApiAdminController {

    @GetMapping("/admin/form/model/get")
    @ResponseBody
    public UIModel getFormModel() {
        FormModel formModel = new FormModel() ;
        formModel.addField("name" , "Test" , 1) ;
        return  UIModel.success().put(formModel) ;
    }

    /**
     * config menu
     * @param uiModel
     */
    public void configureMenu(UIModel uiModel) {
        //addSystemMenu(uiModel);
        /**
         * 完全自定义
         */
        customMenu(uiModel) ;
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
