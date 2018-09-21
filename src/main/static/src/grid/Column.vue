<template>
  <div :class="this_cls">
    <slot></slot>
  </div>
</template>

<script>
export default {
    name: 'v-col',
    props: {
        span:{
            type : Number , default:12
        }
    } ,
    computed : {
        this_cls() {
            return 'col-md-' + this.span
        }
    }
}
</script>