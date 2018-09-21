export const formModelValue = {
    props:{
        ajax_url:String ,
        model:String
    } ,
    data() {
        return {
            data : {},
            formItems:[]
        }
    },
    methods : {
        refresh: function(params) {
            if(this.ajax_url) {
                this.ajaxGet(this.ajax_url , params , function (data) {
                    this.set_formModel(data.FormModel)
                    this.set_value(data.data)
                })
            }
        } ,
        set_value(jsonData) {
            this.data = jsonData ||{}
        } ,
        set_formModel(formModel) {
            if(formModel)
                this.set_formItems(formModel.formItems)
        } ,
        hide_item(key) {
            for(var i in this.formItems) {
                if(this.formItems[i].key == key)
                    this.formItems[i].hidden=true
            }
        },
        show_item(key) {
            for(var i in this.formItems) {
                if(this.formItems[i].key == key)
                    this.formItems[i].hidden=false
            }
        } ,
        set_formItems(formItems) {
            if(formItems)
                this.formItems=formItems
        } ,
        build_val(data , col) {
            if(this.render && this.render[col.key]) {
                return this.render[col.key](data , this)
            }
            if(col.type >= 3 && col.type <= 6  ){
                var r = []
                var isString = (typeof  data[col.key]  == 'string')
                var codes = []
                if(isString){
                    codes =data[col.key].split(',')
                } else {
                    codes.push(data[col.key])
                }
                for(var i in codes) {
                    var c=codes[i]
                    if((!col.ext || !col.ext.dict) && col.type == 5) {
                        return c ?'是':'否'
                    }
                    for(var j in col.ext.dict){
                        if(col.ext.dict[j].code==c)
                            r.push(col.ext.dict[j].label)
                    }
                }
                return r.join(" ")
            } else if(col.type== 10) {
                return '<i class="' + data[col.key] + '"></i>'
            } else if(col.type==12) {
                var d = data[col.key]
                if($.isNumeric(d))
                    return $.toDateString(d)
                return d
            }
            return data[col.key]
        }

    }
}