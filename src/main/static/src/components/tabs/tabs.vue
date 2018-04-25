<template>
    <div class="nav-tabs-custom">
        <ul :class="nav_cls">
            <li v-for="(pane , index) in panes" :class="{active: active_id==pane.tab_id}" @click="tab_click(pane)">
                <a :href="'#' + pane.tab_id" data-toggle="tab" aria-expanded="true">{{pane.label}}</a>
            </li>
            <li v-if="$slots.header" :class="header_cls">
                <slot name="header"></slot>
            </li>
        </ul>
        <div class="tab-content">
            <slot></slot>
        </div>
    </div>
</template>

<script>
export default {
    props:{
        nav_float :{type:String, default:"left"}
    } ,
    data() {
        return {
          panes: [] ,
          active_id:''
        }
    } ,
    methods: {
        addPane(pane){
            if(pane.active || this.panes.length == 0)
                this.active_id = pane.tab_id
            this.panes.push(pane)
        } ,
        tab_click(pane){
            this.$emit("on-tab-click", pane)
        }

    } ,
    computed:{
        nav_cls(){
            return 'nav nav-tabs ' + (this.nav_float == 'right'?'pull-right':'')
        } ,
        header_cls() {
            return 'header ' + (this.nav_float  == 'right'?'pull-left':'pull-right')
        }
    } ,
    mounted(){
        $("#" + this.active_id).addClass("active")
    }

}
</script>