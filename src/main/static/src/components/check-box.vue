<template>
    <div>
        <input type="checkbox" :lay-filter="name" v-for="item in items"
            :name="name"
            :value="item.key"
            :title="item.title"
            :checked="item.checked">
    </div>

</template>

<script>
    export default {
        name: 'v-checkbox',
        props: {
            name : String,
            items : Object
        },
        methods:{
            get_values:function() {
                var r = []
                for(var i in this.items) {
                    var item=this.items[i]
                    if(item.checked )
                        r.push(item.key)
                }
                return r
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
                        if(item.key == data.value)
                            item.checked = data.elem.checked
                    }
                              //console.log(data.elem); //得到checkbox原始DOM对象
                })
            })
        }
    }
</script>
