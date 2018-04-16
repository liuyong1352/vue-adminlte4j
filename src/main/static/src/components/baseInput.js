export const baseInput = {
    props:{
        name:String ,
        value: String,
        inline:{type:Boolean , default:false} ,
        placeholder: {String , default:'enter...'} ,
        wrap_class:String ,
        verify:String
    } ,
    created(){

    } ,
    data() {

    } ,
    methods: {
        set_value(val){
            this.value=val
            this.$refs.input.value=this.value
        } ,
        get_value(){
            return this.$refs.input.value
        } ,
        handleInput(event) {
            var value = event.target.value
            this.$emit('input', value)
            this.value = value
        }
    } ,
    computed: {
        wrapClasses(){
            if(this.wrap_class)
                return this.wrap_class
            return this.inline? 'layui-input-inline':'layui-input-block'
        }  ,
        dynName() {
            return (this.name || '_input_' + unique_id())
        }
    },
    mounted () {
        layui.use('form' , function(){})
    }
}