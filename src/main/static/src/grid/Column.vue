<template>
  <div :class="this_class()">
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: 'v-col',
  props: {
    'class': {
      type: String,
      default: ''
    } ,
    span:{
        type : Number
    }
  } ,
  methods : {
    this_class : function(){
            if(this['class'])
                return this['class']
            if(this.span)
                return 'col-md-' + this.span
            return ''
    }
  } ,
  computed : {

  }
}
</script>