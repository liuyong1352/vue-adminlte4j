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
    private static final String MENU_ITEM_FILE = "menu_items.s";

    private   AppInfo appInfo ;
    private   List<Menu> menus = new ArrayList<>();


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
            setProperties(appInfo,prop);

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

    /**
     * 得到当前工程路径
     * @return
     */
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

    private static void setProperties(Object model,Properties prop ) throws Exception {
        if(model == null )
            throw new IllegalArgumentException("model can not be null") ;

        Field[] fields = model.getClass().getDeclaredFields() ;

        for(Field field : fields) {
            if(!field.isAccessible())
                field.setAccessible(true);
            prop.setProperty(field.getName(),(String) field.get(model));
        }
    }




    @Override
    public List<Menu> loadMenus() {

        Path path=isDevMode()?getWorkSpacePath(MENU_ITEM_FILE) : loadFromClassPath(MENU_ITEM_FILE);

        //文件不存在 或者路径为空 则直接返回原先的值
        if(path == null || !path.toFile().exists()){
            return menus;
        }
        try {
            FileInputStream inputStream = new FileInputStream(path.toString());
            Properties prop = new Properties();
            prop.load(inputStream);

            getMenus(prop);

        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }


        //先获得路径 判断路径文件是否存在
        //然后创建流
        return null;
    }

    /**
     * 从menu_item.s读取值 放到menus中
     * @param prop
     */
    public void getMenus(Properties prop) throws IllegalAccessException {

        int menuCount = 1;
        int length =prop.size();
        String value;
        String name;
        String type;
        while(length>0) {
            boolean kidFlag = true; //避免重复添加kidMenu
            Menu menu = new Menu();

            Field[] fields = menu.getClass().getDeclaredFields();
            for(Field field : fields) {

                //这里先假定menu的desc不为空
                if(kidFlag){

                    int result = getKidMenus(menu,prop, getMenuName(menuCount));

                    if(result > 0){
                        length -= result;
                        kidFlag = false;
                    }

                }

                if(!field.isAccessible())
                    field.setAccessible(true);

                name = getMenuName(menuCount)+field.getName();

                if(name.indexOf("children")> 0) continue;

                type = field.getType().getName();
                value = (String) prop.get(name);
                //这里要滤过children属性 不然会覆盖先前的
                if(value != null)length--;  //配置文件存在才减减

                //order是int 添加一种情况吧
                if(type.equals("int")){
                    field.set(menu ,Integer.parseInt(value));
                }else{
                    field.set(menu ,value);
                }

            }
            menus.add(menu);
            menuCount++;   //进行menu2

        }


    }


    public int getKidMenus(Menu menu,Properties prop,String name) throws IllegalAccessException {
        //这里是否可以迭代 这里返回获取了几个元素 减length  如果不存在第一个子菜单 或者没有写描述 直接返回0
        int count = 0;
        String kidName = name+"kid1"+".desc";
        if(prop.get(kidName)==null) return 0;

        boolean flag =true;
        int menuNumber = 1;
        while(flag){

            kidName = name+"kid"+menuNumber+".desc";
            if(prop.get(kidName)==null) {
                flag = false;
                break;
            }

            Menu kidMenu = new Menu();
            Field[] kidfields = menu.getClass().getDeclaredFields();



            for(Field kidField:kidfields){
                kidName = name+"kid"+menuNumber+"."+ kidField.getName();
                String value = (String) prop.get(kidName);
                if(value == null) continue;


                count++;  //配置文件存在才减减
                String type = kidField.getType().getName();


                //开启权限
                if(!kidField.isAccessible())
                    kidField.setAccessible(true);

                if(type.equals("int")){
                    kidField.set(kidMenu ,Integer.parseInt(value));
                }else{
                    kidField.set(kidMenu ,value);
                }
            }
            menu.addChildMenu(kidMenu);
            menuNumber++;
        }


        return count;
    }



    private String getKidMenuName(int i,int j){
        return "menu"+i+".kid"+j+".";
    }

    private String getMenuName(int i){
        return "menu"+i+".";
    }




    public static void main(String args[] ) throws IOException  {

        DefaultModelConfig modelConfig = new DefaultModelConfig();
        List<Menu> tempMenus = new ArrayList<>();
        tempMenus = modelConfig.loadMenus();



//        AppInfo appInfo= modelConfig.loadAppInfo();
//        System.out.println(appInfo.getUserName());
//        appInfo.setUserName("Test 336");
//        modelConfig.storeAppInfo(appInfo);

    }
}
