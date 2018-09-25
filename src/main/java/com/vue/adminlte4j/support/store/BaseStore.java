package com.vue.adminlte4j.support.store;

import com.vue.adminlte4j.util.EnvUtils;
import com.vue.adminlte4j.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bjliuyong on 2018/2/5.
 */
public interface BaseStore {


    String WORKSPACE_DIR = "ui-model" ;


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
        URL url = this.getClass().getClassLoader().getResource(WORKSPACE_DIR);
        if(url == null)
            return  null;
        String configPath = url.getPath() ;
        return Paths.get(configPath , fileName) ;
    }

    default boolean deleteStoreFile(String fileName) throws IOException {
        return Files.deleteIfExists(getStorePath(fileName));
    }


    default File getOrCreateFile(String fileName) throws IOException {
        File storeFile =  getWorkSpacePath(fileName).toFile() ;
        if(!storeFile.exists()) {
            File parentFile = storeFile.getParentFile() ;
            if(!parentFile.exists())
                parentFile.mkdirs() ;
            storeFile.createNewFile() ;
        }
        return storeFile;

    }

    /**
     * 检查是否为开发时
     */
    default void checkEnv() {
        if(!EnvUtils.isDevelopment)
            throw new IllegalStateException("current not in development Mode !") ;
    }

    default void writeObject(Object data , String fileName) {
        checkEnv();
        Path storePath = getWorkSpacePath(fileName) ;
        if(storePath == null) {
            throw new IllegalStateException("can not modify , fileName=>" + fileName) ;
        }

        List<Object> objectList  ;
        if(data instanceof List) {
            objectList = (List)data ;
        } else {
            objectList = Arrays.asList(data) ;
        }

        try {
            if(objectList.isEmpty() ){
                Files.deleteIfExists(storePath);
                return;
            }
            Files.write(getOrCreateFile(fileName).toPath() , SerializeUtil.serialize(objectList));
        } catch (Exception e) {
            throw new RuntimeException(e) ;
        }
    }

    default <T> T readObject(String fileName , Class<T> requiredType)  {
        List<T> resultLists = readListObject(fileName , requiredType) ;
        if(resultLists == null || resultLists.isEmpty()) {
            return  null ;
        }
        return resultLists.get(0) ;
    }

    default <T> List<T> readListObject(String fileName , Class<T> requiredType) {

        if(!EnvUtils.isDevelopment)
            return readListObjectInAllClassPath(fileName , requiredType) ;

       /* Path storePath = getStorePath(fileName) ;
        if(storePath == null || !storePath.toFile().exists()) {

        }*/

        List<String> lines ;
        try {
            Path storePath = getWorkSpacePath(fileName) ;
            if(storePath == null || !storePath.toFile().exists() )
                return  null ;
            lines = Files.readAllLines(storePath);
            if(lines == null || lines.isEmpty())
                return null ;
            return SerializeUtil.deSerialize(lines , requiredType) ;
        } catch (Exception e) {
            throw  new RuntimeException(e);
        }
    }

    default <T> List<T> readListObjectInAllClassPath(String fileName , Class<T> requiredType) {
        /*if(EnvUtils.isDevelopment)
            return  null ;*/
        InputStream inputStream = FileUtils.openInputStream(Paths.get(WORKSPACE_DIR , fileName).toString()) ;
        if(inputStream == null )
            return  null ;
        try {
            List<String> lines = FileUtils.readAllLines(inputStream) ;
            return SerializeUtil.deSerialize(lines , requiredType) ;
        } catch (Exception e) {
            throw new RuntimeException(e) ;
        }
    }
}
