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
        type : Number
    }
  } ,
  computed : {
    this_class : function(){
        if(this['class'])
            return this['class']
        if(this.span)
            return 'col-md-' + this.span
        return ""
    }
  }
}
</script>