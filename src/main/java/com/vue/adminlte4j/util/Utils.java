package com.vue.adminlte4j.util;

import com.vue.adminlte4j.model.UIModel;
import com.vue.adminlte4j.support.AdminRuntimeException;

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
        if(e instanceof AdminRuntimeException) {
            return UIModel.fail().msg(e.getMessage()) ;
        } else {
            e.printStackTrace();
            return UIModel.fail().msg("系统暂时出小差了， 请联系管理员！") ;
        }
    }

    public static <V> V internalCall(Callable<V> callable) {
        try {
            return callable.call();
        }  catch (Exception e) {
            throw new RuntimeException(e) ;
        }
    }

    public static void validate(boolean exp , String msg) {
        if(!exp)
            throw new AdminRuntimeException(msg) ;
    }

    public static void validateBlank(final CharSequence cs , String msg) {
        if(isBlank(cs))
            throw new AdminRuntimeException(msg) ;
    }



    public static void validateEmpty(final CharSequence cs , String msg) {
        if(isEmpty(cs))
            throw new AdminRuntimeException(msg) ;
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }


}

