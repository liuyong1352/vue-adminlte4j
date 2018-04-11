<template>
    <input  :id="eid"
                :placeholder="placeholder"
                :value="value"
                class="layui-input"
                v-on:input="handleInput">
</template>

<script>
import {baseInput}  from '../baseInput'
export default {
  mixins: [baseInput],
  name: 'v-date',
    props : {
        value: String,
        type: {
            type: String,
            default: 'datetime'
        },
        range: {
            type:Boolean ,
            default:false
        } ,
        format: String,
        class: {
            type:String,
            default:''
        }
    },
  data() {
      return {
          currentValue : this.value
      }
  } ,
  computed: {
    eid : function() {
        return this.$attrs.id || "date0"
    }
  },

  methods: {
    get_value: function() {
            return this.$el.value
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
    var config={elem: '#' + this.eid ,type: this.type }
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