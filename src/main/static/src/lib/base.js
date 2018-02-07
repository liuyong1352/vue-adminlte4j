+function ($) {


    $.extend({
//layer.alert('a',{title:'b' , icon:1} ,function(index){layer.close(index) })

        alert : function (content , options , type ,callback ) {

            function alert_json(data , _options) {
                _options = _options || {}

                if(data.code == 1) {
                    _options.icon=1
                } else {
                    _options.icon=2
                }
                return layer.alert(data.msg , _options)
            }

            if(typeof(content)=="object") {
                return alert_json(content)
            }

            var _options = options || {}

            if(type == undefined || type == null ) {
                if(type == 1 || type == "success" || type == "yes" || type == "ok"  )
                    _options.icon= 1
                else if (type == 0 || type == "warn"|| type == "error" || type == "no")
                    _options.icon = 2
            }

            return layer.alert(content ,_options , function (index) {
                if(callback)
                    callback(index)
                else
                    layer.close(index)
            } )
        }


    })
}(jQuery)