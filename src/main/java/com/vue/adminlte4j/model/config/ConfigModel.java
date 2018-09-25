package com.vue.adminlte4j.model.config;

import com.vue.adminlte4j.support.AdminRuntimeException;
import com.vue.adminlte4j.util.ReflectUtils;
import com.vue.adminlte4j.util.Utils;

/**
 * @author tangwei
 * @date 2018/9/25 10:36
 */
public class ConfigModel {

    private long id  ;
    private String modelName ;
    private String desc ;
    private boolean clazz ;

    public static void validateModelName(String modelName) {
        Utils.validateBlank(modelName , "model name can not be empty!");
    }

    public void validate() {
        validateModelName(this.modelName) ;
        Utils.validateEmpty(desc , "desc can not be empty!");
        if(this.clazz) {
            try {
                Class.forName(modelName , false , ReflectUtils.getDefaultClassLoader()) ;
            } catch (ClassNotFoundException e) {
                throw new AdminRuntimeException(modelName + " this class not found !") ;
            }
        }
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isClazz() {
        return clazz;
    }

    public void setClazz(boolean clazz) {
        this.clazz = clazz;
    }
}
