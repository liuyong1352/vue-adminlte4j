<template>
    <div :id="id" :class="wrapClasses">
        <input type="checkbox" :lay-filter="name" v-for="item in items"
            :name="name"
            :value="item.code"
            :title="item.label"
            :checked="isChecked(item)"
            :disabled="disabled"
            lay-skin="primary">
    </div>

</template>

<script>
import {baseInput}  from '../baseInput'
export default {
    mixins: [baseInput],
    name: 'v-checkbox',
    props: {
        checkedValues:String,
        items : Object
    },
        methods:{
            get_value:function() {
                var arr = []
                $('input[name=' + this.name + ']:checked').each(function(){
                    arr.push($(this).val())
                })
                return arr.join(',')
            } ,
            isChecked : function(item) {
                if(this.checkedValues)  {
                    var arr = this.checkedValues.split(',')
                    for(var i in arr ){
                        if(arr[i] == item.code)
                            return true
                    }
                }
                return item.checked
            }
        } ,
        mounted () {
            var self = this
            layui.use('form' , function(){
                var form = layui.form
                form.on('checkbox('+ self.name +')', function(data){
                    for(var i in self.items) {
                        var item=self.items[i]
                        if(item.code == data.value)
                            item.checked = data.elem.checked
                    }
                    self.$emit('input', self.get_value());
                              //console.log(data.elem); //得到checkbox原始DOM对象
                })
            })
            //console.log("check-box ! mounted")
        }
    }
</script>
