package com.vue.adminlte4j.model;

import com.vue.adminlte4j.support.ModelConfigManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjliuyong on 2017/12/13.
 */
public class TableData<T> {

    private List<Column> columns = new ArrayList<>();
    private List<Object> dataItems = new ArrayList<>();

    private boolean isPage  = true;
    private int totalSize  ;

    public TableData<T> configDisplayColumn(Column column) {
        columns.add(column) ;
        return this ;
    }

    public TableData<T> configDisplayColumn(Class type) {
        this.columns.addAll(ModelConfigManager.getModelConfigColumns(type));
        return this ;
    }

    public TableData<T> removeDisplayColumn(String key) {
        for(int i = 0 ; i < columns.size() ; i++) {
            if(columns.get(i).getKey().equals(key))
                columns.remove(i) ;
        }
        return this ;
    }

    public TableData<T> addData(T data) {
        dataItems.add(data);
        return this ;
    }

    public TableData<T> addAll(List<T> datas) {
        dataItems.addAll(datas) ;
        return this ;
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

    public TableData<T> setTotalSize(int totalSize) {
        this.totalSize = totalSize;
        return this ;
    }

    public boolean isPage() {
        return isPage;
    }

    public TableData<T> setPage(boolean page) {
        isPage = page;
        return this ;
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
