package com.vue.adminlte4j.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by bjliuyong on 2017/12/26.
 */
public class ModelConfigFactory {

    private static IModelConfig modelConfig = new DefaultModelConfig() ;

    static {
        Properties properties = new Properties();
        InputStream inputStream = null ;

        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("vue-adminlte.properties") ;
            properties.load(inputStream);
            String className = properties.getProperty("model.config.class");
            if(className != null )
                modelConfig = (IModelConfig) Class.forName(className).newInstance() ;
        } catch (Exception e) {
            //log
        } finally {
            if(inputStream != null )
                try {
                    inputStream.close();
                } catch (IOException e) {

                }
        }
    }



    public static IModelConfig getInstance() {
        return modelConfig ;
    }

}
