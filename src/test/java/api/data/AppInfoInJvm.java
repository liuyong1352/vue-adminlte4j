package api.data;

import com.jd.vue.adminlte.model.AppInfo;

/**
 * Created by bjliuyong on 2017/12/14.
 */
public class AppInfoInJvm {

    public static AppInfo getAppInfo() {
        AppInfo appInfo = new AppInfo() ;
        appInfo.setUserName("This is TestName");
        appInfo.setIndexUrl("/index.html");
        appInfo.setSignOutUrl("/login.html");
        appInfo.setProfileUrl("/profile.html");
        appInfo.setUserImgUrl("");
        appInfo.setLogoName("JD");
        appInfo.setLogoShortName("V-ALT");
        return  appInfo ;
    }
}
