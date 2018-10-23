<template>
    <div  class="tree-node">
        <div :class="['tree-node-content' , {'tree-node-selected' : model.id == model.tree.selected_node_id}]"
             :style="{ 'padding-left': (level  * 18) + 'px' }" @click="node_click">
        <span v-if="model.children && model.children.length"
              :class="model.expand ? expand_icon : collapse_icon" @click.stop="toggle"></span>
            <span v-else class="glyphicon glyphicon-triangle-right is-leaf"></span>
            <span v-text="text" ></span>
        </div>
        <template v-if="model.expand && model.children && model.children.length">
            <div class="tree-group">
                <v-tree-node v-for="child in model.children"
                             :model="child"
                             :key="child.id"
                             :level="level+1">
                </v-tree-node>
            </div>
        </template>
    </div>
</template>
<script>
    export default {
        name: 'v-tree-node',
        props: {
            model: Object ,
            level : {
                type:Number ,
                default :0
            }
        } ,
        data(){
            return {}
        } ,
        computed : {
            expand_icon() {
                return this.model.tree.expand_icon
            } ,
            collapse_icon() {
                return this.model.tree.collapse_icon
            } ,
            text() {
                return this.model.data[this.model.tree.text]
            }
        } ,
        methods : {
            toggle(){
                this.model.expand ^=1
            } ,
            selected() {
                var model = this.model
                model.tree.selected_node_id=model.id
                model.tree.selected_node = model
                model.tree.$emit('on-node-selected' , model)
            } ,
            node_click() {
                this.selected()
            }
        }
    }
</script>