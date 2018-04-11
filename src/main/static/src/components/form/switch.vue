<template>
    <div :class="wrap_class">
        <input type="checkbox"
            :name="name"
            value="true"
            :checked="isOpen"
            :lay-text="text"
            lay-skin="switch">
    </div>

</template>

<script>
    export default {
        name: 'v-switch',
        props: {
            name : String,
            text : {type:String ,default:'开|关'} ,
            items: Object,
            wrap_class:{type:String},
            isOpen:Boolean
        },
        methods:{
            buildText:function() {
                if(this.items && this.items.length >= 2 )  {
                    return this.items[0].label + '|' + this.items[1].label
                }
                return this.text
            } ,
            get_value:function() {
                return $('input[name=' + 'isAdmin' + ']:checked').length //0 or 1
            }
        } ,
        created () {

        } ,

        mounted () {
            var self = this
            layui.use('form' , function(){

            })
        }
    }
</script>
