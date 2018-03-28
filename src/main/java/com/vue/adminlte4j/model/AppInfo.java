package com.vue.adminlte4j.model;

import com.vue.adminlte4j.annotation.DictData;
import com.vue.adminlte4j.annotation.DictEntry;
import com.vue.adminlte4j.annotation.DictProvider;
import com.vue.adminlte4j.annotation.Form;
import com.vue.adminlte4j.annotation.UIDate;
import com.vue.adminlte4j.annotation.UIFormItem;
import com.vue.adminlte4j.model.form.DateType;
import com.vue.adminlte4j.model.form.FormItemType;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by bjliuyong on 2017/12/13.
 */
@Form(span = 4 )
public class AppInfo implements Serializable{

    @UIFormItem(label = "应用名称" )
    private String appName      ;

    @UIFormItem(label = "首页地址" )
    private String indexUrl     ;

    private String userName     ;
    private String userImgUrl   ;

    @UIFormItem(label = "用户profileUrl" )
    private String profileUrl   ;

    @UIFormItem(label = "登出Url")
    private String signOutUrl   ;

    @UIFormItem(label = "Logo名称")
    private String logoName     ;

    @UIFormItem(label = "Logo名称缩写")
    private String logoShortName;

    @UIDate(type = DateType.DATE)
    @com.alibaba.fastjson.annotation.JSONField(format="yyyy-MM-dd") //使用fastjson 格式化
    private Date created ;



    @UIFormItem(type = FormItemType.CHECKBOX)
    @DictData({
        @DictEntry("阅读") ,
        @DictEntry("读") ,
        @DictEntry("阅")
    })
    private String checkBox;

    public String getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(String checkBox) {
        this.checkBox = checkBox;
    }

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
