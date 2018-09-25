package com.vue.adminlte4j.web.springmvc;

import com.vue.adminlte4j.model.AppInfo;
import com.vue.adminlte4j.model.Menu;
import com.vue.adminlte4j.model.UIModel;
import com.vue.adminlte4j.service.DefaultServiceRegister;
import com.vue.adminlte4j.util.MenuUtils;
import com.vue.adminlte4j.util.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by bjliuyong on 2018/2/2.
 */
public class ApiAdminController  extends DefaultServiceRegister implements AppInfoApiController,MenuApiController{

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

    @RequestMapping(value = "/admin/app_info/get" , method = RequestMethod.GET)
    @ResponseBody
    public UIModel _getAppInfo() {
        return Utils.call(() -> UIModel.success().formData(getAppInfoService().get())) ;
    }

    @RequestMapping(value = "/admin/app_info/update" , method = RequestMethod.POST)
    @ResponseBody
    public UIModel _updateAppInfo(@RequestBody AppInfo appinfo)  {
        return Utils.call(()->{
            getAppInfoService().update(appinfo);
            return UIModel.success().setMsg("修改成功！") ;
        }) ;
    }


//    @RequestMapping(value = "/admin/menu/get/{id}" , method = RequestMethod.GET)
//    @ResponseBody
//    public UIModel getMenu(@PathVariable String id) {
//        return _getMenu(id) ;
//    }
//
//    @RequestMapping(value = "/admin/menu/get/" , method = RequestMethod.GET)
//    @ResponseBody
//    public UIModel _getMenu(String id) {
//        Menu menu = null ;
//        if(id != null && !id.isEmpty()) {
//            UIModel uiModel = UIModel.success() ;
//            configureMenu(uiModel);
//            menu = MenuUtils.getMenu(uiModel.menu() , id );
//        }
//        return  UIModel.success().formData(menu , Menu.class) ;
//    }

//    //@GetMapping("/admin/menu/list")
//    @RequestMapping(value = "/admin/menu/list" , method = RequestMethod.GET)
//    @ResponseBody
//    public UIModel listMenu() throws Exception {
//        TableData tableData = TableBuilder.newBuilder(Menu.class)
//            .ignoreColumn("children")
//            .isPage(false)
//            .data(_listMenu())
//            .build();
//
//        return UIModel.success().tableData(tableData);
//    }

//    //@GetMapping("/admin/menu/tree")
//    @RequestMapping(value = "/admin/menu/tree" , method = RequestMethod.GET)
//    @ResponseBody
//    public UIModel treeMenu() throws Exception {
//        return UIModel.success().treeData(_listMenu()) ;
//    }
//
//    private List<Menu> _listMenu() throws Exception{
//
//        UIModel uiModel = new UIModel();
//        configureMenu(uiModel);
//        List<Menu> _menus = uiModel.menu() ;
//        List<Menu> out = new ArrayList<>() ;
//
//        for(Menu menu : _menus) {
//            _listChildMenu(menu , out) ;
//        }
//        Collections.sort(out);
//        return  out ;
//    }

//    public void _listChildMenu(Menu menu, List<Menu> out) {
//        if(menu.getChildren() != null ) {
//            for(Menu child : menu.getChildren()) {
//                child.setPid(menu.getId());
//                _listChildMenu(child , out);
//            }
//        }
//        out.add(menu) ;
//    }
}
