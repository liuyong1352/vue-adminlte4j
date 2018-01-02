package api.data;

import api.UserInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjliuyong on 2017/12/27.
 */
public class ModelConfig {

    private static List<Class> modelClasses = new ArrayList<>() ;

    static {
        modelClasses.add(UserInfo.class) ;
    }

    public static List<Class> list() {
        return modelClasses  ;
    }
}
