package api;

import api.data.domain.XModel;
import com.vue.adminlte4j.model.UIModel;
import java.util.Date;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by bjliuyong on 2018/3/30.
 */
@Controller
public class FormController  {

    private XModel xModel = new XModel() ; //just for test

    {
        xModel.setAge(20);
        xModel.setName("this is test!");
        xModel.setLove("");
        xModel.setBirthDay(new Date());
    }

    @GetMapping("/test/form/get")
    @ResponseBody
    public UIModel get(){
        //just for test form
        return  UIModel.success().formData(xModel) ;
    }

    @GetMapping("/test/form/update")
    @ResponseBody
    public UIModel update(XModel xModel){
        //just for test form
        this.xModel = xModel ;
        return  UIModel.success() ;
    }
}
