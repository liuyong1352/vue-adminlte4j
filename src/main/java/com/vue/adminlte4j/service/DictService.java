package com.vue.adminlte4j.service;

import com.vue.adminlte4j.model.Dict;
import java.util.List;

/**
 * Created by bjliuyong on 2018/3/27.
 */
public interface DictService {

    List<Dict> findAll() ;

    List<Dict> list(String key) ;

    Dict get(String key) ;
}
