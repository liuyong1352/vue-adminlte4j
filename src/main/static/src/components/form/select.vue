<template>
    <div :class="wrapClasses">
        <select :name="dynName" lay-verify="" lay-search>
             <option v-for="item in items" :value="item.code" selected="isChecked(item)">{{item.label}}</option>
        </select>
    </div>
</template>

<script>
import {baseInput}  from '../baseInput'
import {baseDict}  from '../baseDict'
export default {
    mixins: [baseInput ,baseDict],
    name: 'v-select',
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
        layui.use('form' , function(){})
    }
}
</script>
