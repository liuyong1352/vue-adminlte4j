package com.vue.adminlte4j.model;

import api.data.StatusEnum;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by bjliuyong on 2018/2/6.
 */
public class MenuTest {

    @Test
    public void v() throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Menu menu = new Menu() ;
        menu.setId("1");
        menu.setPid("2");
        menu.setUrl("\\index.html");

        System.out.println(StatusEnum.VALID.toString());

        StatusEnum.valueOf("VALID") ;

        PropertyDescriptor propertyDescriptor = new PropertyDescriptor("url" , Menu.class) ;
        System.out.println(propertyDescriptor.getReadMethod().invoke(menu));
        propertyDescriptor.getWriteMethod().invoke(menu , "唐大师");
        System.out.println(propertyDescriptor.getReadMethod().invoke(menu));
    }
}

