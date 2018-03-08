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
        elements.forEach(e ->{
            TreeNode node = build(e) ;
            idNodeMap.put(node.getId() , node);

        });

        idNodeMap.forEach((k,v)->{

            String pid = v.getParentId() ;
            if(pid == null || pid.isEmpty() || pid.equals("0"))
                rootNodes.add(v) ;
            else {
                idNodeMap.get(pid).addChildNode(v);
            }

        });
        return  rootNodes  ;
    }


}
