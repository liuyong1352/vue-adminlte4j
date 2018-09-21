import {baseValidate}   from '../baseValidate'
import {formModelValue}  from '../base/formModelValue'
export const baseForm = {
    mixins:[baseValidate , formModelValue] ,
    props: {
        submit_url:{type :String } ,
        model: String ,
        submit_before: Function ,
        submit_after: Function ,
        has_reset: {type:String ,default:"true"}
    } ,
    data : function() {
        return {
            row_items:[],
            form_inline: false
        }
    } ,
    methods:{
        set_formModel(formModel) {
            if(formModel){
                this.set_formItems(formModel.formItems)
                this.form_inline=formModel.inline
            }
        } ,
        set_formItems(formItems) {
            this.formItems = formItems
            var row_items=[]
            var ts=0 , j=0
            row_items.push([])
            for(var i in formItems) {
                var item = formItems[i]
                ts+=item.span
                if(ts>12) {
                    row_items.push([])
                    j++
                    ts=item.span
                }
                row_items[j].push(item)
            }
            this.row_items = row_items
        } ,

        hide_item(key) {
            for(var i in this.row_items) {
                for(var j in this.row_items[i])
                    if(this.row_items[i][j].key == key)
                        this.row_items[i][j].hidden=true
            }
        },
        show_item(key) {
            for(var i in this.row_items) {
                for(var j in this.row_items[i])
                    if(this.row_items[i][j].key == key)
                        this.row_items[i][j].hidden=false
            }
        } ,

        buildVal: function(item) {
            var val = this.data[item.key]
            if(0 == val) {
                return val
            }
            return val || item.defVal
        } ,
        get_ext_val:function(item , key, defVal){
            if(item.ext)
                return (item.ext)[key]
            return defVal
        } ,
        get_date_type_val:function(item , key) {
            if(item.ext) {
                var t=(item.ext)[key]
                if(t==1)
                    return 'date'
                if(t==2)
                    return 'time'
                if(t==3)
                    return 'year'
                if(t==4)
                    return 'month'
            }
            return 'datetime'
        },
        get_wrapper_class:function (item) {
            return 'layui-input-block'
        } ,
        get_item_value: function(key) {
            return this.$refs[key][0].get_value()
        } ,
        set_item_value(key ,val) {
            this.$refs[key][0].set_value(val)
        } ,
        get_value:function() {
            var jsonData = {}
            for(var i in this.formItems){
                var item=this.formItems[i]
                jsonData[item.key]= this.get_item_value(item.key)
            }
            return jsonData
        } ,
        submit: function(url ,callback) {
            if(!this.validate())
                return
            var formData = this.get_value()
            if(this.submit_before && !this.submit_before(url , formData)) {
                return
            }
            var self=this
            $.post(url,formData).then(function(data){
                if(self.submit_after && !self.submit_after(data)) {
                    return
                }
                callback(data)
            })
        } ,
        reset() {
            for(var k in this.$refs ) {
                this.$refs[k][0].reset()
            }
        },
        internal_submit(){
            this.submit(this.submit_url ,function (data) {
                $.alert(data)
            })
        } ,
        get_verify(item) {
            if(!item.validate)
                return ""
            var t=item.validate.type
            if(t == 1)
                return "required"
            else if (t == (1<<1))
                return "number"
            else if (t == (1<<2))
                return "email"
        }

    },
    computed: {
        dynName() {
            return (this.name || '_form_' + unique_id())
        }
    },
    mounted() {
        this.refresh()
    }
}