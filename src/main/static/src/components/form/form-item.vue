<template>
    <div :class="col_size">
        <div class="form-group">
          <label v-if="label" class="col-sm-2 control-label">{{label}}</label>
          <div class="col-sm-10">
              <slot></slot>
          </div>
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
  } ,
  mounted : function() {
    console.log("form + mounted!!")
  }
}
</script>