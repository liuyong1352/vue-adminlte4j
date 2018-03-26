package com.vue.adminlte4j.util;

import com.vue.adminlte4j.model.UIModel;
import com.vue.adminlte4j.support.AdminRuntimeException;
import java.lang.reflect.Field;
import java.util.concurrent.Callable;

/**
 * Created by bjliuyong on 2018/3/12.
 */
public class Utils {

    public static UIModel run(Runnable runnable) {
        return run(runnable , "操作成功!") ;
    }

    public static UIModel run(Runnable runnable , String msg) {
        try {
            runnable.run();
            return UIModel.success().setMsg(msg) ;
        } catch (Exception e) {
            return exceptionHandler(e) ;
        }
    }

    public static UIModel call(Callable<UIModel> callable) {
        try {
            return callable.call();
        }  catch (Exception e) {
            return exceptionHandler(e) ;
        }
    }

    private static UIModel exceptionHandler(Exception e) {
        if(e instanceof AdminRuntimeException ) {
            return UIModel.fail().setMsg(e.getMessage()) ;
        } else {
            e.printStackTrace();
            return UIModel.fail().setMsg("系统暂时出小差了， 请联系管理员！") ;
        }
    }


}
