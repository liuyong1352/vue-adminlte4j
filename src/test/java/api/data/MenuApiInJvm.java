package api.data;

import com.vue.adminlte4j.model.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by bjliuyong on 2017/12/14.
 */
public class MenuApiInJvm {


    private static  List<Menu> menus ;

    static  {
        menus = buildMenu() ;
    }

    public static List<Menu> getMenu() {
        return  menus ;
    }

    private static List<Menu> buildMenu() {

        List<Menu> menus = new ArrayList<>() ;

        Menu menu2 = createMenu("#" , "实例" , "fa fa-link" , 2) ;
        Menu menu3 = createMenu("#" , "ui-element" , "fa fa-link" , 3) ;
        Menu menu4 = createMenu("#" , "form" , "fa fa-link" , 4) ;

        menu2.addChildMenu(createMenu("/example/v_box.html" , "box使用" , "fa fa-circle-o" , 1));
        menu2.addChildMenu(createMenu("/example/tree.html" , "tree" , "fa fa-circle-o" , 1));
        menu2.addChildMenu(createMenu("/example/v_table.html" , "table使用" , "fa fa-circle-o" , 2));


        menu2.addChildMenu(createMenu("/example/v_button.html" , "button使用" , "fa fa-circle-o" , 2));
        menu2.addChildMenu(createMenu("/example/v_layout.html" , "layout使用" , "fa fa-circle-o" , 2));
        menu2.addChildMenu(createMenu("/example/v_alert.html" , "v_alert使用" , "fa fa-circle-o" , 2));
        menu2.addChildMenu(createMenu("/example/v_callout.html" , "v_callout使用" , "fa fa-circle-o" , 2));
        menu2.addChildMenu(createMenu("/example/v_tab.html" , "v_tab使用" , "fa fa-circle-o" , 2));
        menu2.addChildMenu(createMenu("/example/v_text.html" , "text使用" , "fa fa-circle-o" , 2));
        menu2.addChildMenu(createMenu("/example/v_folder.html" , "folder使用" , "fa fa-circle-o" , 2));
        menu2.addChildMenu(createMenu("/example/v_tags.html" , "tags使用" , "fa fa-circle-o" , 2));

        menu3.addChildMenu(createMenu("/ui-element/v_modal.html" , "Modal" , "fa fa-circle-o" , 1));
        menu4.addChildMenu(createMenu("/form/input.html" , "輸入框" , "fa fa-circle-o" , 1));
        menu4.addChildMenu(createMenu("/form/checkbox.html" , "复选框" , "fa fa-circle-o" , 2));
        menu4.addChildMenu(createMenu("/form/radio.html" , "单选按钮" , "fa fa-circle-o" , 3));
        menu4.addChildMenu(createMenu("/form/select.html" , "下拉选择框" , "fa fa-circle-o" , 4));
        menu4.addChildMenu(createMenu("/form/switch.html" , "开关" , "fa fa-circle-o" , 5));
        menu4.addChildMenu(createMenu("/form/date.html" , "日期" , "fa fa-circle-o" , 6));
        menu4.addChildMenu(createMenu("/form/icon_selector.html" , "图标选择器" , "fa fa-circle-o" , 7));
        menu4.addChildMenu(createMenu("/form/v_form.html" , "动态表单" , "fa fa-circle-o" , 8));
        menu4.addChildMenu(createMenu("/form/v_sform.html" , "静态表单" , "fa fa-circle-o" , 9));

        menus.add(menu2) ;
        menus.add(menu3) ;
        menus.add(menu4) ;



        return menus;
    }

    private static Menu createMenu(String url , String desc , String icon ,int order) {
        Menu menu = new Menu() ;
        menu.setId(UUID.randomUUID().toString());
        menu.setUrl(url);
        menu.setDesc(desc);
        menu.setIcon(icon);
        menu.setOrder(order);
        return menu ;
    }
}
