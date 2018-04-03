package api;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.vue.adminlte4j.support.FileChangeListener;
import com.vue.adminlte4j.web.StaticFileFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Created by bjliuyong on 2017/12/13.
 */
@SpringBootApplication
public class ApplicationStarter {

    public static void main(String[] args) {
        configFileChangeListener();
        SpringApplication.run(ApplicationStarter.class, args);
        FileChangeListener.getInstance().interrupt();
    }



    private static void configFileChangeListener() {
        FileChangeListener fileChangeListener = FileChangeListener.getInstance();

        Path distLibPath = Paths.get("src" , "main" ,"static" , "dist" , "lib") ;
        Path targetLibPath = Paths.get("target" , "classes" , "META-INF" , "resources" , "lib") ;
        Path vueAdminlteJs = Paths.get("vue-adminlte","dist","js","vue-adminlte.min.js") ;

        fileChangeListener.listen(distLibPath , "base.js").to(targetLibPath , "base.js") ;
        fileChangeListener.listen(distLibPath , "lib.js").to(targetLibPath , "lib.js") ;
        fileChangeListener.listen(distLibPath , vueAdminlteJs).to(targetLibPath , vueAdminlteJs) ;
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
