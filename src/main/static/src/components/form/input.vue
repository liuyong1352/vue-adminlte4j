<template>
    <div :class="wrapClasses">
        <input :id="vid" :type="type" :class="inputClasses"
            ref="input"
            :placeholder="placeholder"
            :value="value"
            v-on:input="handleInput">
    </div>
</template>

<script>
export default {
  name: 'v-input',
  props: {
    vid :String ,
    value: [String, Number] ,
    placeholder: String,
    type: {
            type: String,
            default: 'text'
    },
    class: {
        type:String,
        default:''
    }

  } ,
  data() {
    return {
        currentValue : this.value
    }
  } ,
  computed: {
    wrapClasses(){
        return 'layui-input-inline'
    },
    inputClasses(){
        return 'layui-input'
    }
  },
  watch: {
     value (val) {
         this.setCurrentValue(val);
     }
  },
  methods: {
    get_value:function() {
        return this.$el.getElementsByTagName('input')[0].value
    } ,
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
  } ,
  mounted : function() {

  }
}
</script>