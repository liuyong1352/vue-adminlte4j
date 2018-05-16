package com.vue.adminlte4j.web.springmvc;

import com.vue.adminlte4j.model.AppInfo;
import com.vue.adminlte4j.model.Menu;
import com.vue.adminlte4j.model.TableData;
import com.vue.adminlte4j.model.UIModel;
import com.vue.adminlte4j.model.builder.TableBuilder;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by bjliuyong on 2018/2/2.
 */
public class ApiAdminController implements AdminApiConfig {

    //@GetMapping("/admin/app_info/get_all")
    @RequestMapping(value = "/admin/app_info/get_all" , method = RequestMethod.GET)
    @ResponseBody
    public UIModel _getAllApiInfo(HttpServletRequest request) {
        return Utils.call(() -> {
            AppInfo appInfo = getAppInfoService().get() ;
            UIModel uiModel = UIModel.success().appInfo(appInfo) ;
            configureMenu(uiModel);
            uiModel.setUserName(getUserName(request));
            MenuUtils.sortTreeData(uiModel.menu());
            return uiModel;
        }) ;

    }

    //@GetMapping("/admin/app_info/get")
    @RequestMapping(value = "/admin/app_info/get" , method = RequestMethod.GET)
    @ResponseBody
    public UIModel _getAppInfo() {
        return Utils.call(() -> UIModel.success().formData(getAppInfoService().get())) ;
    }

    //@PostMapping("/admin/app_info/update")
    @RequestMapping(value = "/admin/app_info/update" , method = RequestMethod.POST)
    @ResponseBody
    public UIModel _updateAppInfo(@RequestBody AppInfo appinfo)  {
        return Utils.call(()->{
            getAppInfoService().update(appinfo);
            return UIModel.success().setMsg("修改成功！") ;
        }) ;
    }

    //@PostMapping("/admin/menu/add")
    @RequestMapping(value = "/admin/menu/add" , method = RequestMethod.POST)
    @ResponseBody
    public UIModel addMenu(@RequestBody Menu menu)  {
        return Utils.run(() -> _addMenu(menu));
    }

    //@DeleteMapping("/admin/menu/delete/{id}")
    @RequestMapping(value = "/admin/menu/delete/{id}" , method = RequestMethod.DELETE)
    @ResponseBody
    public UIModel deleteMenu(@PathVariable String id) {
        return Utils.run(()-> _deleteMenu(id)) ;
    }

    //@PostMapping("/admin/menu/update")
    @RequestMapping(value = "/admin/menu/update" , method = RequestMethod.POST)
    @ResponseBody
    public UIModel updateMenu(@RequestBody Menu menu) {
        return Utils.run(()-> _updateMenu(menu) , "修改成功！") ;
    }


    //@PostMapping("/admin/menu/up/{id}")
    @RequestMapping(value = "/admin/menu/up/{id}" , method = RequestMethod.POST)
    @ResponseBody
    public UIModel upMenuLevel(@PathVariable String id) {
        return Utils.run(()-> _upMenuLevel(id)) ;
    }

    //@GetMapping("/admin/menu/get/{id}")
    @RequestMapping(value = "/admin/menu/get/{id}" , method = RequestMethod.GET)
    @ResponseBody
    public UIModel getMenu(@PathVariable String id) {
        return _getMenu(id) ;
    }

    //@GetMapping("/admin/menu/get/")
    @RequestMapping(value = "/admin/menu/get/" , method = RequestMethod.GET)
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

    //@GetMapping("/admin/menu/list")
    @RequestMapping(value = "/admin/menu/list" , method = RequestMethod.GET)
    @ResponseBody
    public UIModel listMenu() throws Exception {
        TableData tableData = TableBuilder.newBuilder(Menu.class)
            .ignoreColumn("children")
            .isPage(false)
            .data(_listMenu())
            .build();

        return UIModel.success().tableData(tableData);
    }

    //@GetMapping("/admin/menu/tree")
    @RequestMapping(value = "/admin/menu/tree" , method = RequestMethod.GET)
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
