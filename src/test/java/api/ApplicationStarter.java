package api;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.vue.adminlte4j.support.FileChangeListener;
import com.vue.adminlte4j.web.StaticFileFilter;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Created by bjliuyong on 2017/12/13.
 */
@SpringBootApplication
public class ApplicationStarter {

    private final static Logger LOG =  LoggerFactory.getLogger(ApplicationStarter.class);


    public static void main(String[] args) {
        configFileChangeListener();

        ApplicationContext context = SpringApplication.run(ApplicationStarter.class, args);
        String serverPort = context.getEnvironment().getProperty("server.port");
        LOG.info("Clay started at http://localhost:" + serverPort);

    }


    /**
     *  动态刷新资源
     */
    private static void configFileChangeListener() {
        FileChangeListener fileChangeListener = FileChangeListener.getInstance();

        Path distLibPath = Paths.get("src" , "main" ,"static" , "dist" , "lib") ;
        Path targetLibPath = Paths.get("target" , "classes" , "META-INF" , "resources" , "lib") ;
        Path vueAdminlteJs = Paths.get("vue-adminlte","dist","js","vue-adminlte.min.js") ;
        Path baseCss = Paths.get("vue-adminlte","dist","css","base.css") ;
        fileChangeListener.listen(distLibPath , "base.js").to(targetLibPath , "base.js") ;
        fileChangeListener.listen(distLibPath , "lib.js").to(targetLibPath , "lib.js") ;
        fileChangeListener.listen(distLibPath , vueAdminlteJs).to(targetLibPath , vueAdminlteJs) ;
        fileChangeListener.listen(distLibPath , baseCss).to(targetLibPath , baseCss) ;
        fileChangeListener.autoConfig(FileChangeListener.SPRING_BOOT);

        fileChangeListener.start();
    }

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        //fastJsonConfig.setDateFormat("yyyy-MM-dd hh:mm:ss");
        converter.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters(converter) ;
    }

    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new StaticFileFilter());//添加过滤器
        registration.addUrlPatterns("/*");//设置过滤路径，/*所有路径
       /* registration.addInitParameter("name", "alue");//添加默认参数
        registration.setName("MyFilter");//设置优先级
        registration.setOrder(1);//设置优先级*/
        return registration;
    }

}
