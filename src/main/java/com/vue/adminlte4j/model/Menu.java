package com.vue.adminlte4j.model;

import com.vue.adminlte4j.annotation.UIFormItem;
import com.vue.adminlte4j.model.form.FormItemType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjliuyong on 2017/11/23.
 */
public class Menu implements Comparable<Menu> ,Serializable  {

    //@UIFormItem(hidden = true)
    private String id ;
    private String desc ;
    //@UIFormItem(hidden = true)
    private String pid ;
    private String url ;

    @UIFormItem(type = FormItemType.ICON_SELECTOR , label = "图标")
    private String icon ;
    /**
     * 排序字段 ,倒序排列
     */
    private int order ;

    @UIFormItem(ignore = true)
    private List<Menu> children ;

    public Menu(){

    }

    public Menu(String id, String desc , String url, String icon, int order) {
        this.id = id;
        this.desc = desc  ;
        this.url = url;
        this.icon = icon;
        this.order = order;
    }

    public void addChildMenu(Menu menu) {
        if(children == null)
            children = new ArrayList<>() ;
        children.add(menu) ;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    @Override public int compareTo(Menu o) {
        if(order > o.getOrder()) {
            return 1 ;
        }
        return -1 ;
    }


    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
