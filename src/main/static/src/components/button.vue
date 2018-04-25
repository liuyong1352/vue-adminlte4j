<template>
    <button :type="btnType" class="btn" :class="[isBlock?'btn-block':'', btnTheme, disabled, flat, size, getAlign]"
        @click="handleClick">
        <slot></slot>
    </button>
</template>

<script>
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
                default: 'success'
            },
            isDisabled: {
                type: Boolean,
                default: false
            },
            isFlat: {
                type: Boolean,
                default: false
            },
            size: {
                type: String,
                default: 'lg'
            },
            isBlock: {
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
        created () {

        },
        computed: {

            btnTheme () {
                return `btn-${this.theme}`
            },
            disabled () {
                return this.isDisabled ? 'disabled' : ''
            },
            flat () {
                return this.isFlat ? 'btn-flat' : ''
            },
            getAlign () {
                switch (this.align) {
                    case 'left':
                    case 'right':
                        break
                    default:
                        return ''
                }

                return 'pull-' + this.align
            }
        }

    }
</script>