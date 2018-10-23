package com.vue.adminlte4j.support.store;


import java.util.ArrayList;

import com.vue.adminlte4j.model.Menu;
import org.junit.Assert;
import org.junit.Test;


/**
 * Created by bjliuyong on 2018/3/12.
 */
public class BaseStoreTest {

    BaseStore baseStore = new BaseStore() {} ;
    String fileName = "___test___.s" ;

    @Test
    public void testWriteObject(){

        _testWriteObject("这个是普通的内容而已") ;
        //测试回车换行
        _testWriteObject("小白\r\n很白");

        _testWriteObject("小白\r很白");

        _testWriteObject("小白\n很白");
    }

    private void _testWriteObject(String content) {

        Menu menu = new Menu();
        menu.setDesc(content);
        menu.setId("1");
        menu.setOrder(1);

        try {
            baseStore.writeObject(menu , fileName);
            Menu menu1 = baseStore.readObject(fileName ,Menu.class) ;
            Assert.assertEquals(menu.getDesc() , menu1.getDesc());
        } finally {
            //删除测试数据文件
            baseStore.writeObject(new ArrayList<Menu>(), fileName);
        }
    }


}

