package com.vue.adminlte4j.service;

import com.vue.adminlte4j.model.config.ConfigFormItem;
import com.vue.adminlte4j.model.config.ConfigModel;

import java.util.List;

/**
 * @author tangwei
 * @date 2018/9/24 22:46
 */
public interface ModelConfigService {

    /**
     * 搜索全部模型
     * @return
     */
    List<ConfigModel> getModelList() ;

    /**
     * 保存模型名称
     * @param configModel
     */
    void saveModel(ConfigModel configModel) ;

    void deleteModel(ConfigModel configModel) throws Exception;

    List<ConfigFormItem> get(String clsName) ;

    void save(String clsName, List<ConfigFormItem> configFormItems) ;

    void update(String clsName, List<ConfigFormItem> configFormItems);
}
