package com.vue.adminlte4j.util;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by bjliuyong on 2018/3/9.
 */
public class EnvUtils {

    public static final boolean isDevelopment = isIsDevelopment() ;

    private static boolean isIsDevelopment() {

        String srcDir = System.getProperty(Constants.UI_SRC_DIR) ;
        if(srcDir != null && !srcDir.isEmpty()) {
            return true ;
        }

        boolean flag = getJavaResourcesPath().toFile().exists() ;
        if(!flag) {
            flag = getJavaSrcPath().toFile().exists() ;
        }
        return flag ;
    }

    /**
     * 得到当前工程路径
     * @return
     */
    public static Path getJavaResourcesPath() {
        return  Paths.get(getSrcDir()  , "src" , "main" , "resources") ;
    }

    /**
     * 得到当前工程路径
     * @return
     */
    public static Path getJavaSrcPath() {
        return  Paths.get(getSrcDir()  , "src" , "main" , "java") ;
    }

    /**
     * 获取src dir
     * @return
     */
    private static String getSrcDir() {
        String srcDir = System.getProperty(Constants.UI_SRC_DIR) ;
        if(srcDir == null || srcDir.isEmpty())
            srcDir = System.getProperty("user.dir") ;
        return srcDir ;
    }

}
