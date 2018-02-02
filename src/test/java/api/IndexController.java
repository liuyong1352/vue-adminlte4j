package api;

import api.data.MenuApiInJvm;
import com.vue.adminlte4j.model.Menu;
import com.vue.adminlte4j.model.TableData;
import com.vue.adminlte4j.model.UIModel;
import com.vue.adminlte4j.support.ModelConfigManager;
import com.vue.adminlte4j.web.config.MenuConfig;
import com.vue.adminlte4j.web.springmvc.ApiAdminController;
import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by bjliuyong on 2017/12/14.
 */
@Controller
public class IndexController extends ApiAdminController {

    @GetMapping("/get_table_data")
    @ResponseBody
    UIModel get_table_data(@RequestParam(required = false) String name) {



        TableData tableData = new TableData() ;
        tableData.configDisplayColumn(UserInfo.class);

        tableData.configDisplayColumn(TableData.createColumn("name" , "姓名") );
        tableData.configDisplayColumn(TableData.createColumn("age" , "年龄") );
        tableData.configDisplayColumn(TableData.createColumn("birthDay" , "生日") );

        for(int i = 0 ; i < 10 ; i++ ) {
            UserInfo userInfo = new UserInfo() ;
            userInfo.setName("u" + i);

            if(name!= null && !name.isEmpty()) {
                if(!userInfo.getName().startsWith(name))
                    continue;
            }

            userInfo.setAge(20 + i);
            userInfo.setBirthDay(new Date());
            userInfo.setSex(i%2);
            tableData.addData(userInfo);
        }

        if("".equals(name)) {
            tableData.setTotalSize(0);
            tableData.setPage(false);
        }

        tableData.setTotalSize(50);

        return UIModel.success().tableData(tableData);
    }

    @GetMapping("/get_menu_data")
    @ResponseBody
    UIModel getMenus() throws Exception {

        TableData<Menu> tableData = new TableData<>() ;

        tableData.configDisplayColumn(Menu.class);

        tableData.setPage(false);
        tableData.addAll(_listMenu());

        return UIModel.success().tableData(tableData);
    }

    private List<Menu> _listMenu() {
        List<Menu> _menus = MenuConfig.mergeAdminMenu(ModelConfigManager.getMenu()) ;
        List<Menu> out = new ArrayList<>() ;

        for(Menu menu : _menus) {
            _listChildMenu(menu , out) ;
        }

        return  out ;
    }

    private void _listChildMenu(Menu menu , List<Menu> out) {

        if(menu.getChildren() != null ) {
            for(Menu child : menu.getChildren()) {
                child.setPid(menu.getId());
                _listChildMenu(child , out);
            }
        }

        menu.setChildren(null);
        out.add(menu) ;
    }
}
