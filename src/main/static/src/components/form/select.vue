<template>
    <div :class="wrapClasses">
        <div :class="pop_state?'layui-form-select layui-form-selected':'layui-form-select'" :id="dynName">
            <div class="layui-select-title" @click="pop_up">
                <input :placeholder="placeholder" :value="get_text()" class="layui-input">
                <i class="layui-edge"></i>
            </div>
            <dl v-if="pop_state" class="layui-anim layui-anim-upbit" >
                <dd class="layui-select-tips" @click="change_state()">{{placeholder}}</dd>
                <dd v-for="item in dict" @click="change_state(item)" :class="item.state?'layui-this': ''" >
                    {{item.label}}
                </dd>
            </dl>
        </div>
    </div>
</template>

<script>
    import {baseDict}  from '../baseDict'
    export default {
        mixins: [baseDict],
        name: 'v-select' ,
        data() {
            return {
                pop_state: 0
            }
        } ,
        methods: {
            change_state(item) {
                this.pop_state =0
                if(item) {
                    this.reset_state()
                    item.state = 1
                    this.set_v(item.code)
                } else {
                    this.reset()
                }
            } ,
            pop_up() {
                var self = this
                this.pop_state ^=1
                if(this.pop_state) {
                    eventBus.$on('doc_click', this.doc_click_event)
                }
            },
            get_text() {
                for(var k in this.dict) {
                    if(this.dict[k].state){
                        return this.dict[k].label
                    }
                }
            } ,
            doc_click_event(e) {
                if(!this.$el.contains(e.target)) {
                    this.pop_state =0
                }
            }
        },
        mounted() {

        } ,
        beforeDestroy(){

        }
    }
</script>
