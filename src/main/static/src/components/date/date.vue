<template>
    <div :class="wrapClasses">
        <input :id="dynName"
                    :placeholder="placeholder"
                    :value="v"
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
        range: { type:String } ,
        format: String
    },
    methods: {

    } ,
    mounted : function() {
        var config={elem: '#' + this.dynName ,type: this.type }
        if(this.range)
            config.range=this.range
        if(this.format)
            config.format=this.format
        if(this.value) {
            if($.isNumeric(this.value))
                config.value=$.toDateString(this.value, this.format)
            else
                config.value=this.value
        }

        var self =this
        config.done = function(value, date, endDate) {
            self.set_value(value)
        }

        layui.use(['laydate'] , function(){
            layui.laydate.render(config)
        })
    }
}
</script>