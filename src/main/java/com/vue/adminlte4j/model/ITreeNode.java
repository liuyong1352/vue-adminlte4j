package com.vue.adminlte4j.model;

import java.util.List;

public interface ITreeNode {

    String getId() ;

    String getPid() ;

    String getIcon() ;

    String getText() ;

    default List<String> getTags() {
        return  null ;
    }

    /**
     * 判断是否是根节点
     * @return
     */
    default boolean isRoot() {
        String pid = getPid() ;
        return pid == null || pid.isEmpty() || "0".equals(pid) ;
    }
}
