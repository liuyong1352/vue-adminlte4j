<template>
    <div class="nav-tabs-custom">
        <ul :class="nav_cls">
            <li v-for="(pane , index) in panes" :id="'li' + pane.tab_id" :class="{active: active_id==pane.tab_id}" @click="tab_click(pane)">
                <a :href="'#' + pane.tab_id" data-toggle="tab" aria-expanded="true">{{pane.label}}</a>
            </li>

            <li v-for="(pane , index) in dynamic" :id="'li' + pane.tab_id" :class="{active: active_id==pane.tab_id}" @click="tab_click(pane)">
                <a :href="'#' + pane.tab_id" data-toggle="tab" aria-expanded="true">{{pane.label}} <i class="fa fa-times" @click="removePane(pane , $event)"></i></a>
            </li>

            <li v-if="$slots.header" :class="header_cls">
                <slot name="header"></slot>
            </li>
        </ul>
        <div class="tab-content">
            <slot></slot>
            <div v-for="(item , index) in dynamic" class="tab-pane" :id="item.tab_id"
                v-html="dynamic_render(item)" :data-params="item.params"></div>
        </div>
    </div>
</template>

<script>
export default {
    name:'v-tab',
    props:{
        nav_float :{type:String, default:"left"}
    } ,
    data() {
        return {
          panes: [] ,
          dynamic:[] ,
          active_id:''
        }
    } ,
    methods: {
        addChildPane(pane){
            if(pane.active || this.panes.length == 0)
                this.active_id = pane.tab_id
            this.panes.push(pane)
        } ,
        addPane(pane) {
            pane.tab_id = "tab_" + unique_id()
            if(pane.active)
                this.active_id = pane.tab_id
            this.dynamic.push(pane)

            if(pane.url) {
                this.$nextTick(function(){
                    $("#" +pane.tab_id).load(pane.url)
                })
            }

            this.$emit("on-tab-add", pane)
        } ,
        removePane(pane , event) {

            for(var i=0;i<this.dynamic.length;i++) {
                if(this.dynamic[i].tab_id == pane.tab_id) {
                    if(this.active_id == pane.tab_id) {
                        this.active_id= i ? this.dynamic[i-1].tab_id : this.panes[this.panes.length - 1].tab_id
                    }
                    this.dynamic.splice(i,1)
                    break
                }
            }
            //$("#" + pane.tab_id).remove()
            this.$emit("on-tab-remove", pane)
            event.stopPropagation()
            event.preventDefault()
        } ,
        tab_click(pane){
            this.active_id = pane.tab_id
            this.$emit("on-tab-click", pane)
        } ,

        change_tab(index) {
            var vm = this
            var old_active_id = this.active_id
            this.$nextTick(function () {
                if(vm.panes.length <= index)
                    console.error("input index too large")
                $("#" + old_active_id).removeClass("active")
                $("#li" + old_active_id).removeClass("active")
                this.active_id = vm.panes[index].tab_id
                $("#" + vm.active_id).addClass("active")
                $("#li" + vm.active_id).addClass("active")
            })
        } ,
        dynamic_render(item) {
            return item.el ? $(item.el).html() : item.content
        }

    } ,
    updated() {
        $("#" + this.active_id).siblings().removeClass('active')
        $("#" + this.active_id).addClass("active")
    },
    computed:{
        nav_cls(){
            return 'nav nav-tabs ' + (this.nav_float == 'right'?'pull-right':'')
        } ,
        header_cls() {
            return 'header ' + (this.nav_float  == 'right'?'pull-left':'pull-right')
        }
    } ,
    mounted(){
        //$("#" + this.active_id).addClass("active")
    }

}
</script>