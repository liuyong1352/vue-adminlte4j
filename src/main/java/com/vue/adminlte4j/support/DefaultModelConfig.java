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

    private static final boolean isDev  ;

    static {

        boolean flag = getJavaResources().toFile().exists() ;

        if(!flag) {
            String userDir = System.getProperty("user.dir") ;
            Path path = Paths.get(userDir  , "src" , "main" , "java") ;
            flag = path.toFile().exists() ;
        }

        isDev = flag ;
    }

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
    public List<Menu> loadMenus() {
        return null;
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




    private static File getStoreFile(String type) throws IOException {
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



    /**
     * 得到当前工程路径
     * @return
     */
    private static Path getJavaResources() {
        String userDir = System.getProperty("user.dir") ;
        Path path = Paths.get(userDir  , "src" , "main" , "resources") ;
        return  path ;
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

        String lines;
        String[] arrays;
        //跳过第一行
        lines = buffer.readLine();
        while( (lines=buffer.readLine()) != null){
            //正则是取消多余空格或者tab键
            arrays = lines.split("\\s+");
            //menu id  icon url  order  desc   pid
            int length = 1;

            Menu menu = new Menu();
            length += setMenus(length,menu,arrays);
            //主菜单添加后 还有元素是子菜单
            while(length < arrays.length) {

                Menu kidMenu = new Menu();
                length +=setMenus(length,kidMenu,arrays);
                menu.addChildMenu(kidMenu);

            }
            menus.add(menu);
        }


        //关闭流 原因很简单 相互占用着 最先的关闭 后面两个依赖第一个的流 所以
        buffer.close();
        reader.close();
        fileStream.close();



        return menus;
    }

    private static int setMenus(int index,Menu menu,String[] arrays) throws IllegalAccessException {
        int length = 1;
        Field[] Fields = menu.getClass().getDeclaredFields();
        String name;
        String type;

        for(Field field : Fields){

            name = field.getName();
            type = field.getType().getName();

            if (!field.isAccessible())
                field.setAccessible(true);

            if(!name.equals("children")) {
                if(type.equals("int")){
                    field.set(menu ,Integer.parseInt(arrays[index]));
                }else{
                    field.set(menu ,arrays[index]);
                }
                length++;
                index++;
            }
        }
        return length;
    }


    private static void writeTitleToFile(BufferedWriter bw) throws IOException {
        bw.write("menu id  icon url  order  desc  pid");
        bw.newLine();
    }

    @Override
    public   void  storeMenus(List<Menu> menus) throws IOException, IllegalAccessException {

        FileOutputStream oFile = new FileOutputStream(getStoreFile(MENU_ITEM_FILE));
        OutputStreamWriter osw = new OutputStreamWriter(oFile,"utf-8");
        BufferedWriter bw = new BufferedWriter(osw);


        writeTitleToFile(bw);

        //开始处理数据
        for(Menu menu: menus){
            Field[] fields = menu.getClass().getDeclaredFields();

            for(Field field : fields) {
                String name = field.getName();


                if(!field.isAccessible())
                    field.setAccessible(true);
                Object value = field.get(menu);
                if(name.equals("children")){

                    if( menu.getChildren() != null) {
                        for (Menu kidMenu : menu.getChildren()) {

                            Field[] kidFields = kidMenu.getClass().getDeclaredFields();

                            for (Field kidfield : kidFields) {

                                if (!kidfield.isAccessible())
                                    kidfield.setAccessible(true);

                                Object kidvalue = kidfield.get(kidMenu);
                                if (kidvalue != null) bw.write(kidvalue + " ");
                            }
                        }
                    }

                }
                if(value != null) bw.write(value+" ");

            }
            bw.newLine();
        }

        bw.close();
        osw.close();
        oFile.close();
    }





    private static void setMenusProperties1(List<Menu> menus ,Properties prop){
        if(menus == null )
            throw new IllegalArgumentException("model can not be null") ;
    }


    public static void main(String args[] ) throws IOException, IllegalAccessException {

        DefaultModelConfig modelConfig = new DefaultModelConfig();
        List<Menu> tempMenus = new ArrayList<>();
        tempMenus = modelConfig.loadMenus1();

        Menu testMenu = new Menu();
        testMenu.setIcon("111");
        testMenu.setDesc("222");


        tempMenus.set(1,testMenu);
        modelConfig.storeMenus(tempMenus);

    }
}
