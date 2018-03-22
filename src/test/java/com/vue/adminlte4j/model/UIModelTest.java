package com.vue.adminlte4j.model;

import java.util.concurrent.locks.Lock;
import org.junit.Test;

/**
 * Created by bjliuyong on 2018/3/1.
 */
public class UIModelTest {

    @Test
    public void testToJson() {
        Lock  lock = null ;

        UIModel uiModel = UIModel.success().isLogin(true).setLoginUrl("/login.html");
        System.out.println(uiModel.toJsonString());
    }
}
