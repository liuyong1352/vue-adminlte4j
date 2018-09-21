<template>
<div class="wrapper">
    <v-header :data="app_info" :user_name="user_name" :user_img="user_img"></v-header>
    <v-sidebar :data="menu_items" ></v-sidebar>
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
import VSideBar     from '../sidebar-menu.vue'
import VContent     from '../content.vue'
import VControlSideBar from '../control-sidebar.vue'

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
    mounted() {
        this.ajaxGet('/admin/app_info/get_all' , null , function(data){
            this.app_info=data.app_info
            this.menu_items=data.menu_items
            this.user_name=data.user_name
            this.user_img=data.user_img
            var indexUrl = data.app_info.indexUrl
            var hash = window.location.hash
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
        'v-sidebar': VSideBar ,
        'v-content': VContent ,
        'v-control-sidebar': VControlSideBar ,
        'v-footer': VFooter
    }
}
</script>