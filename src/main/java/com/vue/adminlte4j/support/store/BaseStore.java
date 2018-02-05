package com.vue.adminlte4j.support.store;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by bjliuyong on 2018/2/5.
 */
public class BaseStore {

    public static final boolean isDev  ;

    protected static final String WORKSPACE_DIR = "ui-model" ;
    protected static final String APP_INFO_FILE = "app_info.s" ;
    protected static final String MENU_ITEM_FILE = "menu_items.s";

    static {

        boolean flag = getJavaResources().toFile().exists() ;

        if(!flag) {
            String userDir = System.getProperty("user.dir") ;
            Path path = Paths.get(userDir  , "src" , "main" , "java") ;
            flag = path.toFile().exists() ;
        }

        isDev = flag ;
    }

    /**
     * 得到当前工程路径
     * @return
     */
    public static Path getJavaResources() {
        String userDir = System.getProperty("user.dir") ;
        Path path = Paths.get(userDir  , "src" , "main" , "resources") ;
        return  path ;
    }




}
