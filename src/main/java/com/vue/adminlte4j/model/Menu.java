package com.vue.adminlte4j.model;

import com.vue.adminlte4j.annotation.UIFormItem;
import com.vue.adminlte4j.annotation.Validate;
import com.vue.adminlte4j.model.builder.FormModelBuilder;
import com.vue.adminlte4j.model.form.FormItem;
import com.vue.adminlte4j.model.form.FormItemType;

import com.vue.adminlte4j.model.form.FormModel;
import com.vue.adminlte4j.model.form.ValidateType;
import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjliuyong on 2017/11/23.
 */
public class Menu implements Comparable<Menu> ,Serializable ,ITreeNode ,FormModelBuilder {

    private String id ;
    private String pid ;

    @UIFormItem
    @Validate
    private String desc ;

    @UIFormItem
    @Validate
    private String url ;

    @UIFormItem(type = FormItemType.ICON_SELECTOR , label = "图标" )
    @Validate
    private String icon ;

    /**
     * 排序字段 ,倒序排列
     */
    private int order ;
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
        menu.setPid(id);
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

    @Override public String getText() {
        return this.getDesc();
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
        } else if(order == o.getOrder()) {
            return id.compareTo(o.getId());
        }
        return -1 ;
    }


    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Menu clone() {
        Menu menu = new Menu() ;
        menu.id = id ;
        menu.pid = pid ;
        menu.icon = icon ;
        menu.url = url ;
        menu.desc = desc ;
        menu.order = order ;
        return  menu ;
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("Menu{");
        sb.append("id='").append(id).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", pid='").append(pid).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", icon='").append(icon).append('\'');
        sb.append(", order=").append(order);
        sb.append(", children=").append(children);
        sb.append('}');
        return sb.toString();
    }


    @Override public void config(FormModel formModel) {
        FormItem formItem = new FormItem();
    }
}
