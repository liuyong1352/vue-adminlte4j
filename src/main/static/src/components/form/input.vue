<template>
    <div :id="id" :class="wrapClasses">
        <input :type="type" class="layui-input"
            ref="input"
            :lay-verify="verify"
            :placeholder="placeholder"
            :value="value"
            v-on:input="handleInput">
    </div>
</template>

<script>
import {baseInput}  from '../baseInput'
export default {
    mixins: [baseInput],
    name: 'v-input',
    props: {
      id :String ,
      value: [String, Number] ,

      type: {
              type: String,
              default: 'text'
      }

    } ,
  data() {
    return {
        currentValue : this.value
    }
  } ,

  watch: {
     value (val) {
         this.setCurrentValue(val);
     }
  },
  methods: {
    handleInput : function(event) {
        var value = event.target.value
        this.$emit('input', value)
        this.setCurrentValue(value)
        //this.$emit('on-change', event)
        //this.$refs.input.value = value
    },
    setCurrentValue(value) {
       this.currentValue = value
    }
  }
}
</script>