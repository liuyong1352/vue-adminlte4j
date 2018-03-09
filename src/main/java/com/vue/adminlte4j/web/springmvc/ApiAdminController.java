package com.vue.adminlte4j.web.springmvc;

import com.vue.adminlte4j.model.AppInfo;
import com.vue.adminlte4j.model.Menu;
import com.vue.adminlte4j.model.TableData;
import com.vue.adminlte4j.model.TreeNode;
import com.vue.adminlte4j.model.UIModel;
import com.vue.adminlte4j.support.ModelConfigManager;
import com.vue.adminlte4j.web.config.AdminApiConfig;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjliuyong on 2018/2/2.
 */
public class ApiAdminController implements AdminApiConfig {

    @GetMapping("/admin/app_info/get_all")
    @ResponseBody
    public UIModel _getAllApiInfo() {
        try {
            UIModel uiModel = UIModel.success();
            configureMenu(uiModel);
            configureAppInfo(uiModel);
            return  uiModel;
        } catch (Exception e) {
            return UIModel.fail().setMsg("system.error!");
        }
    }

    @GetMapping("/admin/app_info/get")
    @ResponseBody
    public UIModel _getAppInfo() {
        try {
            UIModel uiModel = UIModel.success();
            configureAppInfo(uiModel);
            return uiModel ;
        } catch (Exception e) {
            return UIModel.fail().setMsg("system.error!");
        }
    }

    @PostMapping("/admin/app_info/update")
    @ResponseBody
    public UIModel _updateAppInfo(@RequestBody AppInfo appinfo)  {
        try {
            ModelConfigManager.storeAppInfo(appinfo);
            return UIModel.success().setMsg("修改成功！") ;
        } catch (IOException e) {
            return UIModel.fail().setMsg("修改失败! ") ;
        }
    }

    @PostMapping("/admin/menu/add")
    @ResponseBody
    public UIModel addMenu(@RequestBody Menu menu)  {
        try {
            ModelConfigManager.addMenu(menu);
            return UIModel.success().setMsg("修改成功！") ;
        } catch (Exception e) {
            return UIModel.fail().setMsg("修改失败!") ;
        }
    }

    @DeleteMapping("/admin/menu/delete/{id}")
    @ResponseBody
    public UIModel deleteMenu(@PathVariable String id) {
        try {
            ModelConfigManager.deleteMenu(id);
            return UIModel.success().setMsg("修改成功！") ;
        } catch (Exception e) {
            return UIModel.fail().setMsg("修改失败!") ;
        }
    }

    @PostMapping("/admin/menu/up/{id}")
    @ResponseBody
    public UIModel upMenuLevel(@PathVariable String id) {
        try {
            ModelConfigManager.deleteMenu(id);
            return UIModel.success().setMsg("修改成功！") ;
        } catch (Exception e) {
            return UIModel.fail().setMsg("修改失败!") ;
        }
    }

    @GetMapping("/admin/menu/get/{id}")
    @ResponseBody
    public UIModel getMenu(@PathVariable String id) {
        try {
            List<Menu> menus = _listMenu() ;

            for(Menu m : menus) {
                if(m.getId().equals(id))
                    return UIModel.success().data(m) ;
            }
        } catch (Exception e) {
            return UIModel.fail().setMsg("对不起，请稍后再试!") ;
        }
        return UIModel.fail().setMsg("对不起， 未找到您要的数据！") ;
    }

    @GetMapping("/admin/menu/get/")
    @ResponseBody
    public UIModel _getMenu(String id) {

        UIModel uiModel = getMenu(id) ;
        Object data = getMenu(id).data() ;
        if(data == null)
            return uiModel.formData(new Menu());
        return  uiModel.formData(data) ;
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
        return UIModel.success().treeData(_listMenu(), o -> {
            TreeNode treeNode1 = new TreeNode() ;
            Menu menu = (Menu)o ;
            treeNode1.setId(menu.getId());
            treeNode1.setParentId(menu.getPid());
            treeNode1.setText(menu.getDesc());
            treeNode1.setIcon(menu.getIcon());
            return treeNode1;
        }) ;
    }



    private List<Menu> _listMenu() throws Exception{

        UIModel uiModel = new UIModel();
        configureMenu(uiModel);
        List<Menu> _menus = uiModel.menu() ;
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

        //menu.setChildren(null);
        out.add(menu) ;
    }
}
