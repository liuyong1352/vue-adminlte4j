<template>
    <div>
        <input type="checkbox" :lay-filter="name" v-for="item in items"
            :name="name"
            :value="item.code"
            :title="item.label"
            :checked="isChecked(item)"
            lay-skin="primary">
    </div>

</template>

<script>
    export default {
        name: 'v-checkbox',
        props: {
            name : String,
            checkedValues:String,
            items : Object
        },
        methods:{
            get_values:function() {
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
        created () {

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
                              //console.log(data.elem); //得到checkbox原始DOM对象
                })
            })
            //console.log("check-box ! mounted")
        }
    }
</script>
