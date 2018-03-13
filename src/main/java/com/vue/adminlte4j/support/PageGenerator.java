package com.vue.adminlte4j.support;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by bjliuyong on 2018/2/5.
 */
public class PageGenerator {

    public String title ;
    public String path ;

    public void gen() {



    }

    public static void main(String args[]) throws Exception {

        URL url = PageGenerator.class.getClassLoader().getResource("META-INF/sys_tpl_dft_config/starter.html");

        List<String> content = Files.readAllLines(Paths.get(url.toURI())) ;
        System.out.println(url);
    }
}
