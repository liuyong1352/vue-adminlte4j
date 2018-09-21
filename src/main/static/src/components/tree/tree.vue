<template>
<div :class="['tree' , {'tree-border' : isBorder}]">
    <template v-if="nodes && nodes.length">
        <div class="tree-group">
            <v-tree-node v-for="node in nodes" :model="node" :key="node.id" ></v-tree-node>
        </div>
    </template>
</div>
</template>
<script>
import TreeNode from './tree-node.vue'
export default {
    name: 'v-tree',
    props: {
        ajax_url:String ,
        data : Array ,
        isBorder: {type:Boolean , default : true } ,
        text : {type : String , default : "text"} ,
        expand_icon : {type: String  ,default : 'glyphicon glyphicon-triangle-bottom'} ,
        collapse_icon : {type: String , default: 'glyphicon glyphicon-triangle-right' }
    } ,
    data(){
        return {
            tree: {
                selected_node_id:null ,
                expand_icon : this.expand_icon ,
                collapse_icon : this.collapse_icon
            } ,
            nodes : null ,
            text_key : this.text
        }
    } ,
    methods : {
        refresh(data) {
            this.tree.selected_node_id = null
            if(this.ajax_url) {
                this.ajaxGet(this.ajax_url , null , function(rep){
                    this.nodes = this.build_tree(rep.data)
                })
            }
        },
        build_tree(elements) {
            var tree = []
            var map = {}
            var arr = []
            for(var i in elements ) {
                var d = elements[i]
                var e = {id:d.id ,
                    pid  : d.pid ,
                    data : d ,
                    text : d[this.text_key] ,
                    expand: 0 ,
                    show : 0 ,
                    tree : this.tree,
                    root : this
                    }
                arr[i] = e
                map[e.id] = e
            }

            for(var i in arr) {
                var e = arr[i]
                var pid = e.pid
                if(pid  &&  map[pid]) {
                    var parent = map[pid]
                    if(!parent.children)
                       parent.children = []
                    parent.children.push(e)
                    e.parent = parent
                } else {
                    e.show = 1
                    tree.push(e)
                }
            }
            return tree
        } ,
        get_selected() {
            return this.tree.selected_node_id
        }

    } ,
    created() {
        if(this.data) {
            this.nodes = this.build_tree(this.data)
        }
        this.refresh()
    } ,
    components : {
        'v-tree-node': TreeNode,
    }
}
</script>