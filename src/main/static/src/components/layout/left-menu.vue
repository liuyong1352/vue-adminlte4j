<template>
    <aside class="main-sidebar">
        <section class="sidebar">
            <ul class="sidebar-menu" data-widget="tree" >
                <li class="header"></li>
                <template v-for="item in data">
                    <v-menu :model="item" :key="item.id"></v-menu>
                </template>
            </ul>
        </section>
    </aside>
</template>

<script>
    import VMenu from './menu.vue'
    export default {
        name: 'v-left-menu',
        props: {
            data: Array
        } ,
        data() {
            return {
                selected_node_id:null
            }
        } ,
        methods : {

        } ,
        components : {
            'v-menu': VMenu,
        }

    }
</script>