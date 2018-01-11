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
        Properties pro = new Properties();
        //相当于加上“user.dir”的绝对路径
        Path path = DefaultModelConfig.getWorkSpacePath("model.properties");
        InputStream in = new BufferedInputStream(new FileInputStream(path.toString()));
        pro.load(in);
        appInfo.setUserName(pro.getProperty("userName"));
        appInfo.setIndexUrl(pro.getProperty("indexUrl"));
        appInfo.setSignOutUrl(pro.getProperty("signOutUrl"));
        appInfo.setProfileUrl(pro.getProperty("profileUrl"));
        appInfo.setUserImgUrl(pro.getProperty("userImgUrl"));
        appInfo.setLogoName(pro.getProperty("logoName"));
        appInfo.setLogoShortName(pro.getProperty("logoShortName"));
        return appInfo;
    }

    @Override public void storeAppInfo(AppInfo appInfo) {
        return ;
    }

    public static void main(String args[] ) throws IOException {
        DefaultModelConfig defaultModelConfig = new DefaultModelConfig() ;
        AppInfo appInfo = defaultModelConfig.loadAppInfo() ;

        appInfo.setUserName("test");

        defaultModelConfig.storeAppInfo(appInfo);

    }

}
