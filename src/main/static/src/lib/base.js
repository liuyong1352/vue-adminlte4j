(function($){
    //首先备份下jquery的ajax方法
    var _ajax=$.ajax;

    //重写jquery的ajax方法
    $.ajax=function(opt){
        var method = opt.type
        if(method && (method == 'POST' || method == 'DELETE')) {
            if(!opt.contentType) {
                opt.contentType = 'application/json; charset=utf-8'
            }
            if(opt.contentType.startsWith('application/json')) {
                opt.data = JSON.stringify(opt.data || {})
            }
        }
        var noop = function() {}
        var onError=function(xhr, textStatus){
            if(xhr.status == 401 ) { //401 未授权
                var l = xhr.getResponseHeader("location")||xhr.getResponseHeader("Location")
                if(l == null ) {
                    $.load_page(null , xhr.responseText)
                } else {
                    var ds = decodeURIComponent(l)
                    var origin = window.location.origin
                    var i = ds.indexOf(origin)
                    var sp = ds.substr(0 , i)
                    //var ss = ds.substring(i)
                    //var tail = ss.substr(origin.length)

                    var loginUrl = sp + encodeURIComponent(window.location.href)
                    window.location.href = loginUrl
                }
            } else {
                message.error("网络异常，请联系管理员！" , 5000 )
            }
        }
        var onComplete=function (xhr, textStatus) {
            if(xhr.status==200 ) {
                if(xhr.responseJSON && xhr.responseJSON.code == 401)
                    window.location.href =  xhr.responseJSON.location
            }
        }
        //备份opt中error和success方法
        var fn = {
            error:opt.error || onError,
            success:opt.success || noop,
            complete:opt.complete || noop
        }
        //扩展增强处理
        var _opt = $.extend(opt,{
            error:function(XMLHttpRequest, textStatus, errorThrown){
                //错误方法增强处理
                fn.error(XMLHttpRequest, textStatus, errorThrown);
            },
            success:function(data, textStatus){
                //成功回调方法增强处理
                fn.success(data, textStatus);
            },
            beforeSend:function(xhr){
                //提交前回调方法
                //console.log( "hre1"+ xhr )
            },
            complete:function(xhr, textStatus){
                fn.complete(xhr, textStatus)
            }
        });
        return _ajax(_opt);
    };

})(jQuery);

+function ($,window, document) {

    /**注册使用event - bus***/
    window.eventBus = new Vue()
    function getQueryStringJson() {
        var pairs = location.search.slice(1).split('&')  //去掉问号
        var result = {};
        pairs.forEach(function(pair) {
            pair = pair.split('=');
            result[pair[0]] = decodeURIComponent(pair[1] || '');
        })

        var hash = window.location.hash
        if(hash && hash.length > 0) {
            var hq_arr = hash.split('?')
            if(hq_arr.length > 1) {
                pairs = hq_arr[1].split('&')
                for(var i = 0 ; i < pairs.length  ; i++ ) {
                    var pair = pairs[i].split('=');
                    result[pair[0]] = decodeURIComponent(pair[1] || '')
                }
            }
        }

        return result
    }

    function getQueryVariable(key) {
        var pairs = location.search.slice(1).split('&')
        for(var i = 0 ; i < pairs.length  ; i++ ) {
            var pair = pairs[i].split('=');
            if (pair[0] == key) {
                return decodeURIComponent(pair[1]);
            }
        }

        var hash = window.location.hash
        if(hash && hash.length > 0) {
            var hq_arr = hash.split('?')
            if(hq_arr.length > 1) {
                pairs = hq_arr[1].split('&')
                for(var i = 0 ; i < pairs.length  ; i++ ) {
                    var pair = pairs[i].split('=');
                    if (pair[0] == key) {
                        return decodeURIComponent(pair[1]);
                    }
                }
            }
        }
        console.log('Query variable %s not found', key);
    }

    function isNullOrEmpty(s) {
        return (s == undefined || s == null || s == '')
    }

    function prefixFill(num, length){
        var str = '';
        num = String(num);
        length = length || 2;
        for(var i = num.length; i < length; i++){
            str += '0';
        }
        return str + num;
    };

    function filterArray(arr) {
        var a = []
        for(var i in arr) {
            var o = arr[i]
            a.push(filterObj(o))
        }
        return a
    }

    function filterObj(obj) {
        if(obj == undefined || obj == null )
            return obj
        if(typeof obj == 'string' || $.isNumeric(obj) || $.isBoolean(obj))
            return obj

        if($.isArray(obj))
            return filterArray(obj)
        var _d={}
        for(var k in obj) {
            var v = obj[k]
            if(v == undefined || v == null || v.length==0)
                continue
            _d[k] = filterObj(v)
        }
        return _d ;
    }

    function ajaxBuilder(url  , method , params ) {
        var _d = filterObj(params)
        this.settings = {url:url , data: _d,
            dataType: "json" ,
            type:method
        }
    }

    ajaxBuilder.prototype.dataType=function(dataType) {
        this.settings.dataType = dataType
    }

    ajaxBuilder.prototype.contentType=function(contentType) {
        this.settings.contentType = contentType
    }

    ajaxBuilder.prototype.then = function(fn) {
        this.settings.success = fn
        $.ajax(this.settings)
    }

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
        } ,

        msg: function(msg) {
            layer.msg(msg)
        } ,
        isBoolean: function (o) {
            return (typeof o) === 'boolean'
        } ,
        getQueryStr : function (key) {
            return getQueryVariable(key)
        } ,
        parseQueryStr : function () {
            return getQueryStringJson()
        } ,
        isNullOrEmpty : function(str) {
            return isNullOrEmpty(str)
        } ,
        isEmpty :function (str) {
            return isNullOrEmpty(str)
        } ,
        post:function(url , params) {
            return new ajaxBuilder(url ,"POST", params )
        } ,
        get:function(url , params) {
            return new ajaxBuilder(url , "GET" , params )
        } ,
        delete:function(url , params) {
            return new ajaxBuilder(url , "DELETE" , params )
        } ,
        load_page:function(url , html) {
            //$(document).off('click' ,'#_layout')
            var screen_content = $("#app")
            $("#app").children().remove()
            if(url)
                screen_content.load(url)
            else
                screen_content.html(html)
        } ,
        getTabPaneParentId(id) {
            var p = $("#" + id ).parent()
            var pid = p.attr('id')
            p.html($('#' + id).html())
            //$("#" + id).attr("id" , newId)
            return pid
        } ,
        toDateString:function (d , format) {
            d = d || new Date()
            if($.isNumeric(d))
                d = Number(d)
            var date = new Date(d)
                ,ymd = [
                prefixFill(date.getFullYear(), 4)
                ,prefixFill(date.getMonth() + 1)
                ,prefixFill(date.getDate())
            ]
                ,hms = [
                prefixFill(date.getHours())
                ,prefixFill(date.getMinutes())
                ,prefixFill(date.getSeconds())
            ]
            format = format || 'yyyy-MM-dd HH:mm:ss'
            return format.replace(/yyyy/g, ymd[0])
                .replace(/MM/g, ymd[1])
                .replace(/dd/g, ymd[2])
                .replace(/HH/g, hms[0])
                .replace(/mm/g, hms[1])
                .replace(/ss/g, hms[2])
        }
    })

    /**
     * 注册全局layer
     */
    layui.use(['layer'] , function () {
        window.layer= layui.layer
        window.alert = window.alert || {
            info(text) {
                layer.alert(text , {icon: 1})
            }

        }

        function lay_msg(text ,duration ,icon,fn) {
            if($.isPlainObject(text)) {
                layer.msg(text.msg, {icon: text.code || 2,time: duration||3000 }, fn)
            } else {
                layer.msg(text, {icon: icon||1,time: duration||3000 }, fn)
            }
        }
        window.message = window.message ||
            {
                msg(text , duration, fn) {
                    lay_msg(text, duration, fn)
                } ,
                info(text , duration ,fn) {
                    lay_msg(text , duration  , 1 ,fn)
                } ,
                success(text , duration ,fn) {
                    lay_msg(text , duration  , 1 ,fn)
                } ,
                error(text , duration, fn) {
                    lay_msg(text , duration  , 2 ,fn)
                } ,
                warn(text , duration, fn) {
                    lay_msg(text , duration  , 7 ,fn)
                }
            }
    })

    /**
     * 注册全局id获取器
     * @returns {number}
     */
    window.unique_id= function () {
        return (window.$id = window.$id? (window.$id + 1) : 6)
    }

    Vue.prototype.ajax = function (url  , method, params , opt ,fn) {
        var req = new ajaxBuilder(url , method , params )
        var self = this
        req.then(function (data) {
            fn.call(self ,data)
        })
    }

    Vue.prototype.ajaxGet = function(url , params , fn) {
        this.ajax(url , 'GET' , params , {} , fn)
    }

    Vue.prototype.ajaxPost = function (url , params , fn) {
        this.ajax(url , 'POST' , params , {} , fn)
    }

    Vue.prototype.ajaxDelete = function (url , params , fn) {
        this.ajax(url , 'DELETE' , params , {} , fn)
    }

    Vue.prototype.setModel = function(ref , model) {
        var deep_clone = {}
        $.extend(true , deep_clone , model)
        this.$refs[ref].set_formModel(deep_clone)
    }

    Vue.prototype.showItem = function(ref , key) {
        this.$refs[ref].show_item(key)
    }

    Vue.prototype.hideItem = function(ref , key) {
        this.$refs[ref].hide_item(key)
    }

    Vue.prototype.setData = function (ref , data) {
        this.$refs[ref].set_value(data)
    }

    Vue.prototype.refresh = function(ref , params) {
        this.$refs[ref].refresh(params)
    }

    /**
     * 加载模型， 初始化vue实例
     * @param vue
     * @param models
     */
    Vue.prototype.initModel=function (models , refOrFn) {
        this.ajaxGet('/admin/model/lookup' , {model:models} ,function (data) {
            if($.isFunction(refOrFn)) {
                refOrFn.call(this , data)
            } else {
                this.setModel(refOrFn , data[models])
            }
        })

    }

    Vue.prototype.initData = function (url , param , refOrFn) {
        this.ajaxGet(url , param ,function(data){
            if($.isFunction(refOrFn)) {
                refOrFn.call(this , data)
            } else {
                this.setData(refOrFn ,data.data )
            }
        })
    }

    Vue.prototype.validate = function (ref) {
        return this.$refs[ref].validate()
    }

    /**
     * 公用信息提示modal
     *
     */
    /*
     * <div class="modal fade" id="myModal" tabindex="-1" role="dialog">
     *
     * <div class="modal-dialog" role="document">
     *
     * <div class="modal-content">
     *
     * <div class="modal-header"> <button type="button" class="close"
     * data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
     * <h4 class="modal-title">Modal title</h4> </div>
     *
     * <div class="modal-body"> ... </div>
     *
     * <div class="modal-footer"> <button type="button" class="btn btn-default"
     * data-dismiss="modal">Close</button> <button type="button" class="btn
     * btn-primary">Save changes</button> </div>
     *
     * </div>
     *
     * </div>
     *
     * </div>
     */

    window.modals = window.modals||{}
    function _modal_structure(config, ok, cancel) {
        // <div id="myModal" class="modal fade" tabindex="-1" role="dialog">
        var modal = document.createElement('DIV');
        modal.id = 'modal-tips-div';
        modal.className = 'modal fade';
        modal.tabindex = -1;
        modal.role = 'dialog';
        modal.setAttribute('aria-labelledby', 'myModalLabel');

        // <div class="modal-dialog" role="document">
        var dialog = document.createElement('DIV');
        dialog.className = 'modal-dialog modal-'+(config.large?'lg':'sm');

        //update for HANZO, 2016/12/28
        if (config.width) { dialog.style.width = config.width+'px'; }

        dialog.role = 'document';
        modal.appendChild(dialog);

        // <div class="modal-content">
        var content = document.createElement('DIV');
        content.className = 'modal-content';
        dialog.appendChild(content);

        // <div class="modal-header">
        var header = document.createElement('DIV');
        header.className = 'modal-header';

        // <button type="button" class="close" data-dismiss="modal"
        // aria-label="Close"><span aria-hidden="true">&times;</span></button>
        var close_icon = document.createElement('BUTTON');
        close_icon.className = 'close';
        close_icon.type = 'button';
        close_icon.setAttribute('data-dismiss', 'modal');
        close_icon.setAttribute('aria-label', 'Close');
        close_icon.innerHTML = '<span aria-hidden="true">&times;</span>';
        header.appendChild(close_icon);

        // //<h4 class="modal-title">Modal title</h4>
        if (config.title) {
            var title = document.createElement('H5');
            title.id = 'myModalLabel';
            title.className = 'modal-title';
            title.innerHTML = /* '<i class="fa fa-fw fa-info-circle"></i>'+ */config.title;
            header.appendChild(title);
        }

        content.appendChild(header);

        // <div class="modal-body">
        var body = document.createElement('DIV');
        body.className = 'modal-body';

        //update for HANZO, 2016/12/28
        if (config.src) {
            body.style.height = config.height+'px';

            var frame = document.createElement('iframe');
            frame.style.margin = 0;
            frame.style.padding = 0;
            frame.style.width = '100%';
            frame.style.height = '100%';
            frame.style.border = 'none';
            frame.src = config.src;
            body.appendChild(frame);
        } else {
            body.innerHTML = config.text||'';
        }

        content.appendChild(body);

        if (ok || cancel || config.has_footer) {
            // <div class="modal-footer">
            var footer = document.createElement('DIV');
            footer.className = 'modal-footer';

            if (cancel) {
                // <button type="button" class="btn btn-default"
                // data-dismiss="modal">Close</button>
                var btn_close = document.createElement('BUTTON');
                btn_close.className = 'btn btn-default btn-sm';
                btn_close.type = 'button';
                btn_close.setAttribute('data-dismiss', 'modal');
                btn_close.innerHTML = config.cancel_label;
                btn_close.onclick = function() {
                    if (config.cancel_call) { config.cancel_call.call(this); }
                };

                footer.appendChild(btn_close);
            }

            if (ok) {
                // <button type="button" class="btn btn-primary">Save</button>
                var btn_ok = document.createElement('BUTTON');
                btn_ok.className = 'btn btn-primary btn-sm';
                btn_ok.type = 'button';
                //btn_ok.setAttribute('data-dismiss', 'modal');
                btn_ok.innerHTML = config.ok_label;
                btn_ok.onclick = function() {
                    $("#modal-tips-div").modal('hide');
                    if (config.callback) { config.callback.call(this); }
                };

                footer.appendChild(btn_ok);
            }

            content.appendChild(footer);
        }

        return modal;
    }

    function _create_modal(config, ok, cancel) {
        _remove_modal();

        var modal = _modal_structure(config, ok, cancel);

        document.body.appendChild(modal);

        $('#modal-tips-div').modal('show');

        $('#modal-tips-div').on("hidden.bs.modal", function() {
            modals.fixwyhtml5();
            _remove_modal();
            $(this).removeData("bs.modal");
        });


        return modal;
    }

    function _remove_modal() {
        $("#modal-tips-div").remove();
        $("div.modal-backdrop").remove();
    }

    /**
     * 目前阶段涉及参数说明 { title: 'modal标题', text: '提示内容', ok_label: '确定按钮的内容',
	 * cancel_label: '取消按钮的内容', callback: '确定按钮的回调函数', cancel_call: '取消按钮的回调函数',
	 * large: 'modal大小模式' }
     */
    var _CONFIG = {
        ok_label: '确定',
        cancel_label: '关闭',
        large: false
    };
    function modal_params(text, callback) {
        return $.extend({}, _CONFIG, (typeof text != 'object')?{ text: text, callback: callback }:text);
    }

    var _TITLE = { info: '提示', correct: '成功', warn: '警告', error: '错误' };
    function _alert(args, type) {
        var cfg = modal_params.apply(this, args);
        cfg.title = cfg.title||_TITLE[type];

        return _create_modal(cfg, false, true);
    }

    /**
     * 用法: 1.modals.info('...'); 2.modals.info({ ... });
     *
     * correct、warn、error可不要。为统一规范尽量选择相应函数
     *
     * @returns {*}
     */
    modals.info = function() {
        return _alert.call(this, arguments, 'info');
    };

    modals.correct = function() {
        return _alert.call(this, arguments, 'correct');
    };

    modals.warn = function() {
        return _alert.call(this, arguments, 'warn');
    };

    modals.error = function() {
        return _alert.call(this, arguments, 'error');
    };

    /**
     * 用法: 1.modals.confirm('...'); 2.modals.confirm({ ... });
     *
     * @returns {*}
     */
    modals.confirm = function() {
        var cfg = modal_params.apply(this, arguments);
        cfg.title = cfg.title||'确认提示';
        cfg.cancel_label = cfg.cancel_label==_CONFIG.cancel_label?'取消':cfg.cancel_label;

        return _create_modal(cfg, true, true);
    };

    modals.popup = function(cfg) {
        return _create_modal(cfg||{}, false, false);
    };


    /****************************************************************************
     * @author bill qq:475572229 window
     ***************************************************************************/
        //{"backdrop":"static"}点击背景不会消失
    var _win_config={winId:'user-win',backdrop:true,keyboard:true,width:900};
    //config={win:'userWin',top:'auto'/20,width:'900px',title:'新增用户'}
    modals.openWin=function(config){
        var winId=config.winId||_win_config.winId;
        this.closeWin(winId);
        /*if($("#"+winId).length>0){
         this.showWin(winId);
         return false;
         }*/
        config=$.extend({},_win_config,config);
        this._create_modal(config);
        if(!config.url&&!config.loadContent){
            this.error('未配置url');
        }
        $("#"+winId).modal({remote:config.url,backdrop:config.backdrop||_win_config.backdrop,keyboard:config.keyboard||_win_config.keyboard});
        if(config.loadContent){
            config.loadContent();
        }
        this.showWin(config.winId||_win_config.winId);
        //attach show event
        $("#"+winId).on('shown.bs.modal',function(){
            /*if(config.top=="auto"){
             var height=$(window).height()>$(this).find(".modal-dialog").eq(0).height()?($(window).height()-$(this).find(".modal-dialog").eq(0).height())/2:0;
             $(this).find(".modal-dialog").eq(0).css("margin-top",height);
             }*/
            $(this).on('loaded.bs.modal', function(event) {
                $(this).find(".modal-title").html(config.title);
            });
            if(config.showFunc)
                config.showFunc.call(this);
        });
        //attach hidden event
        $("#"+winId).on("hidden.bs.modal", function() {
            modals.closeWin(winId);
            if(config.hideFunc)
                config.hideFunc.call(this);
            modals.fixwyhtml5();
            $(this).removeData("bs.modal");

        });
    }
    //add by billjiang fix the wyhtml5 problem: wyhtml has the modal class,make the scrollbar hidden
    modals.fixwyhtml5=function(){
        if($(".wysihtml5-editor")){
            $(document.body).removeClass('modal-open');
            $(document.body).css("padding-right","0px");
        }
    }
    modals._create_modal=function(config){
        var modal = document.createElement('DIV');
        modal.id = config.winId;
        modal.className = 'modal fade';
        modal.tabindex = -1;
        modal.role = 'dialog';
        modal.setAttribute('aria-labelledby', 'myModalLabel');
        modal.setAttribute('aria-hidden',true);

        // <div class="modal-dialog" role="document">
        var dialog = document.createElement('DIV');
        dialog.className = 'modal-dialog';
        dialog.role = 'document';
        if(isNaN(config.width)&&config.width.indexOf("px")>0)
            $(dialog).css('width',config.width);
        //dialog.style='width:'+config.width+';';
        else
            $(dialog).css('width',config.width+"px");
        //dialog.style='width:'+config.width+'px;';
        modal.appendChild(dialog);

        // <div class="modal-content">
        var content = document.createElement('DIV');
        content.className = 'modal-content';
        dialog.appendChild(content);

        if(config._hidden_data) {
            //<input id="_hidden_data" hidden="true" value=""/>
            var _hi=document.getElementById("_icon_hidden_data")
            if(_hi)
                _hi.setAttribute('value' , config._hidden_data)
            else {
                _hi = document.createElement('input');
                _hi.setAttribute('hidden' , true)
                _hi.setAttribute('value' , config._hidden_data)
                _hi.setAttribute('id' , '_icon_hidden_data' )
                document.body.appendChild(_hi);
            }
        }



        document.body.appendChild(modal);
    }

    modals.closeWin=function(winId){
        winId=winId||_win_config.winId;
        var win = document.getElementById(winId);
        if (win) {
            $(win).remove();
            $("div.modal-backdrop").remove();
        }
    }

    modals.hideWin=function(winId){
        winId=winId||_win_config.winId;
        $("#"+winId).modal('hide');
    }

    modals.showWin=function(winId){
        winId=winId||_win_config.winId;
        $("#"+winId).modal('show');
    }

    modals.removeData=function(winId){
        winId=winId||_win_config.winId;
        //$("#myModal").on("hidden.bs.modal", function() {
        $("#"+winId).removeData("bs.modal");
        //});
    }
}(jQuery ,window, document)




