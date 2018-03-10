package com.vue.adminlte4j.support;


import com.vue.adminlte4j.model.AppInfo;
import com.vue.adminlte4j.model.Menu;
import com.vue.adminlte4j.model.TableData;
import com.vue.adminlte4j.support.store.BaseStore;

import com.vue.adminlte4j.util.EnvUtils;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bjliuyong on 2017/12/26.
 */

public class DefaultModelConfig implements IModelConfig ,BaseStore{
    @Override
    public List<Menu> loadMenus() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteMenu(String id) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Menu addMenu(Menu menu) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private Set<Class> typeSet   = new HashSet<>() ;

    private   AppInfo appInfo ;




    @Override public List<TableData.Column> configModelColumn(Class type) {

        typeSet.add(type);

        Field[] fields = type.getDeclaredFields() ;

        List<TableData.Column> columns = new ArrayList<>(fields.length) ;

        for(Field field : fields) {
            columns.add(new TableData.Column(field.getName() ,field.getName()));
        }

        return columns;
    }




    /**
     * 加载appInfo
     * @return
     */
    @Override public synchronized AppInfo loadAppInfo() {

//        if(appInfo != null )
//            return  appInfo ;

        Path path = getStorePath(APP_INFO_FILE) ;
        if(path== null || !path.toFile().exists()) {
            return  (appInfo = new AppInfo()) ;
        }

        FileInputStream inputStream = null ;
        try {

            inputStream = new FileInputStream(path.toFile());

            Properties prop      =  new Properties();

            prop.load(inputStream);

            //LOAD 加载prop到appinfo ,需要setAppinfo
            getProperties(appInfo,prop);
        } catch (Exception e) {
            throw new RuntimeException(e) ;
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



    /**
     *  存储appInfo
     * @param appInfo
     * @throws IOException
     */
    @Override
    public synchronized void storeAppInfo(AppInfo appInfo) throws IOException  {
        storeProperties(appInfo);
    }

    @Override
    public synchronized void storeMenus(List<Menu> menus) throws Exception {

    }

    private   void storeProperties(AppInfo appInfo) throws IOException {

        Properties prop   =  new Properties();
        FileOutputStream oFile = new FileOutputStream(getOrCreateFile(APP_INFO_FILE));
        try {
            setInfoProperties(appInfo,prop);

            prop.store(oFile, "change ");
        } catch (IOException e) {
            e.printStackTrace();
            oFile.close();
            throw new IOException("properties store error");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        oFile.close();
    }

    private static void getProperties(Object model,Properties prop ) throws Exception {
        if(model == null )
            throw new IllegalArgumentException("model can not be null") ;

        Field[] fields = model.getClass().getDeclaredFields() ;

        for(Field field : fields) {
            if(!field.isAccessible())
                field.setAccessible(true);
            field.set(model , prop.get(field.getName()));
        }
    }

    private static void setInfoProperties(Object model,Properties prop ) throws IOException, IllegalAccessException {
        if(model == null )
            throw new IllegalArgumentException("model can not be null") ;

        Field[] fields = model.getClass().getDeclaredFields();

        for(Field field : fields) {
            if(!field.isAccessible())
                field.setAccessible(true);
            Object val = field.get(model) ;
            if(val != null)
                prop.setProperty(field.getName() , (String)val) ;
        }
    }




    public static void main(String args[] ) throws Exception {

        DefaultModelConfig modelConfig = new DefaultModelConfig();

        List<Menu> tempMenus = modelConfig.loadMenus() ;

        System.out.println(tempMenus);
    }
}
