package com.vue.adminlte4j.model.config;

import com.vue.adminlte4j.annotation.*;
import com.vue.adminlte4j.model.Dict;
import com.vue.adminlte4j.model.form.FormItem;
import com.vue.adminlte4j.model.form.FormItemType;
import com.vue.adminlte4j.model.form.ValidateType;
import com.vue.adminlte4j.util.Constants;
import com.vue.adminlte4j.util.ReflectUtils;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import static com.vue.adminlte4j.model.form.FormItemType.*;

/**
 * Created by bjliuyong on 2018/5/25.
 */
@Form(hidden = false , ignore = false ,span = 6)
public class ConfigFormItem {


    @Validate
    @UIFormItem(span = 6)
    private String key   ;

    @Validate
    private String label ;

    @UIFormItem(type = SELECT)
    @DictProvider(type = ConfigFormItem.class , method = "dict")
    private int         type = INPUT;

    private int         span = 12;


    @UIFormItem(type = SWITCH)
    @DictData({@DictEntry(code = "1" , value = "是") ,
        @DictEntry(code = "0" , value = "否")})
    private int  hidden ;

    private String placeholder ;

    private String dictName;

    private String dictProvider ;

    private int subType ;
    private String format ;

    @UIFormItem(type=SWITCH , label = "is range")
    @DictData({@DictEntry(code = "1" , value = "Y") ,
        @DictEntry(code = "0" , value = "N")})
    private int range ;

    @UIFormItem(type = SELECT)
    @DictProvider(type = ConfigFormItem.class , method = "dict")
    private int validateType ;

    private String validateRegex ;

    @UIFormItem(type = CHECKBOX)
    @DictData({
        @DictEntry(code = "1" ,value = "add"),
        @DictEntry(code = "2" ,value = "edit"),
        @DictEntry(code = "4" ,value = "query"),
        @DictEntry(code = "8" ,value = "table")})
    private String operation  ;

    @UIFormItem(hidden = true)
    private int operationMode ;


    public ConfigFormItem() {

    }

    public ConfigFormItem(FormItem formItem) {
        this.key = formItem.getKey() ;
        this.label = formItem.getLabel() ;
        this.span   = formItem.getSpan() ;
        this.type   = formItem.getType() ;
        this.hidden = formItem.isHidden() ? 1:0;

    }

    public FormItem convert()  {
        FormItem formItem = new FormItem(this.key , this.label) ;
        formItem.setSpan(this.span) ;
        formItem.setPlaceholder(this.placeholder) ;
        formItem.setHidden(this.hidden == 1 ) ;
        formItem.setType(this.type) ;

        if(this.validateType != Constants.DEFAULT) {
            com.vue.adminlte4j.model.form.Validate validate = com.vue.adminlte4j.model.form.Validate.newValidate(this.validateType) ;
            formItem.setValidate(validate);
        }

        return  formItem ;
    }

    public static List<Dict> dict(String key) {

        if(key.equals("type")) {
            return constructDict(FormItemType.class);
        } else if(key.equals("validateType")) {
            return constructDict(ValidateType.class);
        }

        return  null ;

    }

    public static List<Dict> constructDict(Class cls) {
        List<Field> fields = ReflectUtils.findAllField(cls) ;
        return  fields.stream()
            .map(field -> Dict.build(ReflectUtils.getStaticFieldValue(field).toString() ,field.getName().toLowerCase()))
            .collect(Collectors.toList()) ;
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


    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSpan() {
        return span;
    }

    public void setSpan(int span) {
        this.span = span;
    }

    public int getHidden() {
        return hidden;
    }

    public void setHidden(int hidden) {
        this.hidden = hidden;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictProvider() {
        return dictProvider;
    }

    public void setDictProvider(String dictProvider) {
        this.dictProvider = dictProvider;
    }

    public int getSubType() {
        return subType;
    }

    public void setSubType(int subType) {
        this.subType = subType;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getValidateType() {
        return validateType;
    }

    public void setValidateType(int validateType) {
        this.validateType = validateType;
    }

    public String getValidateRegex() {
        return validateRegex;
    }

    public void setValidateRegex(String validateRegex) {
        this.validateRegex = validateRegex;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(int operationMode) {
        this.operationMode = operationMode;
    }
}
