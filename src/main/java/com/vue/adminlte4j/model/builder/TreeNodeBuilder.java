package com.vue.adminlte4j.model.builder;

import com.vue.adminlte4j.model.TreeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bjliuyong on 2018/3/6.
 */
public interface TreeNodeBuilder<E> {

    TreeNode build(E e) ;

    default List<TreeNode> transform(List<E> elements) {
        List<TreeNode> rootNodes = new ArrayList<>() ;
        Map<String, TreeNode> idNodeMap = new HashMap<>() ;
        List<TreeNode> treeNodes = new ArrayList<>(elements.size()) ;  //保证elements次序不变
        elements.forEach(e ->{
            TreeNode node = build(e) ;

            if(node.isRoot())
                rootNodes.add(node) ;
            else
                treeNodes.add(node);
            idNodeMap.put(node.getId() , node);
        });

        treeNodes.forEach((v)->{
            if(!v.isRoot())
                idNodeMap.get(v.getParentId()).addChildNode(v);
        });
        return  rootNodes  ;
    }


}
