package com.vue.adminlte4j.support;


import com.vue.adminlte4j.model.AppInfo;
import com.vue.adminlte4j.model.Menu;
import com.vue.adminlte4j.model.TableData;
import com.vue.adminlte4j.support.store.BaseStore;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bjliuyong on 2017/12/26.
 */

public class DefaultModelConfig extends BaseStore implements IModelConfig{


    private Set<Class> typeSet   = new HashSet<>() ;

    private   AppInfo appInfo ;
    private   List<Menu> menus ;

    private AtomicInteger menuIdGenerator ;


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

        Path path = isDev ? getWorkSpacePath(APP_INFO_FILE) : loadFromClassPath(APP_INFO_FILE)  ;
        appInfo = new AppInfo() ;

        if(path== null || !path.toFile().exists()) {
            return  appInfo ;
        }

        FileInputStream inputStream = null ;
        try {

            inputStream = new FileInputStream(path.toFile());

            Properties prop      =  new Properties();

            prop.load(inputStream);

            //LOAD 加载prop到appinfo ,需要setAppinfo
            getProperties(appInfo,prop);
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
        storeMenuProperties(menus);
    }

    @Override
    public void deleteMenu(String id) throws Exception {

        if(menus == null) {
            return;
        }

        if( deleteInMem(id , menus) ) {
            storeMenus(menus);
        }





    }

    private boolean deleteInMem(String id , List<Menu> menus ) {

        if(menus == null || menus.isEmpty())
            return  false ;

        for(int i = 0 ; i < menus.size() ; i++ ) {
            Menu m = menus.get(i) ;
            if (m.getId().equals(id)) {
                menus.remove(i);
                return  true ;
            }

            if(deleteInMem(id , m.getChildren()))
                return  true ;
        }

        return false ;
    }

    @Override
    public Menu addMenu(Menu menu) throws Exception {
        int id = menuIdGenerator.getAndIncrement() ;
        menu.setId(id + "");
        String pid = menu.getPid() ;
        if(pid != null && !pid.isEmpty() ) {
            for(Menu m : menus ) {
                if(m.getId().equals(menu.getPid()))
                    m.addChildMenu(menu);
            }
        } else {
            menus.add(menu) ;
        }

        storeMenus(menus);
        return menu;
    }

    private static  void storeMenuProperties(List<Menu> menus) throws IOException {

        FileOutputStream output = null;
        try {
            output = new FileOutputStream(getStoreFile(MENU_ITEM_FILE));
            ObjectOutputStream outputStream = new ObjectOutputStream(output) ;
            outputStream.writeObject(menus);
        } catch (IOException e) {
            throw e ;
        }  finally {
            output.close();
        }


    }

    public static File getStoreFile(String type) throws IOException {
        if(!isDev)
            throw new RuntimeException("current not in dev Mode !") ;

        File storeFile = getWorkSpacePath(type).toFile() ;

        File wp = getWorkSpacePath("").toFile() ;

        if(!wp.exists()) {
            wp.mkdir() ;
        }

        if(!storeFile.exists()) {
            storeFile.createNewFile() ;
        }

        return storeFile;

    }

    private  static  void storeProperties(AppInfo appInfo) throws IOException {

        Properties prop   =  new Properties();
        FileOutputStream oFile = new FileOutputStream(getStoreFile(APP_INFO_FILE));
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






    public static Path getWorkSpacePath(String dir) {
        Path path = getJavaResources() ;
        return Paths.get(path.toString() , WORKSPACE_DIR , dir) ;
    }

    public static Path loadFromClassPath(String fileName) {
        try {
            String configPath = DefaultModelConfig.class.getClassLoader().getResource(WORKSPACE_DIR).getPath();
            return Paths.get(configPath , fileName) ;
        } catch (Exception e) {
            return null ;
        }

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




    @Override
    public List<Menu> loadMenus() throws Exception {

        if(menus == null )
            menus = loadFromFile() ;

        return  menus ;

    }


    private List<Menu> loadFromFile() throws  Exception {

        Path path= isDev ? getWorkSpacePath(MENU_ITEM_FILE) : loadFromClassPath(MENU_ITEM_FILE);

        if(path == null || !path.toFile().exists()){
            menus = new ArrayList<>() ;
            if( menuIdGenerator == null ) {
                initMenuIdGenerator(menus);
            }
            return menus;
        }

        FileInputStream inputStream = null ;
        try {
            inputStream = new FileInputStream(path.toString());
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            menus = (List<Menu>)objectInputStream.readObject() ;

            if( menuIdGenerator == null ) {
                initMenuIdGenerator(menus);
            }

            return menus ;
        } finally {
            inputStream.close();
        }

    }

    private void initMenuIdGenerator(List<Menu> menus) {
        int maxId = 0 ;
        for(Menu menu : menus) {
            int id = Integer.parseInt(menu.getId()) ;
            if(id > maxId)
                maxId = id ;
        }
        menuIdGenerator = new AtomicInteger(maxId + 1 ) ;
    }

    public static void main(String args[] ) throws Exception {

        DefaultModelConfig modelConfig = new DefaultModelConfig();

        List<Menu> tempMenus = modelConfig.loadMenus() ;

        System.out.println(tempMenus);
    }
}
