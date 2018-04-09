package com.vue.adminlte4j.model;

import com.vue.adminlte4j.model.builder.FormModelUtils;
import com.vue.adminlte4j.model.form.ExtInfo;
import com.vue.adminlte4j.model.form.FormItem;
import com.vue.adminlte4j.model.form.FormItemType;
import com.vue.adminlte4j.model.form.FormModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by bjliuyong on 2017/12/13.
 */
public class TableData<T> {

    private List<Column> columns = new ArrayList<>();
    private List<Object> dataItems = new ArrayList<>();

    private boolean isPage  = true;
    private int totalSize  ;

    public static <T> TableData<T> newInstance(Class<T> tClass) {
        TableData tableData =  new TableData<T>() ;
        FormModel model = FormModelUtils.getFormModel(tClass) ;
        List<FormItem> formItems = model.getFormItems() ;
        formItems.forEach(item->{
            Column column = new Column();
            column.setKey(item.getKey());
            column.setLabel(item.getLabel());
            column.setType(item.getType());
            column.setExtInfo(item.getExt());
            tableData.columns.add(column) ;
        });
        return tableData ;
    }

    public TableData<T> configDisplayColumn(Column column) {
        columns.add(column) ;
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

    public TableData<T> addAll(Collection<? extends T> c) {
        dataItems.addAll(c) ;
        return this ;
    }

    public List<Column>  getColumns() {
        return columns;
    }

    public List<Object> getDataItems() {
        return dataItems;
    }

    public static Column createColumn(String key , String lable) {
        Column column = new Column() ;
        column.setKey(key);
        column.setLabel(lable);
        return column ;
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

    public static class Column  {

        private String key  ;
        private String label ;
        private int  type = FormItemType.INPUT.getKey();
        private ExtInfo extInfo ;

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

        public ExtInfo getExtInfo() {
            return extInfo;
        }

        public void setExtInfo(ExtInfo extInfo) {
            this.extInfo = extInfo;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
