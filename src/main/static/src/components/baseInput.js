export const baseInput = {
    props:{
        name:String ,
        value: String,
        inline:{type:Boolean , default:false} ,
        placeholder: String ,
        wrap_class:String ,
        verify:String
    } ,
    methods: {
        get_value(){
            return this.$refs.input.value
        }
    } ,
    computed: {
        wrapClasses(){
            if(this.wrap_class)
                return this.wrap_class
            return this.inline? 'layui-input-inline':'layui-input-block'
        }
    },
    mounted () {
        layui.use('form' , function(){

        })
    }
}