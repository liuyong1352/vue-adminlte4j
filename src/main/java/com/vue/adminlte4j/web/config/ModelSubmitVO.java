package com.vue.adminlte4j.web.config;

import com.vue.adminlte4j.model.config.ConfigFormItem;

import java.util.List;

/**
 * Created by bjliuyong on 2018/5/28.
 */
public class ModelSubmitVO {

    private String clsName ;

    private List<ConfigFormItem> configFormItems ;

    public String getClsName() {
        return clsName;
    }

    public void setClsName(String clsName) {
        this.clsName = clsName;
    }

    public List<ConfigFormItem> getConfigFormItems() {
        return configFormItems;
    }

    public void setConfigFormItems(List<ConfigFormItem> configFormItems) {
        this.configFormItems = configFormItems;
    }
}
