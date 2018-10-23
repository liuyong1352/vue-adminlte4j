package com.vue.adminlte4j.web.springmvc;

import com.vue.adminlte4j.model.Menu;
import com.vue.adminlte4j.model.TableData;
import com.vue.adminlte4j.model.UIModel;
import com.vue.adminlte4j.model.builder.TableBuilder;
import com.vue.adminlte4j.util.MenuUtils;
import com.vue.adminlte4j.util.Utils;
import com.vue.adminlte4j.web.config.MenuApiConfig;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by bjliuyong on 2018/6/5.
 */
public interface MenuApiController extends MenuApiConfig {

    @RequestMapping(value = "/admin/menu/_list" , method = RequestMethod.GET)
    @ResponseBody
    default UIModel _list() throws Exception {
        return UIModel.success().data(_listMenu());
    }


    @RequestMapping(value = "/admin/menu/add" , method = RequestMethod.POST)
    @ResponseBody
    default UIModel addMenu(@RequestBody Menu menu)  {
        return Utils.run(() -> _addMenu(menu));
    }

    @RequestMapping(value = "/admin/menu/delete/{id}" , method = RequestMethod.DELETE)
    @ResponseBody
    default UIModel deleteMenu(@PathVariable String id) {
        return Utils.run(()-> _deleteMenu(id)) ;
    }

    @RequestMapping(value = "/admin/menu/update" , method = RequestMethod.POST)
    @ResponseBody
    default UIModel updateMenu(@RequestBody Menu menu) {
        return Utils.run(()-> _updateMenu(menu) , "修改成功！") ;
    }


    @RequestMapping(value = "/admin/menu/up/{id}" , method = RequestMethod.POST)
    @ResponseBody
    default UIModel upMenuLevel(@PathVariable String id) {
        return Utils.run(()-> _upMenuLevel(id)) ;
    }

    @RequestMapping(value = "/admin/menu/get/{id}" , method = RequestMethod.GET)
    @ResponseBody
    default UIModel getMenu(@PathVariable String id) {
        return _getMenu(id) ;
    }

    @RequestMapping(value = "/admin/menu/get/" , method = RequestMethod.GET)
    @ResponseBody
    default UIModel _getMenu(String id) {
        Menu menu = null ;
        if(id != null && !id.isEmpty()) {
            UIModel uiModel = UIModel.success() ;
            configureMenu(uiModel);
            menu = MenuUtils.getMenu(uiModel.menu() , id );
        }
        return  UIModel.success().formData(menu , Menu.class) ;
    }

    @RequestMapping(value = "/admin/menu/list" , method = RequestMethod.GET)
    @ResponseBody
    default UIModel listMenu() throws Exception {
        TableData tableData = TableBuilder.newBuilder(Menu.class)
                .ignoreColumn("children")
                .isPage(false)
                .data(_listMenu())
                .build();

        return UIModel.success().tableData(tableData);
    }

    @RequestMapping(value = "/admin/menu/tree" , method = RequestMethod.GET)
    @ResponseBody
    default UIModel treeMenu() throws Exception {
        return UIModel.success().treeData(_listMenu()) ;
    }

    default List<Menu> _listMenu() throws Exception{

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

    default void _listChildMenu(Menu menu , List<Menu> out) {
        if(menu.getChildren() != null ) {
            for(Menu child : menu.getChildren()) {
                child.setPid(menu.getId());
                _listChildMenu(child , out);
            }
        }
        out.add(menu.clone()) ;
    }

}
