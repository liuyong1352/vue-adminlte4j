<template>
    <transition name="fade">
        <div v-if="visible" class="modal" :style="styleObj">
            <div :class="modal_cls" >
                <div class="modal-content" >
                    <div class="modal-header">
                        <button type="button" class="close"  aria-label="Close" @click="close">
                          <span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">{{title}}</h4>
                    </div>
                    <div class="modal-body" >
                        <slot></slot>
                    </div
                    <div v-if="$slots.footer" class="modal-footer">
                        <slot name="footer"></slot>
                    </div>
                </div>
            </div>
        </div>
    </transition>
</template>
<script>
var prefixCls='modal'
export default {
    name: 'v-dialog',
    props: {
        id:String  ,
        title:String ,
        size: String
    } ,
    data() {
        return {
            visible:0 ,
            styleObj: {
                display:'block'
            }
        }
    } ,
    methods: {
        toggle() {
            this.visible^=1
        } ,
        show() {
            this.visible=1
        } ,
        hide() {
            this.visible=0
        } ,
        close() {
            this.hide()
        }
    } ,
    computed: {
        modal_cls() {
            return [`modal-dialog`,{
                                [`${prefixCls}-${this.size}`]: this.size }]
        }
    }

}
</script>

