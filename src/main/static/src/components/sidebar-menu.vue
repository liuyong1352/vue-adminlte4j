<template>
<aside class="main-sidebar">
    <section class="sidebar">
        <ul class="sidebar-menu" data-widget="tree" >
            <li class="header"></li>
            <template v-for="item in data">
                <li v-if="item.children" :class="append_active_class(item , 'treeview')"  >
                    <a href="#"><i :class="item.icon"></i> <span v-text="item.desc"></span>
                        <span class="pull-right-container"><i class="fa fa-angle-left pull-right"></i></span>
                    </a>
                    <ul class="treeview-menu">
                        <li v-for="citem in item.children">
                            <a href="#" @click="link_click(citem  ,item,$event)">
                                <i :class="citem.icon"></i><span v-text="citem.desc"></span>
                            </a>
                        </li>
                    </ul>
                </li>
                <li :class="append_active_class(item ,'')" v-else>
                    <a href="#"  @click="link_click(item  , null ,$event)">
                        <i :class="item.icon"></i><span v-text="item.desc"></span>
                    </a>
                </li>
            </template>
        </ul>
    </section>
</aside>
</template>

<script>
import util from '../lib/util'
export default {
    name: 'v-sidebar',
    props: {
        data: Array
    } ,
    methods : {
        link_click: function(item ,p_item,event){
            $('.sidebar-menu').find('.active').removeClass('active')
            $("#lib_menu1").html(item.desc)
            if(p_item) {
                $(event.target).parents('.treeview').addClass('active')
                $("#page_header").html(p_item.desc)
            } else {
                $(event.target).parents('li').addClass('active')
                $("#page_header").html(item.desc)

            }
            if(item.url != window.location.hash.substring(1))
                window.location.hash = item.url
            else
                $.load_page(item.url)
            event.preventDefault()
        },
        is_cur_menu: function(item) {
            var pathname = window.location.hash.substring(1)
            if(item['children'] !== null && item['children'] !== undefined ) {
                for(var c in item['children']) {
                    if(item['children'][c].url === pathname) {
                        $("#page_header").html(item.desc)
                        $("#lib_menu1").html(item.children[c].desc)
                        return true
                    }
                }
                return false
            }
            var r = item.url == pathname
            if(r)
                $("#lib_menu1").html(item.desc)
            return r

        },
        append_active_class: function(item , cur_class) {
            if(this.is_cur_menu(item)) {

                return cur_class + ' active'
            }
            return cur_class

        }
    },
    mounted : function() {
        $('.sidebar-menu').tree()
    }

}
</script>