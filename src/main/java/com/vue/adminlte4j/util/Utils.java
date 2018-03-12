package com.vue.adminlte4j.util;

import com.vue.adminlte4j.model.UIModel;
import com.vue.adminlte4j.support.AdminRuntimeException;

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
        } catch (AdminRuntimeException e) {
            return UIModel.success().setMsg(e.getMessage()) ;
        } catch (Exception e) {
            return UIModel.success().setMsg("系统暂时大小差了， 请联系管理员！") ;
        }
    }

}
