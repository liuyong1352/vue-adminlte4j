<template>
    <div :class="wrapClasses">
        <input type="radio" :lay-filter="dynName" v-for="item in items"
            :name="dynName"
            ref="input"
            :value="item.code"
            :title="item.label"
            :checked="isChecked(item)">
    </div>
</template>

<script>
import {baseInput}  from '../baseInput'
import {baseDict}  from '../baseDict'
export default {
    mixins: [baseInput ,baseDict],
    name: 'v-radio',
    props: {
        checkedValue: String
    },
    methods:{
        isChecked : function(item) {
            if(this.checkedValue)  {
                var c = this.checkedValue
                if(c == item.code)
                return true
            }
            return item.checked
        }
    } ,
        mounted () {
            var self = this
            layui.use('form' , function(){
                var form = layui.form
                form.on('radio('+ self.dynName +')', function(data){

                })
            })

        }
    }
</script>
