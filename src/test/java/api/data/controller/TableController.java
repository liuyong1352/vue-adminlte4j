package api.data.controller;

import api.data.domain.XModel;
import api.data.domain.XModelQuery;
import api.data.domain.XModelStore;
import com.vue.adminlte4j.model.TableData;
import com.vue.adminlte4j.model.UIModel;
import com.vue.adminlte4j.model.builder.TableBuilder;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        TableBuilder builder = TableBuilder.newBuilder(XModel.class)
            .data(page(xModelQuery))
            .isPage(true)
            .totalSize(count()).hiddenColumn("myIcon") ;
        return  UIModel.success().tableData(builder.build());
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



}
