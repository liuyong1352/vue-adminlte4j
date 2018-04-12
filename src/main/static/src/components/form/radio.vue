<template>
    <div :class="wrapClasses">
        <input type="radio" :lay-filter="name" v-for="item in items"
            :name="name"
            :value="item.code"
            :title="item.label"
            :checked="isChecked(item)">
    </div>

</template>

<script>
import {baseInput}  from '../baseInput'
export default {
    mixins: [baseInput],
    name: 'v-radio',
        props: {
            checkedValue: String,
            items : Object
        },
        methods:{

            get_value:function() {
                return  $('input[name=' + this.name + ']:checked').val()
            } ,
            isChecked : function(item) {
                if(this.checkedValue)  {
                    var c = this.checkedValue
                    if(c == item.code)
                    return true
                }
                return item.checked
            }
        } ,
        created () {

        } ,

        mounted () {
            var self = this
            layui.use('form' , function(){
                var form = layui.form
                form.on('radio('+ self.name +')', function(data){
                    for(var i in self.items) {
                        var item=self.items[i]
                        if(item.code == data.value)
                            item.checked = data.elem.checked
                    }
                              //console.log(data.elem); //得到checkbox原始DOM对象
                })
            })
            //console.log("check-box ! mounted")
        }
    }
</script>
