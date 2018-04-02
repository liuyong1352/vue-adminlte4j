<template>
    <div>
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
            isOpen:Boolean
        },
        methods:{
            get_values:function() {
                return $('input[name=' + 'isAdmin' + ']:checked').length >0
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
