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

    private Set<Class> typeSet = new HashSet<>() ;
    private  Properties prop = new Properties();
    private    Path modelPath;
    private static Path getWorkSpacePath(String dir) {
        String userDir = System.getProperty("user.dir") ;
        Path path = Paths.get(userDir  , "src" , "main" , "resources", dir) ;
        return path ;
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
    public List<Menu> loadMenu() {
        return null;
    }

    @Override public AppInfo loadAppInfo() throws IOException {
        AppInfo appInfo = new AppInfo();
        //相当于加上“user.dir”的绝对路径
        modelPath = DefaultModelConfig.getWorkSpacePath("model.properties");
        InputStream in = new BufferedInputStream(new FileInputStream(modelPath.toString()));
        prop.load(in);
        appInfo.setUserName(prop.getProperty("userName"));
        appInfo.setIndexUrl(prop.getProperty("indexUrl"));
        appInfo.setSignOutUrl(prop.getProperty("signOutUrl"));
        appInfo.setProfileUrl(prop.getProperty("profileUrl"));
        appInfo.setUserImgUrl(prop.getProperty("userImgUrl"));
        appInfo.setLogoName(prop.getProperty("logoName"));
        appInfo.setLogoShortName(prop.getProperty("logoShortName"));
        return appInfo;
    }

    @Override public void storeAppInfo(AppInfo appInfo) throws IOException {

      //  FileOutputStream oFile = new FileOutputStream(modelPath.toString(), true);//true表示追加打开
        FileOutputStream oFile = new FileOutputStream(modelPath.toString());
        prop.setProperty("userName",appInfo.getUserName());
        prop.setProperty("indexUrl",appInfo.getIndexUrl());
        prop.setProperty("signOutUrl",appInfo.getSignOutUrl());
        prop.setProperty("profileUrl",appInfo.getProfileUrl());
        prop.setProperty("userImgUrl",appInfo.getUserImgUrl());
        prop.setProperty("logoName",appInfo.getLogoName());
        prop.setProperty("logoShortName",appInfo.getLogoShortName());
        try {
            prop.store(oFile, "change Model properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
        oFile.close();
    }

    public static void main(String args[] ) throws IOException {
        DefaultModelConfig dmg = new DefaultModelConfig();

        AppInfo appInfo;

        appInfo =dmg.loadAppInfo();
        appInfo.setUserName("Test Change");
        dmg.storeAppInfo(appInfo);

    }

}
