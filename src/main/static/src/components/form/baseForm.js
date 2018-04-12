import BaseFormItem   from './base-form-item.vue'
export const baseForm = {
    props: {
        ajax_url : String,
        submit_url:{type :String }
    } ,
    data : function() {
        return {
            items:[] ,
            row_items:[],
            data : {},
            form_inline: false ,
            verify : {
                required: [
                    /[\S]+/
                    ,'必填项不能为空'
                ]
                ,phone: [
                    /^1\d{10}$/
                    ,'请输入正确的手机号'
                ]
                ,email: [
                    /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/
                    ,'邮箱格式不正确'
                ]
                ,url: [
                    /(^#)|(^http(s*):\/\/[^\s]+\.[^\s]+)/
                    ,'链接格式不正确'
                ]
                ,number: function(value){
                    if(!value || isNaN(value)) return '只能填写数字'
                }
                ,date: [
                    /^(\d{4})[-\/](\d{1}|0\d{1}|1[0-2])([-\/](\d{1}|0\d{1}|[1-2][0-9]|3[0-1]))*$/
                    ,'日期格式不正确'
                ]
                ,identity: [
                    /(^\d{15}$)|(^\d{17}(x|X|\d)$)/
                    ,'请输入正确的身份证号'
                ]
            }
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
        } ,
        validate: function() {
            var self=this ,device = layui.device()
            var DANGER = 'layui-form-danger'
            var elem = $(this.$el)
            var verifyElem = elem.find('*[lay-verify]') //获取需要校验的元素
            for(var i=0 ; i<verifyElem.length;i++ ) {
                var item=verifyElem[i]
                var othis = $(item)
                    ,vers = othis.attr('lay-verify').split('|')
                    ,verType = othis.attr('lay-verType') //提示方式
                    ,value = othis.val()
                othis.removeClass(DANGER)
                for(var j=0 ; j<vers.length ;j++) {
                    var isTrue //是否命中校验
                        ,errorText = '' //错误提示文本
                        ,thisVer=vers[j]
                        ,isFn = typeof this.verify[thisVer] === 'function';
                    if(this.verify[thisVer]){
                        isTrue = isFn ? errorText = self.verify[thisVer](value, item) : !self.verify[thisVer][0].test(value);
                        errorText = errorText || self.verify[thisVer][1];

                        //如果是必填项或者非空命中校验，则阻止提交，弹出提示
                        if(isTrue){
                            //提示层风格
                            if(verType === 'tips'){
                                layer.tips(errorText, function(){
                                    if(typeof othis.attr('lay-ignore') !== 'string'){
                                        if(item.tagName.toLowerCase() === 'select' || /^checkbox|radio$/.test(item.type)){
                                            return othis.next();
                                        }
                                    }
                                    return othis;
                                }(), {tips: 1});
                            } else if(verType === 'alert') {
                                layer.alert(errorText, {title: '提示', shadeClose: true});
                            } else {
                                layer.msg(errorText, {icon: 5, shift: 6});
                            }
                            if(!device.android && !device.ios) item.focus(); //非移动设备自动定位焦点
                            othis.addClass(DANGER);
                            return false
                        }
                    }
                }
            }
            return true
        }
    },
    mounted : function() {
        this.refresh()
    },
    components : {
        'v-base-form-item': BaseFormItem,
    }
}