<template>
    <div class="layui-form-item">
        <label v-if="label" class="layui-form-label">{{label}}</label>
        <div v-if="$slot" class="layui-input-block">
          <slot></slot>
        </div>
    </div>
</template>

<script>
export default {
    name: 'v-form-item',
    props: {
      span : {type : Number  , default : 12} ,
      label : {type : String } ,
      data: String
    } ,
    computed : {
      col_size : function() {
          return 'col-md-' + this.span
      }
    }
}
</script>