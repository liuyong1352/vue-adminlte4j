package com.vue.adminlte4j.model.builder;

import com.vue.adminlte4j.model.TableData;
import com.vue.adminlte4j.model.form.FormItem;
import com.vue.adminlte4j.model.form.FormModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by bjliuyong on 2018/4/23.
 */
public class TableBuilder {

    private TableData target = new TableData() ;
    private Map<String,Boolean> configKeys ;
    private Set<String> ignoreKeys ;
    private Class targetClass  ;

    /**
     * 构建builder实例
     * @param clazz
     * @return
     */
    public static  TableBuilder newBuilder(Class clazz){
        TableBuilder builder = new TableBuilder() ;
        builder.targetClass = clazz ;
        return  builder ;
    }

    /**
     * 设置是否分页
     * @param pageFlag
     * @return
     */
    public TableBuilder isPage(boolean pageFlag) {
        target.setPage(pageFlag);
        return this ;
    }

    /**
     * 设置总的条数
     * @param totalSize
     * @return
     */
    public TableBuilder totalSize(int totalSize) {
        target.setTotalSize(totalSize);
        return this ;
    }

    /**
     * 隐藏哪些字段
     * @param keys
     * @return
     */
    public TableBuilder hiddenColumn(String ... keys) {
        return toggleColumn(true , keys) ;
    }

    /**
     * 展示出哪些字段
     * @param keys
     * @return
     */
    public TableBuilder showColumn(String ... keys) {
        return toggleColumn(false , keys) ;
    }

    public TableBuilder toggleColumn(boolean flag , String ... keys ) {
        if(keys == null || keys.length <= 0 )
            return  this ;
        if(configKeys == null )
            configKeys = new HashMap<>() ;
        for (int i = 0; i < keys.length; i++) {
            configKeys.put(keys[i] , flag) ;
        }
        return this ;
    }

    /**
     * 忽略哪些字段
     * @param keys
     * @return
     */
    public TableBuilder ignoreColumn(String ... keys) {
        if(keys == null || keys.length <= 0 )
            return  this ;
        if(ignoreKeys == null )
            ignoreKeys = new HashSet<>() ;
        for (String key : keys) {
            ignoreKeys.add(key) ;
        }
        return this ;
    }

    public TableBuilder addColunm(FormItem formItem) {
        if(target.getFormItems() == null )
            target.setFormItems(new ArrayList<>());
        target.getFormItems().add(formItem) ;
        return  this  ;
    }

    public TableBuilder data(List datas) {
        target.setDataItems(datas);
        return this ;
    }


    public TableData build() {

        FormModel formModel = FormModelUtils.getFormModel(targetClass) ;
        List<FormItem> formItemList = formModel.getFormItems();
        List<FormItem> ret = new ArrayList<>(formItemList.size()) ;
        for(FormItem formItem : formItemList) {
            FormItem clone = formItem.clone() ;
            String key = clone.getKey() ;
            if(ignoreKeys != null && ignoreKeys.contains(key))
                continue;
            if(configKeys != null && configKeys.containsKey(key)) {
                clone.setHidden(configKeys.get(key));
            }
            ret.add(clone) ;
        }
        if(target.getFormItems() != null) {
            ret.addAll(target.getFormItems()) ;
        }
        target.setFormItems(ret);
        return target ;
    }
}
