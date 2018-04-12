<template>
    <div :class="wrapClasses">
        <input  :id="dynName"
                    :placeholder="placeholder"
                    :value="value"
                    ref="input"
                    :lay-verify="verify"
                    class="layui-input"
                    v-on:input="handleInput">
    </div>
</template>

<script>
import {baseInput}  from '../baseInput'
export default {
  mixins: [baseInput],
  name: 'v-date',
    props : {
        type: {
            type: String,
            default: 'datetime'
        },
        range: {
            type:Boolean ,
            default:false
        } ,
        format: String
    },
  data() {
      return {
          currentValue : this.value
      }
  } ,
  methods: {
    setCurrentValue(value) {
       this.currentValue = value
    }
  } ,
  mounted : function() {
    var config={elem: '#' + this.dynName ,type: this.type }
    if(this.range)
        config.range=this.range
    if(this.format)
        config.format=this.format
    layui.use(['laydate'] , function(){
        layui.laydate.render(config)
    })

  }
}
</script>