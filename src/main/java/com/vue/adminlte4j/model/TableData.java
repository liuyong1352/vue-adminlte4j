package com.vue.adminlte4j.model;

import com.vue.adminlte4j.support.ModelConfigManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by bjliuyong on 2017/12/13.
 */
public class TableData<T> {

    public static final String KEY_TAG = "key" ;
    public static final String TITLE_TAG = "title" ;

    private List<Column> columns = new ArrayList<>();
    private List<Object> dataItems = new ArrayList<>();

    private boolean isPage  = true;
    private int totalSize  ;

    public void configDisplayColumn(Column column) {
        columns.add(column) ;
    }

    public void configDisplayColumn(Class type) {
        this.columns.addAll(ModelConfigManager.getModelConfigColumns(type));
    }

    public void addData(T data) {
        dataItems.add(data);
    }

    public void addAll(List<T> datas) {
        dataItems.addAll(datas) ;
    }

    public List<Column>  getColumns() {
        return columns;
    }

    public List<Object> getDataItems() {
        return dataItems;
    }

    public static Column createColumn(String key , String lable) {
        return new Column(key , lable) ;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public boolean isPage() {
        return isPage;
    }

    public void setPage(boolean page) {
        isPage = page;
    }

    public static class Column {

        private String key  ;
        private String label ;

        public Column(String key, String label) {
            this.key = key;
            this.label = label;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}
