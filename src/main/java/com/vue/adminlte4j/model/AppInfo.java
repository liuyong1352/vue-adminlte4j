package com.vue.adminlte4j.model;

import com.vue.adminlte4j.annotation.UIFormItem;
import java.io.Serializable;

/**
 * Created by bjliuyong on 2017/12/13.
 */
public class AppInfo implements Serializable{

    @UIFormItem(label = "应用名称" )
    private String appName      ;

    @UIFormItem(label = "首页地址" )
    private String indexUrl     ;

    @UIFormItem(label = "用户信息Url" )
    private String profileUrl   ;

    @UIFormItem(label = "登出Url")
    private String signOutUrl   ;

    @UIFormItem(label = "Logo名称")
    private String logoName     ;

    @UIFormItem(label = "Logo名称缩写")
    private String logoShortName;

    @UIFormItem(label = "版权信息")
    private String copyright ;

    @UIFormItem(label = "版本信息")
    private String version ;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getSignOutUrl() {
        return signOutUrl;
    }

    public void setSignOutUrl(String signOutUrl) {
        this.signOutUrl = signOutUrl;
    }

    public String getLogoName() {
        return logoName;
    }

    public void setLogoName(String logoName) {
        this.logoName = logoName;
    }

    public String getLogoShortName() {
        return logoShortName;
    }

    public void setLogoShortName(String logoShortName) {
        this.logoShortName = logoShortName;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}