package api;

import api.data.AppInfoInJvm;
import api.data.MenuApiInJvm;
import com.jd.vue.adminlte.model.TableData;
import com.jd.vue.adminlte.model.UIModel;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by bjliuyong on 2017/12/14.
 */
@Controller
public class IndexController {


    @GetMapping("/get_app_info")
    @ResponseBody
    Map getAppInfo() {

        UIModel uiModel = new UIModel()
            .menu(MenuApiInJvm.getMenu())
            .appInfo(AppInfoInJvm.getAppInfo())
            .isLogin(true) ;

        TableData tableData = new TableData() ;
        tableData.configDisplayColumn(TableData.createColumn("name" , "姓名") );
        tableData.configDisplayColumn(TableData.createColumn("age" , "年龄") );

        Map<String,Object> map = new HashMap<>() ;
        map.put("name" , "xiaohong ") ;
        map.put("age" , "1") ;
        tableData.addData(map);


        uiModel.put("tableData" , tableData ) ;

        //return uiModel ;
        return uiModel ;
    }

    @GetMapping("/get_table_data")
    @ResponseBody
    Map<String,Object> get_table_data(@RequestParam(required = false) String name) {

        UIModel uiModel = new UIModel() ;

        TableData tableData = new TableData() ;
        tableData.configDisplayColumn(UserInfo.class);

       /* tableData.configDisplayColumn(TableData.createColumn("name" , "姓名") );
        tableData.configDisplayColumn(TableData.createColumn("age" , "年龄") );
        tableData.configDisplayColumn(TableData.createColumn("birthDay" , "生日") );*/

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
            tableData.addData(userInfo);
        }

        if("".equals(name)) {
            tableData.setTotalSize(0);
            tableData.setPage(false);
        }

        tableData.setTotalSize(50);

        uiModel.tableData(tableData);
        //uiModel.put(UIModel.TABLE_DATA , tableData ) ;

        return uiModel ;
    }
}
