package api;

import api.data.MenuApiInJvm;
import com.vue.adminlte4j.model.AppInfo;
import com.vue.adminlte4j.model.Menu;
import com.vue.adminlte4j.model.UIModel;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.web.util.HtmlUtils;
import org.yaml.snakeyaml.Yaml;

/**
 * Created by bjliuyong on 2017/12/20.
 */
public class HtmlData {

    public static void main(String ... args ) throws Exception {

        UIModel uiModel = new UIModel()
            .menu(MenuApiInJvm.getMenu())
            .isLogin(true) ;

       /* ObjectMapper objectMapper = new ObjectMapper() ;
        String json = objectMapper.writeValueAsString(uiModel);
        //System.out.println(json);*/

        Yaml yaml = new Yaml();

        //Me me = yaml.loadAs(new FileInputStream(new File("config/me.yaml")), Me.class);
        //System.out.println(yaml.dump(uiModel));
        System.out.println(yaml.dump(new AppInfo()));

        System.out.println("html=>  " + HtmlUtils.htmlEscape("<script>app.main({})</script>"));


        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
        String line = scanner.readLine() ;
        System.out.println("src is :" + line);
        System.out.println("escape is : " + HtmlUtils.htmlEscape(line));
    }




}
