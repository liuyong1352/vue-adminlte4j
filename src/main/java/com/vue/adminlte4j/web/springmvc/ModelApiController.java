package com.vue.adminlte4j.web.springmvc;

import com.vue.adminlte4j.model.UIModel;
import com.vue.adminlte4j.model.config.ConfigFormItem;
import com.vue.adminlte4j.model.config.ConfigModel;
import com.vue.adminlte4j.web.config.BaseConfig;
import com.vue.adminlte4j.web.config.ModelApiConfig;
import org.springframework.web.bind.annotation.*;
import java.util.List;


public interface ModelApiController extends BaseConfig {


    @RequestMapping(value = "/admin/model/list" , method = RequestMethod.GET)
    @ResponseBody
    default UIModel getModelList() {
        return ModelApiConfig.getModelList(getModelConfigService());
    }

    @RequestMapping(value = "/admin/model/save" , method = RequestMethod.POST)
    @ResponseBody
    default UIModel saveModel(@RequestBody ConfigModel configModel) {
        return ModelApiConfig.saveModelList(getModelConfigService() , configModel);
    }

    @RequestMapping(value = "/admin/model/detail/get" , method = RequestMethod.GET)
    @ResponseBody
    default UIModel getModelDetail(ConfigModel configModel) {
        return ModelApiConfig.getModelDetail(getModelConfigService() ,configModel) ;
    }

    @RequestMapping(value = "/admin/model/detail/update/{modelName:.+}" , method = RequestMethod.POST)
    @ResponseBody
    default UIModel updateModelDetail(@PathVariable String modelName , @RequestBody List<ConfigFormItem> configFormItems) {
        return ModelApiConfig.updateModelDetail(getModelConfigService() ,modelName , configFormItems);
    }


    @RequestMapping(value = "/admin/model/lookup" , method = RequestMethod.GET)
    @ResponseBody
    default UIModel findModelDetail(String model ) {
        return ModelApiConfig.findModelDetail(this , model) ;
    }

    @RequestMapping(value = "/admin/model/delete" , method = RequestMethod.DELETE)
    @ResponseBody
    default UIModel deleteModel(@RequestBody  ConfigModel configModel) {
        return ModelApiConfig.deleteModel(getModelConfigService() , configModel) ;
    }

}
