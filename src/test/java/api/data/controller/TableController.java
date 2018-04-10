package api.data.controller;

import api.UserInfo;
import api.data.domain.XModel;
import api.data.domain.XModelQuery;
import api.data.domain.XModelStore;
import com.vue.adminlte4j.model.TableData;
import com.vue.adminlte4j.model.UIModel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by bjliuyong on 2018/2/5.
 */
@Controller
public class TableController {

    @GetMapping("/xmodel/get_query")
    @ResponseBody
    UIModel get_query() {
        return UIModel.success().formData(null , XModelQuery.class);
    }

    @GetMapping("/xmodel/list")
    @ResponseBody
    UIModel query_table_data(XModelQuery xModelQuery) throws Exception {

        TableData tableData = TableData.newInstance(XModel.class)
            .addAll(page(xModelQuery))
            .setTotalSize(count()) ;

        return  UIModel.success().tableData(tableData);
    }

    private int count() throws Exception{
        List<XModel> xModels = XModelStore.getAll() ;
        return xModels.size() ;
    }

    private List<XModel> page(XModelQuery xModelQuery) throws Exception {
        List<XModel> xModels = XModelStore.getAll()
            .stream()
            .filter(xModel -> (xModelQuery.getName() != null ? xModelQuery.getName().equals(xModel.getName()):true))
            .collect(Collectors.toList());
        return xModels.subList(0 , xModels.size() >= 20? 20 : xModels.size()) ;
    }

    @GetMapping("/get_table_data")
    @ResponseBody
    UIModel get_table_data(@RequestParam(required = false) String name) {

        TableData tableData = TableData.newInstance(UserInfo.class);

        tableData.configDisplayColumn(TableData.createColumn("name" , "姓名") );
        tableData.configDisplayColumn(TableData.createColumn("age" , "年龄") );
        //tableData.configDisplayColumn(TableData.createColumn("birthDay" , "生日") );

        tableData.addAll(queryList(name));
        tableData.setTotalSize(50);

        return UIModel.success().tableData(tableData);
    }

    private List<UserInfo> queryList(String name) {

        List<UserInfo> list = new ArrayList<>() ;

        for(int i = 0 ; i < 10 ; i++ ) {
            UserInfo userInfo = new UserInfo() ;
            userInfo.setName("u" + i);

            if(name!= null && !name.isEmpty()) {
                if(!userInfo.getName().startsWith(name))
                    continue;
            }

            userInfo.setAge(20 + i);
            userInfo.setBirthDay(new Date());
            userInfo.setSex(i%2);
            list.add(userInfo);
        }

        return  list ;
    }

}
