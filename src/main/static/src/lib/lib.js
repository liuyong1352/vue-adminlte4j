/*!
 @Title: lib.js
 @Description：前端框架
 @Site: www.xxx.com
 @Author: andre
 @License：MIT
 */
;!function(win){
    "use strict";
    var doc = document
        //获取lib所在目录
        ,getPath = function(){
            var jsPath = doc.currentScript ? doc.currentScript.src : function(){
                    var js = doc.scripts
                        ,last = js.length - 1
                        ,src;
                    for(var i = last; i > 0; i--){
                        if(js[i].readyState === 'interactive'){
                            src = js[i].src;
                            break;
                        }
                    }
                    return src || js[last].src;
                }();
            return jsPath.substring(0, jsPath.lastIndexOf('/') + 1);
        }()
    win.libpath = getPath

}(window);


LazyLoad = function(global) {

    var isIE = !1
    var hash = {}
    var doc  = global.document
    var head = doc.head || doc.getElementsByTagName('head')[0]
    var noop = function() {}

    function createEl(type, attrs) {
        var el = doc.createElement(type), attr
        for (attr in attrs) {
            el.setAttribute(attr, attrs[attr])
        }
        return el
    }
    function done(list, obj) {
        hash[obj.url] = true
        list.shift()
        if (!list.length) {
            obj.callback.call(obj.scope)
        }
    }
    function load(type, urls, config) {
        var charset = config.charset
        var obj = {
            scope: config.scope || global,
            callback: config.callback || noop
        }
        var list = [].concat(urls)
        for (var i = 0, len = urls.length; i < len; i++) {
            var el, url = urls[i]

            // 已经加载的不再加载
            if (hash[url]) {
                throw new Error('warning: ' + url + ' has loaded!')
            }

            if (type === 'js') {
                el = createEl('script', {
                    src: url,
                    async: 'async'
                })

            } else {
                el = createEl('link', {
                    href: url,
                    rel: 'stylesheet',
                    type: 'text/css'
                })
            }

            if (charset) {
                el.setAttribute('charset', charset)
            }

            (function(url) {
                if (isIE) {
                    el.onreadystatechange = function() {
                        var readyState = this.readyState
                        if(readyState === 'loaded' || readyState === 'complete') {
                            obj.url = url
                            this.onreadystatechange = null
                            done(list, obj)
                        }
                    }

                } else {
                    if (type == 'js') {
                        el.onload = el.onerror = function() {
                            obj.url = url
                            done(list, obj)
                        }
                    } else {
                        setTimeout(function() {
                            obj.url = url
                            done(list, obj)
                        }, 100)
                    }
                }
            })(url);

            if (type === 'js') {
                head.insertBefore(el, head.firstChild)
            } else {
                head.appendChild(el)
            }
        }

    }

    function _dep_js(libs  ,callback  , index) {
        var _libs = libs[index]
        var type = typeof  _libs
        var mlibs = []
        if(type == 'string') {
            mlibs.push(_libs)
        } else {
            mlibs = _libs
        }
        load('js' , mlibs , {
            callback: function () {
                if(index == libs.length - 1) {
                    callback()
                } else {
                    _dep_js(libs, callback, index + 1)
                }
            }
        })

    }

    return {
        js: function(urls, callback, scope) {
            load('js', urls, callback, scope)
        },
        css: function(urls, callback, scope) {
            load('css', urls, callback, scope)
        } ,
        dep_js : function (urls, callback, scope) {
            _dep_js(urls , callback , 0)
        }
    }
}(this);

app = function (scope) {

    function tag_is_exist(tagName) {
        return document.getElementsByTagName(tagName).length > 0 ;
    }

    function init(config) {

        var load_css = function (css) { document.write('<link href="'  + css + '" rel="stylesheet">') }
        var body_el = document.getElementsByTagName('body')
        body_el[0].setAttribute('style' , 'display:none')
        body_el[0].setAttribute('class' , 'hold-transition skin-blue sidebar-mini')
        var el = document.getElementsByTagName('v-app')
        el[0].setAttribute("id","app")
        var arr_js = ['/lib/jquery/dist/jquery.min.js' ,
            '/lib/vue/dist/vue.min.js' ,
            '/lib/axios/dist/axios.min.js'

        ]


        var js = [
            '/lib/vue-adminlte/dist/js/vue-adminlte.min.js',
            '/lib/bootstrap/dist/js/bootstrap.min.js' ,
            '/lib/admin-lte/dist/js/adminlte.js',
            '/lib/bootstrap-treeview/dist/bootstrap-treeview.min.js' ,
            '/lib/layui-src/dist/layui.js' ,
            '/lib/base.js'
        ]

        if(tag_is_exist('v-select2')) {
            load_css('/lib/select2/dist/css/select2.min.css')
            arr_js.push('/lib/select2/dist/js/select2.full.min.js')
        }

        var css = [
            '/lib/bootstrap/dist/css/bootstrap' ,
            '/lib/font-awesome/css/font-awesome' ,
            '/lib/admin-lte/dist/css/AdminLTE' ,
            '/lib/admin-lte/dist/css/skins/_all-skins'
        ];

        for(var i = 0; i < css.length; i++) {
            load_css(css[i] + ".min.css")
        }
        load_css('/lib/layui-src/dist/css/layui.css')
        load_css('/lib/vue-adminlte/dist/css/base.css')
        for(var i = 0; i < js.length; i++) {
            arr_js.push(js[i])
        }


        function new_vue(data , config) {
            var watch = config.watch || {}
            var methods = config.methods || function(){}
            var mounted = config.mounted || function(){}
            return new Vue({
                el: '#app',
                data: function(){
                    return data;
                } ,
                watch: watch ,
                methods: methods ,
                mounted: function () {
                    if($('body').data('lte.layout')){
                        $('body').data('lte.layout').fix()
                        $('body').data('lte.layout').fixSidebar()
                    }
                    body_el[0].setAttribute('style' , 'display:block')
                    mounted.apply(this)
                }
            })
        }

        LazyLoad.dep_js(arr_js ,  function () {
            axios.defaults.headers.common['X-Requested-With']='XMLHttpRequest'
            axios.interceptors.request.use(function (config) {
                // Do something before request is sent
                return config;
            }, function (error) {
                // Do something with request error
                //return Promise.reject(error);
                return
            });

            // Add a response interceptor
            axios.interceptors.response.use(function (response) {
                if(response.data.is_login == false)
                    window.location.href = response.data.login_url
                return response
            }, function (error) {
                if(error.response.status == 401) //401 未授权
                    window.location.href = (error.response.headers.location || error.response.headers.Location)
                // Do something with response error Promise.reject(error);
                return
            });

            var base_config = {
                is_syn : false ,
                api: '/admin/app_info/get_all' ,
                data: {} ,
                callback: function () {}
            }
            $.extend(base_config , config)
            var _data = {}
            if($.isFunction(config.data)) {
                _data = config.data()
            } else {
                _data =  config.data || {}
            }
            var data = {data :{}}
            $.extend(data , _data )
            new_vue(data , config)
        })
    }

    return {
        main: function (config) {
            init(config)
        }
    }
}(this) ;

