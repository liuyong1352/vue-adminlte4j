package com.vue.adminlte4j.model.form;

import com.vue.adminlte4j.annotation.DictData;
import com.vue.adminlte4j.annotation.DictEntry;
import com.vue.adminlte4j.annotation.UIDate;
import com.vue.adminlte4j.model.Dict;
import com.vue.adminlte4j.util.AnnotationUtils;
import com.vue.adminlte4j.util.DictUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bjliuyong on 2018/3/26.
 */
public class ExtInfo extends HashMap<String,Object> {

    public static final String FORMAT = "format" ;
    public static final String TYPE  = "type" ;
    public static final String RANGE = "range" ;

    public static final String DICT = "dict" ;

    public static ExtInfo config(int formItemType, Field field ) throws Exception {
        ExtInfo extInfo = null ;
        if(formItemType == FormItemType.DATE) {
            extInfo = ExtInfo.configDate(field);
        } else if(FormItemType.hasDict(formItemType)) {
            extInfo = ExtInfo.configDict(field);
        }
        return  extInfo ;
    }

    public static ExtInfo configDate(Field field) {
        UIDate uiDate = AnnotationUtils.findAnnotation(field , UIDate.class) ;
        if(uiDate == null)
            return null;
        ExtInfo extInfo = new ExtInfo() ;
        extInfo.put(TYPE , uiDate.type().ordinal());
        extInfo.put(FORMAT , uiDate.format());
        extInfo.put(RANGE , uiDate.range());
        return  extInfo ;
    }

    public static ExtInfo newDictExt(List<Dict> dictList) {
        ExtInfo extInfo = new ExtInfo() ;
        extInfo.put(DICT , dictList) ;
        return  extInfo ;
    }

    public static ExtInfo configDict(Field field) throws Exception {
        List<Dict> dictList = parseDictData(field) ;
        if(dictList == null )
            dictList = DictUtils.parseDictProvider(field) ;
        return  newDictExt(dictList) ;
    }


    private static List<Dict> parseDictData(Field field) {
        DictData dictData = AnnotationUtils.findAnnotation(field , DictData.class) ;

        if(dictData == null)
            return null;

        DictEntry[] dictEntries = dictData.value();
        int autoCode = 1 ; //from 1
        List<Dict> dicts = new ArrayList<>();
        for(DictEntry dictEntry : dictEntries) {
            String code = dictEntry.code() ;
            if(code == null || code.isEmpty())
                code = String.valueOf(autoCode) ;
            dicts.add(Dict.build(code , dictEntry.value())) ;
            autoCode++ ;
        }
        return  dicts ;
    }
}
