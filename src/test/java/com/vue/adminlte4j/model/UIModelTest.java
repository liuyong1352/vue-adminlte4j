package com.vue.adminlte4j.model;

import java.util.concurrent.locks.ReentrantLock;
import org.junit.Test;

/**
 * Created by bjliuyong on 2018/3/1.
 */
public class UIModelTest {


    private final ReentrantLock lock = new ReentrantLock();


    public void m() {
        lock.lock(); // block until condition holds
        try {
            // ... method body
        }finally {
            lock.unlock();
        }
    }


    @Test
    public void testToJson() {
        UIModel uiModel = UIModel.success().isLogin(true).setLoginUrl("/login.html");
        System.out.println(uiModel.toJsonString());
    }
}
