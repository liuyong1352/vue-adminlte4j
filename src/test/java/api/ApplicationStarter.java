package api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by bjliuyong on 2017/12/13.
 */
@SpringBootApplication(scanBasePackages = "com.vue.adminlte4j.web.springmvc")
public class ApplicationStarter {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
    }
}
