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
    import {buildTree} from '../../utils/util'
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
                selected_node_id:null ,
                nodes : null
            }
        } ,
        methods : {
            refresh(data) {
                this.selected_node_id = null
                if(this.ajax_url) {
                    this.ajaxGet(this.ajax_url , null , function(rep){
                        this.nodes = this.build_tree(rep.data)
                    })
                }
            },
            build_tree(elements) {
                return buildTree(elements , this)
            } ,
            get_selected() {
                return this.selected_node_id
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