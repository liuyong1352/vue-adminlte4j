package com.vue.adminlte4j.support.store;


import com.vue.adminlte4j.util.EnvUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by bjliuyong on 2018/2/5.
 */
public interface BaseStore {


    String WORKSPACE_DIR = "ui-model" ;
    String APP_INFO_FILE = "app_info.s" ;
    String MENU_ITEM_FILE = "menu_items.s";

    /**
     * 获取fileName的存储文件路劲
     * @param fileName
     * @return
     */
    default Path getStorePath(String fileName) {
        if(EnvUtils.isDevelopment)
            return getWorkSpacePath(fileName) ;
        return getPathFromClassPath(fileName) ;
    }

    /**
     * 获取当前的工作空间
     * @param dir
     * @return
     */
    default Path getWorkSpacePath(String dir) {
        Path path = EnvUtils.getJavaResourcesPath();
        return Paths.get(path.toString(), WORKSPACE_DIR, dir) ;
    }


    default Path getPathFromClassPath(String fileName) {
        String configPath = this.getClass().getClassLoader().getResource(WORKSPACE_DIR).getPath();
        return Paths.get(configPath , fileName) ;
    }


    default File getOrCreateFile(String fileName) throws IOException {
        if(!EnvUtils.isDevelopment)
            throw new IllegalStateException("current not in development Mode !") ;

        File storeFile =  getWorkSpacePath(fileName).toFile() ;
        if(!storeFile.exists()) {
            File parentFile = storeFile.getParentFile() ;
            if(!parentFile.exists())
                parentFile.mkdirs() ;
            storeFile.createNewFile() ;
        }
        return storeFile;

    }

}
