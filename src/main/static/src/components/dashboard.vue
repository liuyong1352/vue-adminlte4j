<template>
<div class="wrapper">
    <v-header :data="app_info" :user_name="user_name" :user_img="user_img"></v-header>
    <v-sidebar :data="menu_items"></v-sidebar>
    <v-content>
        <slot></slot>
    </v-content>
    <v-control-sidebar></v-control-sidebar>
    <v-footer :copyright="app_info.copyright" :version="app_info.version"></v-footer>
</div>
</template>

<script>
import VHeader      from './header.vue'
import VFooter      from './v-footer.vue'
import VSideBar     from './sidebar-menu.vue'
import VContent     from './content.vue'
import VControlSideBar from './control-sidebar.vue'

export default {
    name: 'v-app',
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
        var vm=this
        $.get('/admin/app_info/get_all').then(function (data) {
            vm.app_info=data.app_info
            vm.menu_items=data.menu_items
            vm.user_name=data.user_name
            vm.user_img=data.user_img
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