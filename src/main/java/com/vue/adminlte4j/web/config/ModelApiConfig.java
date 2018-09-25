package com.vue.adminlte4j.web.config;


import com.vue.adminlte4j.model.TableData;
import com.vue.adminlte4j.model.UIModel;
import com.vue.adminlte4j.model.builder.FormModelUtils;
import com.vue.adminlte4j.model.config.ConfigFormItem;
import com.vue.adminlte4j.model.config.ConfigModel;
import com.vue.adminlte4j.model.form.FormItem;
import com.vue.adminlte4j.model.form.FormItemType;
import com.vue.adminlte4j.model.form.FormModel;
import com.vue.adminlte4j.service.DictService;
import com.vue.adminlte4j.service.ModelConfigService;
import com.vue.adminlte4j.service.ServiceRegister;
import com.vue.adminlte4j.util.Utils;
import java.util.List;
import java.util.stream.Collectors;


public class ModelApiConfig {

    /**
     * 保存模型
     * @param modelConfigService
     * @param configModel
     * @return
     */
    public static UIModel saveModelList(ModelConfigService modelConfigService , ConfigModel configModel) {
        return Utils.run(()-> {
            configModel.validate();
            modelConfigService.saveModel(configModel) ;
        }) ;
    }

    /**
     * 查询模型的List文件内容
     * @param modelConfigService
     * @return
     */
    public static UIModel getModelList(ModelConfigService modelConfigService) {
        return Utils.call(()-> {
            List<ConfigModel> configModels = modelConfigService.getModelList() ;

            TableData tableData = new TableData() ;
            tableData.setDataItems(configModels);

            FormModel formModel = FormModelUtils.newFormModel(ConfigModel.class);
            formModel.getFormItems().forEach(formItem -> formItem.setHidden(false));
            formModel.get("id").setHidden(true);
            FormItem formItem = formModel.get("clazz") ;
            formItem.setType(FormItemType.SWITCH) ;
            //formModel.get("modelName").setHidden(false) ;

            tableData.setFormItems(formModel.getFormItems());
            tableData.setPage(false);
            return  UIModel.success().tableData(tableData) ;
        }) ;
    }

    public static UIModel getModelDetail(ModelConfigService modelConfigService , ConfigModel configModel) {
        return Utils.call(() -> {
            configModel.validate();
            String modelName = configModel.getModelName() ;
            List<ConfigFormItem> configFormItems = modelConfigService.get(modelName) ;

            FormModel configFormModel = FormModelUtils.newFormModel(ConfigFormItem.class) ;
            TableData tableData = new TableData() ;
            tableData.setPage(false);

            if(configFormItems == null && configModel.isClazz()) {
                Class cls = Class.forName(modelName) ;
                FormModel formModel = FormModelUtils.newFormModel(cls);
                configFormItems = formModel.getFormItems()
                    .stream()
                    .map(formItem -> new ConfigFormItem(formItem))
                    .collect(Collectors.toList());
            }

            tableData.setDataItems(configFormItems );
            return UIModel.success().tableData(tableData).put(configFormModel);
        }) ;
    }

    public static UIModel updateModelDetail(ModelConfigService modelConfigService, String modelName , List<ConfigFormItem> configFormItems) {
        return Utils.call(() -> {
            ConfigModel.validateModelName(modelName);
            modelConfigService.update(modelName , configFormItems);
            FormModelUtils.refreshModelCache(modelName);
            return UIModel.success().msg("更新成功");
        }) ;
    }

    public static UIModel findModelDetail(ServiceRegister serviceRegister , String model) {
        return Utils.call(() -> {
            Utils.validateBlank(model , "model can not be empty!");
            UIModel uiModel = UIModel.success() ;
            String array[] = model.split(",");
            for(String key : array) {
                FormModel formModel = findFormModel(serviceRegister ,key) ;
                if(formModel == null ) {
                    formModel = FormModelUtils.getFormModelByFullName(key) ;
                }
                uiModel.put(key , formModel) ;
            }
            return uiModel ;
        }) ;
    }

    /**
     * 根据模型名称 搜索模型
     * @param serviceRegister
     * @param modelName
     * @return
     */
    public static FormModel findFormModel(ServiceRegister serviceRegister , String modelName) {
        ConfigModel.validateModelName(modelName);
        List<ConfigFormItem> configFormItems = serviceRegister.getModelConfigService().get(modelName);
        if(configFormItems == null || configFormItems.isEmpty())
            return  null ;

        FormModel formModel = new FormModel() ;
        DictService dictService = serviceRegister.getDictService() ;

        configFormItems.forEach(configFormItem -> {
            FormItem formItem = configFormItem.convert() ;
            //config dict
            if(formItem.hasDict()){
                formItem.setDict(dictService.list(configFormItem.getDictName())) ;
            }
            formModel.getFormItems().add(formItem) ;
        });
        return formModel ;
    }



    public static UIModel deleteModel(ModelConfigService modelConfigService ,  ConfigModel configModel) {
        return Utils.call(() -> {
            ConfigModel.validateModelName(configModel.getModelName() );
            modelConfigService.deleteModel(configModel) ;
            FormModelUtils.refreshModelCache(configModel.getModelName());
            return UIModel.success().msg("delete successful!") ;
        }) ;
    }

}
