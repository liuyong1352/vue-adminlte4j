package com.vue.adminlte4j.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjliuyong on 2018/3/5.
 */
public class TreeNode {

    private String text;

    private List<String> tags;

    private String id;

    private String parentId;

    private List<TreeNode> nodes;

    private String icon;

    public String getParentId() {

        return parentId;
    }

    public String getIcon() {

        return icon;
    }

    public void setIcon(String icon) {

        this.icon = icon;
    }



    public void setParentId(String parentId) {

        this.parentId = parentId;
    }

    public String getText() {

        return text;
    }

    public void setText(String text) {

        this.text = text;
    }

    public List<String> getTags() {

        return tags;
    }

    public void setTags(List<String> tags) {

        this.tags = tags;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public List<TreeNode> getNodes() {

        return nodes;
    }

    public void setNodes(List<TreeNode> nodes) {

        this.nodes = nodes;
    }

    public void addChildNode(TreeNode node) {
        if(nodes == null )
            nodes = new ArrayList<>() ;
        nodes.add(node) ;
    }


}
