package com.vue.adminlte4j.support.store.serialize;

import com.vue.adminlte4j.model.AppInfo;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;

/**
 * Created by bjliuyong on 2018/10/12.
 */
public class FixLenSerializerTest {


    @Test
    public void testEncodeAndDecode() throws Exception {
        FixLenSerializer fixLenSerializer = new FixLenSerializer() ;
        AppInfo appInfo = new AppInfo() ;
        appInfo.setAppName("后台管理\r\n应用");
        appInfo.setCopyright("就是这么简单,");
        byte[] bytes = fixLenSerializer.encode(appInfo) ;
        System.out.println(new String(bytes , "UTF-8"));

        List<AppInfo> clone = fixLenSerializer.decode(bytes , AppInfo.class) ;

        Assert.assertEquals(appInfo.getAppName() ,      clone.get(0).getAppName());
        Assert.assertEquals(appInfo.getCopyright() ,    clone.get(0).getCopyright());
    }
}
