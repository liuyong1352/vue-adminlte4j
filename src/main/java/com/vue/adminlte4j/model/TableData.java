package com.vue.adminlte4j.model;

import com.vue.adminlte4j.model.form.FormItem;

import java.util.List;

/**
 * Created by bjliuyong on 2017/12/13.
 */
public class TableData {

    private List<Object> dataItems  ;
    private List<FormItem> formItems ;

    private boolean isPage  = true;
    private int pageSize = 20 ;
    private int totalSize  ;

    public List<Object> getDataItems() {
        return dataItems;
    }

    public void setDataItems(List dataItems) {
        this.dataItems = dataItems;
    }

    public List<FormItem> getFormItems() {
        return formItems;
    }

    public void setFormItems(List<FormItem> formItems) {
        this.formItems = formItems;
    }

    public boolean isPage() {
        return isPage;
    }

    public void setPage(boolean page) {
        isPage = page;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("TableData{");
        sb.append("dataItems=").append(dataItems);
        sb.append(", formItems=").append(formItems);
        sb.append(", isPage=").append(isPage);
        sb.append(", totalSize=").append(totalSize);
        sb.append('}');
        return sb.toString();
    }


}
