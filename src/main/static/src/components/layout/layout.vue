<template>
    <div class="wrapper">
        <v-header :data="app_info" :user_name="user_name" :user_img="user_img"></v-header>
        <v-menu ref="menu" :data="menu_items" ></v-menu>
        <v-content>
            <slot></slot>
        </v-content>
        <v-control-sidebar></v-control-sidebar>
        <v-footer :copyright="app_info.copyright" :version="app_info.version"></v-footer>
    </div>
</template>

<script>
    import VHeader      from '../header.vue'
    import VFooter      from '../v-footer.vue'
    import VMenu        from './left-menu.vue'
    import VContent     from '../content.vue'
    import VControlSideBar from '../control-sidebar.vue'
    import {buildTree ,findAncestorId} from '../../utils/util'
    export default {
        name: 'v-layout',
        props: {
            data: {type:Object , default:{}}
        } ,
        data(){
            return {
                user_name : '' ,
                user_img:'' ,
                app_info:{} ,
                menu_items:[]
            }
        } ,
        created() {
            this.ajaxGet('/admin/app_info/get_all' , null , function(data){
                this.app_info=data.app_info
                var hash = window.location.hash
                var pathname = hash.substring(1)
                this.menu_items = buildTree(data.menu_items , this.$refs.menu , function(e){
                    if(e.data.url == pathname)
                        e.tree.selected_node_id = findAncestorId(e).id
                })
                this.user_name=data.user_name
                this.user_img=data.user_img
                var indexUrl = data.app_info.indexUrl

                if(hash)
                    $.load_page(hash.substring(1))
                else if(indexUrl) {
                    $.load_page(indexUrl )
                } else {
                    $.load_page( false, "<br>Hi " + (data.user_name||'') + " , Welcome to " +  this.app_info.appName|| 'Admin' + "!!")
                }
                if(this.app_info.appName)
                    document.title= this.app_info.appName



            })

        } ,
        mounted() {
            $(window).on("hashchange", function() {
                //var state = e.state;
                var hash = window.location.hash
                $.load_page(hash.substring(1))
            })
            window.addEventListener("popstate", function(e) {
                //var state = e.state;
                //var hash = window.location.hash
                //$("#screen_content").load(hash.substring(1))
            })
        } ,
        components: {
            'v-header': VHeader,
            'v-menu': VMenu ,
            'v-content': VContent ,
            'v-control-sidebar': VControlSideBar ,
            'v-footer': VFooter
        }
    }
</script>