export const baseInput = {
    props:{
        name:String ,
        value: String,
        inline:{type:Boolean , default:false} ,
        placeholder: {type:String ,default:'Enter ... '},
        wrap_class:String ,
        verify:String ,
        disabled: {type: Boolean,default: false}
    },
    data() {
        return {
            v: this.value
        }
    } ,
    watch: {
        value(newV) {
            this.set_value(newV)
        }
    } ,
    methods: {
        set_v(v){
            this.v = v
            this.$emit('input', this.v)
        } ,
        set_value(v){
            this.set_v(v)
        } ,
        get_value(){
            return this.v
        } ,
        reset() {
            this.set_value('')
        } ,
        handleInput(event) {
            var v = event.target.value
            this.set_value(v)
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
    }
}