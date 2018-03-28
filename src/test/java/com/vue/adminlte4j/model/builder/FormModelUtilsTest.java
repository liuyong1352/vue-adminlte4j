package com.vue.adminlte4j.model.builder;

import com.vue.adminlte4j.model.AppInfo;
import com.vue.adminlte4j.model.form.FormModel;
import org.junit.Assert;
import org.junit.Test;


/**
 * Created by bjliuyong on 2018/3/28.
 */
public class FormModelUtilsTest {

    @Test
    public void test() {
        FormModel formModel = FormModelUtils.getFormModel(AppInfo.class);
        Assert.assertNotNull(formModel);
    }

}
