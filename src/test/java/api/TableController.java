package api;

import com.vue.adminlte4j.model.TableData;
import com.vue.adminlte4j.model.UIModel;
import java.util.ArrayList;
import java.util.List;
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

    @GetMapping("/get_table_data")
    @ResponseBody
    UIModel get_table_data(@RequestParam(required = false) String name) {



        TableData tableData = new TableData() ;
        tableData.configDisplayColumn(UserInfo.class);

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
