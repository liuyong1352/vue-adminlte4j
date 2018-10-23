package com.vue.adminlte4j.model;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.concurrent.locks.Lock;

import com.alibaba.fastjson.JSON;
import com.vue.adminlte4j.model.config.ConfigModel;
import com.vue.adminlte4j.util.FileUtils;
import org.junit.Test;

/**
 * Created by bjliuyong on 2018/3/1.
 */
public class UIModelTest {

    @Test
    public void testToJson() throws Exception{

        UIModel uiModel = UIModel.success().isLogin(true).setLoginUrl("/login.html");
        System.out.println(uiModel.toJsonString());

        Method method = FileUtils.class.getMethod("newBufferedReader" , InputStream.class , Charset.class) ;
        System.out.println(method);
        ConfigModel configModel = JSON.parseObject("{\"clazz\":true,\"id\":0}" , ConfigModel.class);
        System.out.println(JSON.toJSONString(configModel) );
    }
}
