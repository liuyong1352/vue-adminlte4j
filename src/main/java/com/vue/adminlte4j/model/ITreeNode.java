package com.vue.adminlte4j.model;

public interface ITreeNode {

    String getId() ;

    String getPid() ;

    /**
     * 判断是否是根节点
     * @return
     */
    default boolean isRoot() {
        String pid = getPid() ;
        return pid == null || pid.isEmpty() || "0".equals(pid) ;
    }
}
