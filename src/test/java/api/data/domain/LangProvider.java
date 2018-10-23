package api.data.domain;

import com.vue.adminlte4j.model.Dict;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjliuyong on 2018/5/16.
 */
public class LangProvider {

    public static List<Dict> lang() {
        List<Dict> dictList = new ArrayList<>() ;
        dictList.add(Dict.build("1" , "java")) ;
        dictList.add(Dict.build("2" , "c")) ;
        dictList.add(Dict.build("3" , "c++")) ;
        return dictList ;
    }
}
