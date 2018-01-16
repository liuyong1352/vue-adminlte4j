package com.vue.adminlte4j.support;

import com.vue.adminlte4j.model.AppInfo;
import com.vue.adminlte4j.model.Menu;
import com.vue.adminlte4j.model.TableData;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by bjliuyong on 2017/12/26.
 */
public class DefaultModelConfig implements IModelConfig{



    private Set<Class> typeSet   = new HashSet<>() ;
    private static final String WORKSPACE_DIR = "ui-model" ;
    private static final String APP_INFO_FILE = "app_info.s" ;


    public   AppInfo appInfo ;



    @Override public List<TableData.Column> configModelColumn(Class type) {

        typeSet.add(type);

        Field[] fields = type.getDeclaredFields() ;

        List<TableData.Column> columns = new ArrayList<>(fields.length) ;

        for(Field field : fields) {
            columns.add(new TableData.Column(field.getName() ,field.getName()));
        }

        return columns;
    }

    @Override
    public List<Menu> loadMenu() {
        return null;
    }

    @Override public synchronized AppInfo loadAppInfo() {

        if(appInfo != null )
            return  appInfo ;

        Path path = isDevMode() ? getWorkSpacePath(APP_INFO_FILE) : loadFromClassPath(APP_INFO_FILE)  ;
        appInfo = new AppInfo() ;

        if(path== null || !path.toFile().exists()) {
            return  appInfo ;
        }

        FileInputStream inputStream = null ;
        try {

            inputStream = new FileInputStream(path.toFile());

            Properties prop      =  new Properties();

            prop.load(inputStream);
            if(prop.size() == 0){
             throw  new Exception("app_info.s don't contaion data,please input data");
            }
            //LOAD 加载prop到appinfo ,需要setAppinfo
            setClassFromProperties(appInfo,prop);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null )
                try {
                    inputStream.close();
                } catch (IOException e) {
                    //ignore this
                }
        }

        return appInfo;
    }


    @Override public synchronized void storeAppInfo(AppInfo appInfo) throws IOException  {

        if(!isDevMode())
            throw new RuntimeException("current not in dev Mode !") ;

        File storeFile = getWorkSpacePath(APP_INFO_FILE).toFile() ;

        File wp = getWorkSpacePath("").toFile() ;

        if(!wp.exists()) {
            wp.mkdir() ;
        }

        if(!storeFile.exists()) {
            storeFile.createNewFile() ;
        }

        Properties prop   =  new Properties();

        FileOutputStream oFile = new FileOutputStream(storeFile);
        try {
            getClassFromProperties(appInfo,prop);

            prop.store(oFile, "change ");
        } catch (IOException e) {
            e.printStackTrace();
            oFile.close();
            throw new IOException("properties store error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("properties store error");
        }
        oFile.close();
    }

    private static Path getJavaResources() {
        String userDir = System.getProperty("user.dir") ;
        Path path = Paths.get(userDir  , "src" , "main" , "resources") ;
        return  path ;
    }

    private static boolean isDevMode() {
        return getJavaResources().toFile().exists() ;
    }

    private static Path getWorkSpacePath(String dir) {
        Path path = getJavaResources() ;
        return Paths.get(path.toString() , WORKSPACE_DIR , dir) ;
    }

    private static Path loadFromClassPath(String fileName) {
        try {
            String configPath = DefaultModelConfig.class.getClassLoader().getResource(WORKSPACE_DIR).getPath();
            return Paths.get(configPath , fileName) ;
        } catch (Exception e) {
            return null ;
        }

    }

    public static void main(String args[] ) throws IOException  {

        DefaultModelConfig modelConfig = new DefaultModelConfig();
        AppInfo appInfo= modelConfig.loadAppInfo();
        System.out.println(appInfo.getUserName());
        appInfo.setUserName("Test 334");
        modelConfig.storeAppInfo(appInfo);

    }

    private static void setClassFromProperties(Object model,Properties prop ) throws Exception {
        int i;
        if(model == null ) return;
        Class<?> cls = model.getClass();
        Field[] fields =cls.getDeclaredFields();
        for(i = 0;i < fields.length;i++){
            fields[i].setAccessible(true);
            fields[i].set(model,prop.getProperty(fields[i].getName()));


        }
    }

    private static void getClassFromProperties(Object model,Properties prop ) throws Exception {
        int i;
        if(model == null ) return;
        Class<?> cls = model.getClass();
        Field[] fields =cls.getDeclaredFields();
        for(i = 0;i < fields.length;i++){
            fields[i].setAccessible(true);
             prop.setProperty(fields[i].getName(),(String) fields[i].get(model));

        }
    }
}
