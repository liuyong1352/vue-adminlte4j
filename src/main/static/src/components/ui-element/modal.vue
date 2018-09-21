<template>
    <div class="modal fade" :id="dynId">
        <div :class="modal_cls">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
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
</template>
<script>
var prefixCls='modal'
export default {
    name: 'v-modal',
    props: {
        id:String  ,
        title:String ,
        size: String
    } ,
    methods: {
        toggle() {
            $('#'+ this.dynId).modal('toggle')
        } ,
        show() {
            $('#'+ this.dynId).modal('show')
        } ,
        hide() {
            $('#'+ this.dynId).modal('hide')
        }
    } ,
    computed: {
        dynId() {
            return (this.id || '_modal_' + unique_id())
        } ,
        modal_cls() {
            return [`modal-dialog`,{
                                [`${prefixCls}-${this.size}`]: this.size }]
        }
    }

}
</script>

