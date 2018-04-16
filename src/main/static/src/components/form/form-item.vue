<template>
    <div :class="inline?'layui-inline':'layui-form-item'">
        <label v-if="label" class="layui-form-label">{{label}}</label>
        <slot></slot>
    </div>
</template>

<script>
export default {
    name: 'v-form-item',
    props: {
      span : {type : Number  , default : 12} ,
      label : {type : String } ,
      inline:{type:Boolean , default:false}
    } ,
    computed : {
      col_size : function() {
          return 'col-md-' + this.span
      }
    }
}
</script>