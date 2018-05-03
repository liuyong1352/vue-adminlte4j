<template>
    <button :type="btnType" :class="classes"
        @click="handleClick">
        <slot></slot>
    </button>
</template>

<script>
    var prefixCls='btn'
    export default {
        name: 'v-button',
        props: {
            btnType:{
              type:String,
              default:"button"
            },
            name: {
                type: String,
                default: 'Button'
            },
            theme: {
                type: String,
                default: 'default'
            },
            disabled: {
                type: Boolean,
                default: false
            },
            flat: {
                type: Boolean,
                default: false
            },
            size: {
                type: String
            },
            block: {
                type: Boolean,
                default: true
            },
            align: {
                type: String,
                default: ''
            }
        },
        methods: {
            handleClick(event) {
                this.$emit('click', event);
            }
        } ,
        computed: {
            classes() {
                return [`${prefixCls}`,{
                    [`${prefixCls}-${this.theme}`]:this.theme ,
                    [`${prefixCls}-block`]: this.block ,
                    [`${prefixCls}-${this.size}`]: this.size,
                    [`${prefixCls}-flat`]: this.flat ,
                    [`disabled`]: this.disabled
                }]
            }
        }

    }
</script>