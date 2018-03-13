package com.vue.adminlte4j.util;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by bjliuyong on 2018/3/9.
 */
public class EnvUtils {

    public static final boolean isDevelopment  ;

    static {

        boolean flag = getJavaResourcesPath().toFile().exists() ;
        if(!flag) {
            flag = getJavaSrcPath().toFile().exists() ;
        }

        isDevelopment = flag ;
    }

    /**
     * 得到当前工程路径
     * @return
     */
    public static Path getJavaResourcesPath() {
        String userDir = System.getProperty("user.dir") ;
        Path path = Paths.get(userDir  , "src" , "main" , "resources") ;
        return  path ;
    }

    /**
     * 得到当前工程路径
     * @return
     */
    public static Path getJavaSrcPath() {
        String userDir = System.getProperty("user.dir") ;
        Path path = Paths.get(userDir  , "src" , "main" , "java") ;
        return  path ;
    }

}
