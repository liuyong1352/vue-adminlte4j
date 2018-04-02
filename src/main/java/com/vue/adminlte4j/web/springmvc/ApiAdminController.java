package com.vue.adminlte4j.web.springmvc;

import com.vue.adminlte4j.model.AppInfo;
import com.vue.adminlte4j.model.Menu;
import com.vue.adminlte4j.model.TableData;
import com.vue.adminlte4j.model.UIModel;
import com.vue.adminlte4j.util.MenuUtils;
import com.vue.adminlte4j.util.Utils;
import com.vue.adminlte4j.web.config.AdminApiConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by bjliuyong on 2018/2/2.
 */
public class ApiAdminController implements AdminApiConfig {

    @GetMapping("/admin/app_info/get_all")
    @ResponseBody
    public UIModel _getAllApiInfo(HttpServletRequest request) {
        return Utils.call(() -> {
            AppInfo appInfo = getAppInfoService().get() ;
            UIModel uiModel = UIModel.success().appInfo(appInfo) ;
            configureMenu(uiModel);
            appInfo.setUserName(getUserName(request));
            MenuUtils.sortTreeData(uiModel.menu());
            return uiModel;
        }) ;

    }

    @GetMapping("/admin/app_info/get")
    @ResponseBody
    public UIModel _getAppInfo() {
        return Utils.call(() -> UIModel.success().formData(getAppInfoService().get())) ;
    }

    @PostMapping("/admin/app_info/update")
    @ResponseBody
    public UIModel _updateAppInfo(@RequestBody AppInfo appinfo)  {
        return Utils.call(()->{
            getAppInfoService().update(appinfo);
            return UIModel.success().setMsg("修改成功！") ;
        }) ;
    }

    @PostMapping("/admin/menu/add")
    @ResponseBody
    public UIModel addMenu(@RequestBody Menu menu)  {
        return Utils.run(() -> _addMenu(menu));
    }

    @DeleteMapping("/admin/menu/delete/{id}")
    @ResponseBody
    public UIModel deleteMenu(@PathVariable String id) {
        return Utils.run(()-> _deleteMenu(id)) ;
    }

    @PostMapping("/admin/menu/update")
    @ResponseBody
    public UIModel updateMenu(@RequestBody Menu menu) {
        return Utils.run(()-> _updateMenu(menu) , "修改成功！") ;
    }


    @PostMapping("/admin/menu/up/{id}")
    @ResponseBody
    public UIModel upMenuLevel(@PathVariable String id) {
        return Utils.run(()-> _upMenuLevel(id)) ;
    }

    @GetMapping("/admin/menu/get/{id}")
    @ResponseBody
    public UIModel getMenu(@PathVariable String id) {
        return _getMenu(id) ;
    }

    @GetMapping("/admin/menu/get/")
    @ResponseBody
    public UIModel _getMenu(String id) {
        Menu menu = null ;
        if(id != null && !id.isEmpty()) {
            UIModel uiModel = UIModel.success() ;
            configureMenu(uiModel);
            menu = MenuUtils.getMenu(uiModel.menu() , id );
        }
        return  UIModel.success().formData(menu , Menu.class) ;
    }

    @GetMapping("/admin/menu/list")
    @ResponseBody
    public UIModel listMenu() throws Exception {
        TableData<Menu> tableData = new TableData<>() ;

        tableData.configDisplayColumn(Menu.class)
            .removeDisplayColumn("children")
            .setPage(false)
            .addAll(_listMenu());

        return UIModel.success().tableData(tableData);
    }

    @GetMapping("/admin/menu/tree")
    @ResponseBody
    public UIModel treeMenu() throws Exception {
        return UIModel.success().treeData(_listMenu()) ;
    }

    private List<Menu> _listMenu() throws Exception{

        UIModel uiModel = new UIModel();
        configureMenu(uiModel);
        List<Menu> _menus = uiModel.menu() ;
        List<Menu> out = new ArrayList<>() ;

        for(Menu menu : _menus) {
            _listChildMenu(menu , out) ;
        }
        Collections.sort(out);
        return  out ;
    }

    private void _listChildMenu(Menu menu , List<Menu> out) {
        if(menu.getChildren() != null ) {
            for(Menu child : menu.getChildren()) {
                child.setPid(menu.getId());
                _listChildMenu(child , out);
            }
        }
        out.add(menu) ;
    }
}
