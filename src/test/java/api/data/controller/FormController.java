package api.data.controller;

import api.data.domain.User;
import api.data.domain.XModel;
import api.data.domain.XModelStore;
import com.alibaba.fastjson.JSON;
import com.vue.adminlte4j.model.TableData;
import com.vue.adminlte4j.model.UIModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by bjliuyong on 2018/3/30.
 */
@Controller
public class FormController  {

    @PostMapping("/form/add")
    @ResponseBody
    public UIModel add(@RequestBody Map params){
        return  UIModel.success().msg(JSON.toJSONString(params)) ;
    }

    @GetMapping("/form/user/get")
    @ResponseBody
    public UIModel getUser(){
        User user = new User() ;
        user.setName("test!!!");
        user.setAge(1);

        return  UIModel.success().formData(user , User.class) ;
    }

    @GetMapping("/form/user/list")
    @ResponseBody
    public UIModel getUserList(){
        User user = new User() ;
        user.setName("test!!!");
        List<User> userList = new ArrayList<>() ;
        userList.add(user) ;
        TableData tableData = new TableData() ;
        tableData.setDataItems(userList);
        tableData.setPage(true);

        return  UIModel.success().tableData(tableData) ;
    }

    @GetMapping("/test/form/get")
    @ResponseBody
    public UIModel get(){
        //just for test form
        XModel xModel = XModelStore.findOne() ;
        xModel.setInputName("河北廊坊市广阳区城区新星里小区-爱民东道" );
        return  UIModel.success().formData(xModel ,XModel.class) ;
    }


    /**api-submit-url-add**/
    @PostMapping("/test/form/add")
    @ResponseBody
    public UIModel add(@RequestBody XModel xModel) throws Exception {
        XModelStore.add(xModel);
        return  UIModel.success().msg("保存成功") ;
    }
    /**api-submit-url-add**/

    @PostMapping("/test/form/update")
    @ResponseBody
    public UIModel update(@RequestBody XModel xModel) throws Exception {
        XModelStore.update(xModel);
        return  UIModel.success().msg("修改成功") ;
    }
}
