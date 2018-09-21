import {baseInput}  from './baseInput'
export const baseDict = {
    extends: baseInput ,
    props : {
        items : Array
    } ,
    data() {
       return {
           dict : []
       }
    } ,
    created() {
        if(this.items) {
            for(var i in this.items) {
                var e = {}
                e.code=this.items[i].code
                e.label=this.items[i].label
                e.state=0
                this.dict[i]=e
            }
            this.set_state(this.v)
        }
    },
    methods : {
        get_value() {
            var v = []
            for(var i in this.dict) {
                var d = this.dict[i]
                if(d.state)
                    v.push(d.code)
            }
            return v.join(',')
        } ,
        reset_state() {
            for(var i in this.dict) {
                var d = this.dict[i]
                d.state = 0
            }
        } ,
        reset() {
            this.reset_state()
            this.set_value('')
        } ,
        set_value(v) {
            this.set_state(v)
            this.set_v(v)
        } ,
        set_state(v) {
            var arr = []
            if(!$.isEmpty(v) ){
                var t = typeof v
                if(t == 'string')
                    arr = v.split(',')
                else if (t == 'number')
                    arr = [v]
                else if ($.isArray(v))
                    arr = v
            }

            for(var j in this.dict) {
                var d = this.dict[j]
                d.state =0
                for(var i in arr) {
                    var e = arr[i]
                    if(e == d.code) {
                        d.state = 1
                        break
                    }
                }
            }
        },
        change_state(item) {
            item.state ^= 1
            this.set_v(this.get_value())
        }
    }
}