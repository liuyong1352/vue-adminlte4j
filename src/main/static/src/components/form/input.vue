<template>
    <div :class="wrapClasses">
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

  },
  methods: {

    setCurrentValue(value) {
       this.currentValue = value
    }
  }
}
</script>