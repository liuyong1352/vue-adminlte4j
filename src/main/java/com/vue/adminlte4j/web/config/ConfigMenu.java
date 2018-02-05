package com.vue.adminlte4j.web.config;

import com.vue.adminlte4j.model.AppInfo;
import com.vue.adminlte4j.model.Menu;
import com.vue.adminlte4j.support.DefaultModelConfig;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConfigMenu extends DefaultModelConfig{
    private static final String MENU_ITEM_FILE = "menu_items.s";

    private   List<Menu> menus = new ArrayList<>();

    private static final boolean isDev = false;

    public Menu add(Menu menu) {
        return null ;
    }

    public boolean delete(String id) {
        return true ;
    }

    public boolean update(Menu menu) {
        return true ;
    }

    public List<Menu> list() {
        return null ;
    }

    private static void writeTitleToFile(BufferedWriter bw) throws IOException {
        bw.write("menu id  icon url  order  desc  pid");
        bw.newLine();
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


    /**
     * 流的关闭顺序：先打开的后关，后打开的先关
     *  否则有可能出现java.io.IOException: Stream closed异常
     * @return
     * @throws IOException
     *
     */
    public List<Menu> loadMenus() throws IOException, IllegalAccessException {

        Path path= getWorkSpacePath(MENU_ITEM_FILE);

        if(path == null || !path.toFile().exists()){
            return menus;
        }
        //读取文件流
        FileInputStream fileStream = new FileInputStream(path.toString());

        InputStreamReader reader = new InputStreamReader(fileStream,"utf-8");
        BufferedReader buffer = new BufferedReader(reader);

        String lines;
        String[] arrays;
        //跳过第一行描述

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
    public static void main(String[] args) throws IOException, IllegalAccessException {
        ConfigMenu configMenu = new ConfigMenu();
        List<Menu> tempMenus;
        tempMenus = configMenu.loadMenus();

        Menu testMenu = new Menu();
        testMenu.setIcon("333");
        testMenu.setDesc("444");


        tempMenus.set(1,testMenu);
        configMenu.storeMenus(tempMenus);
    }
}
