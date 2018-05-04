<template>
<div id="app" class="wrapper">
    <v-header :data="app_info"></v-header>
    <v-sidebar :data="menu_items"></v-sidebar>
    <v-content>
        <slot></slot>
    </v-content>
    <v-control-sidebar></v-control-sidebar>
    <v-footer ></v-footer>
</div>
</template>

<script>
import VHeader      from './header.vue'
import VFooter      from './v-footer.vue'
import VSideBar     from './sidebar-menu.vue'
import VContent     from './content.vue'
import VControlSideBar from './control-sidebar.vue'

export default {
    name: 'v-dashboard',
    props: {
        data: {type:Object , default:{}}
    } ,
    data(){
        return {
            app_info:{} ,
            menu_items:[]
        }
    } ,
    created() {
        var vm=this
        axios.get('/admin/app_info/get_all').then(function (response) {
            vm.app_info=response.data.app_info
            vm.menu_items=response.data.menu_items
        })
        console.log("dashboard created!!")
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