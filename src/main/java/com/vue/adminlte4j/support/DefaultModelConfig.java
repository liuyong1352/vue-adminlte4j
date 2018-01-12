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
    private Path       modelPath;
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
        Properties prop      =  new Properties();
        //相当于加上“user.dir”的绝对路径
        String fileName = "model.properties";
        modelPath = DefaultModelConfig.getWorkSpacePath(fileName);
        //文件不存在设置默认属性
        if(!checkModelFile(modelPath)) {
            appInfo = setAppInfo();
        }else{
            FileInputStream inputHandle = new FileInputStream(modelPath.toString());
            prop.load(inputHandle);
            appInfo.setUserName(prop.getProperty("userName"));
            appInfo.setIndexUrl(prop.getProperty("indexUrl"));
            appInfo.setSignOutUrl(prop.getProperty("signOutUrl"));
            appInfo.setProfileUrl(prop.getProperty("profileUrl"));
            appInfo.setUserImgUrl(prop.getProperty("userImgUrl"));
            appInfo.setLogoName(prop.getProperty("logoName"));
            appInfo.setLogoShortName(prop.getProperty("logoShortName"));
            inputHandle.close();
        }


        return appInfo;
    }

    @Override public void storeAppInfo(AppInfo appInfo) throws IOException {
        Properties prop   =  new Properties();
        if(!checkModelFile(modelPath)) {
            appInfo = setAppInfo();
        }
        FileOutputStream oFile = new FileOutputStream(modelPath.toString());
        try {
            //后改尝试遍历读取
            prop.setProperty("userName",appInfo.getUserName());
            prop.setProperty("indexUrl",appInfo.getIndexUrl());
            prop.setProperty("signOutUrl",appInfo.getSignOutUrl());
            prop.setProperty("profileUrl",appInfo.getProfileUrl());
            prop.setProperty("userImgUrl",appInfo.getUserImgUrl());
            prop.setProperty("logoName",appInfo.getLogoName());
            prop.setProperty("logoShortName",appInfo.getLogoShortName());

            prop.store(oFile, "change ");
        } catch (IOException e) {
            e.printStackTrace();
            oFile.close();
            throw new IOException("properties store error");
        }
        oFile.close();
    }


    public boolean checkModelFile(Path path) throws FileNotFoundException {
        // 如果文件不存在就创建
        File file = new File(path.toString());
        if (!file.exists()) {
            try {
                file.createNewFile();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                throw new FileNotFoundException("File Create failure");
            }
        }
        return true;
    }


    public AppInfo setAppInfo() {
        AppInfo appInfo = new AppInfo() ;

        appInfo.setUserName("This is TestName");
        appInfo.setIndexUrl("/index.html");
        appInfo.setLogoName("/login.html");
        appInfo.setProfileUrl("/profile.html");
        appInfo.setUserImgUrl("");
        appInfo.setLogoName("JD");
       appInfo.setLogoShortName("V-ALT");
        return appInfo;
    }

    public static void main(String args[] ) throws IOException {
        DefaultModelConfig dmg = new DefaultModelConfig();
        AppInfo appInfo;
        appInfo =dmg.loadAppInfo();
        appInfo.setUserName("Test OK");
        dmg.storeAppInfo(appInfo);

    }

}
