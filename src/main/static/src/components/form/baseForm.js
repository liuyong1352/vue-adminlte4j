import {baseValidate}   from '../baseValidate'
export const baseForm = {
    mixins:[baseValidate] ,
    props: {
        ajax_url : String,
        submit_url:{type :String }
    } ,
    data : function() {
        return {
            items:[] ,
            row_items:[],
            data : {},
            form_inline: false
        }
    } ,
    computed : {
        form_unique_id : function(){
            return '$form_' + unique_id()
        }
    } ,
    methods:{
        refresh: function(data) {
            var vm = this
            if(this.ajax_url) {
                var self=this
                axios.get(this.ajax_url , {params:data}).then(function (response) {
                    var formJson = response.data.FormModel.formItems
                    self.items = formJson
                    var row_items=[]
                    var ts=0 , j=0
                    row_items.push([])
                    for(var i in self.items) {
                        var item = self.items[i]
                        ts+=item.span
                        if(ts>12) {
                            row_items.push([])
                            j++
                            ts=item.span
                        }
                        row_items[j].push(item)
                    }
                    self.row_items = row_items
                    self.data = response.data.data||{}
                    self.form_inline=response.data.FormModel.inline
                    vm.$nextTick(function () {
                        layui.use('form' , function(){
                            var form = layui.form
                            form.render()
                            //监听提交
                            form.on('submit(' + this.form_unique_id + ')', function(data){
                                self.internal_submit()
                                return false
                            })
                        })
                    })
                })
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
        formData: function() {
            var jsonData = {}
            for(var i in this.items){
                var item=this.items[i]
                jsonData[item.key]= this.get_item_value(item.key)
            }
            return jsonData
        } ,
        get_item_value: function(key) {
            return this.$refs[key][0].get_value()

        } ,
        get_value:function() {
            return this.formData()
        } ,
        submit: function(url ,callback) {
            if(!this.validate())
                return

            axios.post(url,this.formData()).then(function(response){
                callback(response)
            })
        } ,
        internal_submit: function(){
            this.submit(this.submit_url ,function (response) {
                $.alert(response.data)
            })
        } ,
        get_verify:function(item) {
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
    mounted : function() {
        this.refresh()
    }
}