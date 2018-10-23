package com.vue.adminlte4j.service;

import com.vue.adminlte4j.model.Dict;
import com.vue.adminlte4j.model.config.ConfigFormItem;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by bjliuyong on 2018/5/28.
 */
public class ConfigFormItemTest {

    @Test
    public void dict() throws Exception {

        List<Dict> dicts = ConfigFormItem.dict("type");
        for(Dict dict : dicts) {
            int code = Integer.valueOf(dict.getCode()) ;
            assertTrue(code > 0 );
        }
    }

}