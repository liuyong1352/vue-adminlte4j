<template>
    <div>
        <input type="radio" :lay-filter="name" v-for="item in items"
            :name="name"
            :value="item.code"
            :title="item.label"
            :checked="isChecked(item)">
    </div>

</template>

<script>
    export default {
        name: 'v-radio',
        props: {
            name : String,
            checked: String,
            items : Object
        },
        methods:{
            get_values:function() {
                var s
                $('input[name=' + this.name + ']:checked').each(function(){
                    s = $(this).val()
                })
                return s
            } ,
            isChecked : function(item) {
                if(this.checked)  {
                    var c = this.checked
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
