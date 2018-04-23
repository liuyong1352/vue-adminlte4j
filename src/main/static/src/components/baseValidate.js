var verify = {
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
export const baseValidate = {
    props:{

    },
    methods : {
        validate: function() {
            var device = layui.device()
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
                        ,isFn = typeof verify[thisVer] === 'function';
                    if(verify[thisVer]){
                        isTrue = isFn ? errorText = verify[thisVer](value, item) : !verify[thisVer][0].test(value);
                        errorText = errorText || verify[thisVer][1];

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
    }
}