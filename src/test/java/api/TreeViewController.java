package api;

import com.vue.adminlte4j.model.TreeNode;
import com.vue.adminlte4j.model.UIModel;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by bjliuyong on 2018/3/5.
 */
@Controller
public class TreeViewController {

    @GetMapping("/get_tree_data")
    @ResponseBody
    UIModel get_table_data() {
        List<TreeNode> treeNodes = new ArrayList<>() ;

        TreeNode treeNode = new TreeNode() ;
        treeNode.setId("1");
        treeNode.setParentId("0");
        treeNode.setIcon("fa fa-male");
        treeNode.setText("Test");

        List<TreeNode> c = new ArrayList<>() ;
        TreeNode ci = new TreeNode() ;
        ci.setText("Test2");
        ci.setParentId("1");
        ci.setIcon("fa fa-male");
        ci.setId("2");

        c.add(ci) ;

        treeNode.setNodes(c);

        treeNodes.add(treeNode) ;
        return UIModel.success().treeData(treeNodes) ;
    }

}
