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

/**
 * Created by bjliuyong on 2017/12/26.
 */

public class DefaultModelConfig extends BaseStore implements IModelConfig{


    private Set<Class> typeSet   = new HashSet<>() ;

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
    public synchronized void storeMenus(List<Menu> menus) throws IOException, IllegalAccessException {
        storeMenuProperties(menus);
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

    private  static void setMenusProperties(List<Menu> menus,Properties prop) throws IllegalAccessException {
        if(menus == null )
            throw new IllegalArgumentException("model can not be null") ;
        int menuCount = 1;
        for(Menu menu : menus){

            if(menu.getChildren() == null){
                setMenu(menu,prop,"menu"+menuCount+".");
            }else{

                int kidCount = 1;
                for(Menu kidMenu:menu.getChildren()){
                    setMenu(kidMenu,prop,"menu"+menuCount+".kid"+kidCount+".");
                    kidCount++;
                }
            }
            menuCount++;
        }
    }

    private static void setMenu(Object model,Properties prop,String name) throws IllegalAccessException {
        //       model.getClass().isArray()
        Field[] fields = model.getClass().getDeclaredFields();
        String value;

        for(Field field : fields) {
            if(!field.isAccessible())
                field.setAccessible(true);

            value= String.valueOf(field.get(model)) ;

            if(value != null &&  !field.getName().equals("children"))
                prop.setProperty(name+field.getName(),value);
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

        Path path= isDev ? getWorkSpacePath(MENU_ITEM_FILE) : loadFromClassPath(MENU_ITEM_FILE);

        if(path == null || !path.toFile().exists()){
            return menus;
        }
        FileInputStream inputStream = null ;
        try {
            inputStream = new FileInputStream(path.toString());
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            return (List<Menu>)objectInputStream.readObject() ;
        } finally {
            inputStream.close();
        }

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
        while(length>0) {
            boolean kidFlag = true; //避免重复添加kidMenu
            Menu menu = new Menu();

            Field[] fields = menu.getClass().getDeclaredFields();
            for(Field field : fields) {
                //这里先假定menu的desc不为空
                if(kidFlag){

                    int result = getKidMenus(menu,prop, getMenuName(menuCount));

                    if(result > 0){
                        //这里返回KidMenu操作的元素个数 然后继续
                        length -= result;
                        kidFlag = false;
                    }

                }
                name = getMenuName(menuCount)+field.getName();

                //不再获取children的值 已在KidMenus中操作
                if(name.indexOf("children")> 0) continue;

                value = (String) prop.get(name);
                //这里要滤过children属性 不然会覆盖先前的
                if(value != null)length--;


                setField(value,menu,field);
            }
            menus.add(menu);
            menuCount++;   //进行menu2

        }


    }


    public int getKidMenus(Menu menu,Properties prop,String name) throws IllegalAccessException {
        //这里是否可以迭代 这里返回获取了几个元素 减length  如果不存在第一个子菜单 或者没有写描述 直接返回0
        int count = 0;
        String kidName;

        if(prop.get(name+"kid1"+".desc")==null) return 0;

        int menuNumber = 1;
        while(true){

            kidName = name+"kid"+menuNumber+".desc";
            if(prop.get(kidName)==null) {
                break;
            }

            Menu kidMenu = new Menu();
            Field[] kidfields = kidMenu.getClass().getDeclaredFields();

            for(Field kidField:kidfields){
                kidName = name+"kid"+menuNumber+"."+ kidField.getName();
                String value = (String) prop.get(kidName);
                if(value == null) continue;
                count++;  //配置文件存在才减减

                setField(value,kidMenu,kidField);
            }
            menu.addChildMenu(kidMenu);
            menuNumber++;
        }
        return count;
    }

    public void setField(String value,Menu menu,Field field) throws IllegalAccessException {
        //共同的
        //开启权限

        String type = field.getType().getName();
        if(!field.isAccessible())
            field.setAccessible(true);

        if(type.equals("int")){
            field.set(menu ,Integer.parseInt(value));
        }else{
            field.set(menu ,value);
        }

    }




    private String getMenuName(int i){
        return "menu"+i+".";
    }


    /**
     * 流的关闭顺序：先打开的后关，后打开的先关
     *  否则有可能出现java.io.IOException: Stream closed异常
     * @return
     * @throws IOException
     *
     */
    public List<Menu> loadMenus1() throws IOException, IllegalAccessException {

        Path path= isDev ? getWorkSpacePath(MENU_ITEM_FILE) : loadFromClassPath(MENU_ITEM_FILE);

        if(path == null || !path.toFile().exists()){
            return menus;
        }
        //读取文件流
        FileInputStream fileStream = new FileInputStream(path.toString());
        //这里防止中文乱码
        InputStreamReader reader = new InputStreamReader(fileStream,"utf-8");
        BufferedReader   buffer = new BufferedReader(reader);

        String lines = null;
        String[] arrays = null;
        while( (lines=buffer.readLine()) != null){
            //正则是取消多余空格或者tab键
            arrays = lines.split("\\s+");
            //menu id  icon url  order  desc   pid
            int length = 1;
            while(length < arrays.length) {
                Menu menu = new Menu();
                Field[] fields = menu.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (!field.isAccessible())
                        field.setAccessible(true);
                    field.set(menu, arrays[length]);
                    length++;
                }
            }

        }


        //关闭流 原因很简单 相互占用着 最先的关闭 后面两个依赖第一个的流 所以
        buffer.close();
        reader.close();
        fileStream.close();



        return menus;
    }

    public static void main(String args[] ) throws IOException, IllegalAccessException {

        DefaultModelConfig modelConfig = new DefaultModelConfig();
        List<Menu> tempMenus = new ArrayList<>();
     //   tempMenus = modelConfig.loadMenus1();

//        Menu testMenu = new Menu();
//        testMenu.setIcon("111");
//        testMenu.setDesc("222");
//
//
//        tempMenus.set(1,testMenu);
//        modelConfig.storeMenus(tempMenus);

    }
}
