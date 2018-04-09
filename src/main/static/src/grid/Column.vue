<template>
  <div :class="this_class">
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: 'column',
  props: {
    'class': {
      type: String,
      default: ''
    } ,
    span:{
        type : Number ,
        default:12
    }
  } ,
  computed : {
    this_class : function(){
        return 'col-md-' + this.span
    }
  }
}
</script>