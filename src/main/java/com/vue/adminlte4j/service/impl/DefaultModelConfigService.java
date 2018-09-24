//package com.vue.adminlte4j.service.impl;
//
//import com.jd.adminlte4j.model.config.ConfigFormItem;
//import com.jd.adminlte4j.model.config.ConfigModel;
//import com.jd.adminlte4j.service.ModelConfigService;
//import com.jd.adminlte4j.support.AdminRuntimeException;
//import com.jd.adminlte4j.support.store.BaseStore;
//import com.jd.adminlte4j.util.Utils;
//import com.vue.adminlte4j.support.store.BaseStore;
//
//import java.io.IOException;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * Created by bjliuyong on 2018/5/28.
// */
//public class DefaultModelConfigService implements ModelConfigService  , BaseStore {
//
//    private static final String STORE_DIR = "model" ;
//    private static final String MODEL_LIST = "mode.list" ;
//
//    private Map<String,List<ConfigFormItemormItem>> cache = new ConcurrentHashMap<>() ;
//
//    @Override
//    public List<ConfigModel> getModelList() {
//        return Utils.internalCall(()-> {
//            List<ConfigModel> r = readListObject(getModelListFile()  , ConfigModel.class) ;
//            if (r == null )
//                r = new ArrayList<>() ;
//            return  r ;
//        }) ;
//    }
//
//    @Override
//    public void saveModel(ConfigModel configModel) {
//
//        List<ConfigModel> allModel = getModelList() ;
//        for(ConfigModel model : allModel) {
//            if(model.getModelName().equals(configModel.getModelName()))
//                throw new AdminRuntimeException("模型名称已存在,无需添加!") ;
//        }
//        allModel.add(configModel);
//        writeObject(allModel , getModelListFile());
//    }
//
//    @Override
//    public void deleteModel(ConfigModel configModel) throws IOException {
//        List<ConfigModel> allModel = getModelList() ;
//        Iterator<ConfigModel> iterator = allModel.iterator();
//        while (iterator.hasNext()) {
//            if(iterator.next().getModelName().equals(configModel.getModelName())) {
//                iterator.remove();
//                break;
//            }
//        }
//        writeObject(allModel , getModelListFile());
//        deleteStoreFile(getStoreFileName(configModel.getModelName()));
//
//    }
//
//    @Override
//    public List<ConfigFormItem> get(String clsName) {
//        List<ConfigFormItem> configFormItems = cache.get(clsName) ;
//        if(configFormItems != null ) {
//            return configFormItems ;
//        }
//        String file = getStoreFileName(clsName) ;
//        try {
//            return readListObject(file , ConfigFormItem.class) ;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public void save(String clsName, List<ConfigFormItem> configFormItems) {
//
//        String file = getStoreFileName(clsName) ;
//        writeObject(configFormItems , file);
//        cache.remove(clsName) ;
//    }
//
//    @Override
//    public void update(String clsName, List<ConfigFormItem> configFormItems) {
//        save(clsName ,configFormItems);
//    }
//
//    private String getModelListFile() {
//        return Paths.get(STORE_DIR , MODEL_LIST).toString() ;
//    }
//
//    private String getStoreFileName(String clsName) {
//        return Paths.get(STORE_DIR , clsName + ".s").toString() ;
//    }
//}
