<template>
    <div  :class="wrapClasses">
        <input type="checkbox" :lay-filter="dynName" v-for="item in items"
            :name="dynName"
            :value="item.code"
            ref="input"
            :title="item.label"
            :checked="isChecked(item)"
            :disabled="disabled"
            lay-skin="primary">
    </div>
</template>
<script>
import {baseInput}  from '../baseInput'
import {baseDict}  from '../baseDict'

export default {
    mixins: [baseInput ,baseDict],
    name: 'v-checkbox',
    mounted () {
        var self = this
        layui.use('form' , function(){
            var form = layui.form
            form.on('checkbox('+ self.dynName +')', function(data){
                self.$emit('input', self.get_value());
                //console.log(data.elem); //得到checkbox原始DOM对象
            })
        })
    }
}
</script>
