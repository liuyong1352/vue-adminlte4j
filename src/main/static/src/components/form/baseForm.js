import VInput       from '../form/input.vue'
import VDate        from '../date/date.vue'
import VCheckBox    from './check-box.vue'
import VSwitch      from './switch.vue'
import VRadio       from './radio.vue'
import IconSelector  from '../ui-element/button/icon-selector-btn.vue'

export const baseForm = {
    props: {
        ajax_url : String,
        submit_url:{type :String }
    } ,
    data : function() {
        return {
            items:[] ,
            data : {},
            form_inline: false
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
                    self.data = response.data.data||{}
                    self.form_inline=response.data.FormModel.inline
                    vm.$nextTick(function () {
                        layui.use('form' , function(){
                            var form = layui.form
                            form.render()
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
        get_class : function(item){
            if(item.hidden)
                return 'hidden col-md-12'
            else
                return item.span?('col-md-'+item.span):''
        } ,
        get_wrapper_class:function (item) {
            if(item.type == 10)
                return 'layui-inline'
            return 'layui-input-block'
        } ,
        formData: function() {
            var jsonData = {}
            for(var i in this.items){
                var item = this.items[i]
                if(item.type == 3 || item.type == 4 || item.type == 5) {
                    jsonData[item.key]=this.$refs[item.key][0].get_values()
                } else {
                    jsonData[item.key]=$("#" + item.key).val()
                }
            }
            return jsonData
        } ,
        get_values:function() {
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
            this.submit(this.submit_url , function(response){
                $.alert(response.data)
            })
        } ,
        validate: function() {
            var result = true
            for(var i=0 ; i<this.items.length;i++){
                if(this.items[i].validate)
                    result = this.validate_item(this.items[i]) && result
            }
            return result
        } ,
        validate_item:function(item) {
            return $.validate(item)
        }
    },
    mounted : function() {
        this.refresh()
    } ,
    components: {
        'v-icon-selector': IconSelector,
        'v-input': VInput,
        'v-checkbox': VCheckBox,
        'v-switch': VSwitch,
        'v-date': VDate,
        'v-radio' : VRadio
    }
}