package api;

import api.data.MenuApiInJvm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vue.adminlte4j.model.UIModel;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.springframework.web.util.HtmlUtils;

/**
 * Created by bjliuyong on 2017/12/20.
 */
public class HtmlData {

    public static void main(String ... args ) throws Exception {

        UIModel uiModel = new UIModel()
            .menu(MenuApiInJvm.getMenu())
            .isLogin(true) ;

        ObjectMapper objectMapper = new ObjectMapper() ;
        String json = objectMapper.writeValueAsString(uiModel);
        //System.out.println(json);
        System.out.println("html=>  " + HtmlUtils.htmlEscape("<script>app.main({})</script>"));


        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
        String line = scanner.readLine() ;
        System.out.println("src is :" + line);
        System.out.println("escape is : " + HtmlUtils.htmlEscape(line));
    }
}
