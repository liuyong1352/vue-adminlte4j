package com.vue.adminlte4j.support.store;

import com.vue.adminlte4j.model.Menu;
import com.vue.adminlte4j.support.store.menu.MenuStore;
import java.net.URLEncoder;
import org.junit.Test;
import sun.nio.cs.UnicodeEncoder;

/**
 * Created by bjliuyong on 2018/3/12.
 */
public class BaseStoreTest {

    MenuStore menuStore = new MenuStore() ;

    @Test
    public void writeObject() throws Exception{
        String fileName = "BaseStoreTest.s" ;
        Menu menu = new Menu();
        menu.setDesc("a%25 %is ,");
        menu.setId("1");
        menu.setOrder(1);


        menuStore.writeObject(menu , fileName);
        Menu menu1 = menuStore.readObject(fileName ,Menu.class) ;
        System.out.println(menu1);
    }
}
