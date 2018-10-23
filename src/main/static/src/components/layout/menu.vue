<template>
    <li v-if="model.children && model.children.length" :class="['treeview' , {'active' : model.id === model.tree.selected_node_id}]"  >
        <a href="#"><i :class="icon"></i> <span v-text="text"></span>
            <span class="pull-right-container"><i class="fa fa-angle-left pull-right"></i></span>
        </a>
        <ul class="treeview-menu">
            <v-menu v-for="item in model.children" :model="item" :key="item.id"></v-menu>
        </ul>
    </li>
    <li :class="{'active' : model.id == model.tree.selected_node_id}" v-else>
        <a href="#"  @click.prevent="link_click">
            <i :class="icon"></i><span v-text="text"></span>
        </a>
    </li>
</template>

<script>
    import {findAncestorId} from '../../utils/util'
    export default {
        name: 'v-menu',
        props: {
            model: Object
        } ,
        methods : {
            link_click: function(){
                this.model.tree.selected_node_id = findAncestorId(this.model).id
                if(this.model.parent) {
                    $("#page_header").html(this.model.parent.data.desc)
                } else {
                    $("#page_header").html(this.text)
                }
                $("#lib_menu1").html(this.text)
                if(this.url != window.location.hash.substring(1))
                    window.location.hash = this.url
                else
                    $.load_page(this.url)
            }
        } ,
        computed : {
            icon() {
                return this.model.data.icon
            } ,
            text() {
                return this.model.data.desc
            } ,
            url() {
                return this.model.data.url
            }
        }

    }
</script>