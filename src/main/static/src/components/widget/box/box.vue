<template>
    <div :class="classes" v-if="!removed" >
        <div class="box-header" :class="(isBorder || is_solid)?'with-border':''">
            <slot name="icon-title"></slot>
            <h3 v-if="title" class="box-title" v-html="title"></h3>
            <template v-if="$slots.header">
                <slot name="header"></slot>
            </template>
            <div class="box-tools pull-right">
                <button type="button" class="btn btn-box-tool" @click="toggle">
                  <i :class="state?'fa fa-minus':'fa fa-plus'"></i>
                </button>
            </div>
        </div>
        <div class="box-body">
            <slot></slot>
        </div>

        <div v-if="$slots.footer" class="box-footer">
            <slot name="footer"></slot>
        </div>
        <div v-if="loading" class="overlay"><div class="fa fa-refresh fa-spin"></div></div>
    </div>
</template>
<script>
export default {
    name: 'v-box',
    props: {
        title:{ type: String },
        is_solid:   { type: Boolean, default: false },
        isBorder:  { type: Boolean, default: false } ,
        theme: {type:String}
    },
    data() {
        return {
            state:1 ,
            loading:0 ,
            removed:0
        }
    } ,
    methods: {
        expand() {
            $(this.$el).children('.box-body ,.box-footer' ).slideDown(500)
        } ,
        collapse() {
           $(this.$el).children('.box-body ,.box-footer' ).slideUp(500)
        } ,
        toggle() {
            this.state ^= 1
            if(this.state) {
                this.expand()
            } else {
                this.collapse()
            }
        } ,
        remove() {
            this.removed = 1
        }
    } ,
    computed : {
        classes() {
            return ['box' , {
                [`box-${this.theme}`]:this.theme ,
                [`box-solid`]: this.is_solid
            }]
        }
    }
}
</script>
