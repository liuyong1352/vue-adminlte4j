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
    private Properties prop      =  new Properties();
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
        Boolean fileStatus =false;
        AppInfo appInfo = new AppInfo();
        //相当于加上“user.dir”的绝对路径
        String fileName = "model.properties";
        modelPath = DefaultModelConfig.getWorkSpacePath(fileName);
        fileStatus = checkFile(modelPath,appInfo);
        //文件不存在设置默认属性
        if(!fileStatus) {
            setProperties();

        }

        FileInputStream finput = new FileInputStream(modelPath.toString());
        InputStream in = new BufferedInputStream(finput);
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

    @Override public void storeAppInfo() throws IOException {
        FileOutputStream oFile = new FileOutputStream(modelPath.toString());
        try {
            prop.store(oFile, "change ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        oFile.close();
    }

    @Override
    public boolean checkFile(Path path,AppInfo appInfo) {

        // 如果文件不存在就创建
        File file = new File(path.toString());
        if (!file.exists()) {
            try {
                file.createNewFile();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public void setProperties() {
        AppInfo appInfo = new AppInfo() ;
        prop.setProperty("userName","This is TestName");
        prop.setProperty("indexUrl","/index.html");
        prop.setProperty("signOutUrl","/login.html");
        prop.setProperty("profileUrl","/profile.html");
        prop.setProperty("userImgUrl","");
        prop.setProperty("logoName","JD");
        prop.setProperty("logoShortName","V-ALT");
    }

    public static void main(String args[] ) throws IOException {
        DefaultModelConfig dmg = new DefaultModelConfig();

        AppInfo appInfo;

        appInfo =dmg.loadAppInfo();
        appInfo.setUserName("Test OK");
        dmg.prop.setProperty("userName",appInfo.getUserName());
        dmg.storeAppInfo();

    }

}
