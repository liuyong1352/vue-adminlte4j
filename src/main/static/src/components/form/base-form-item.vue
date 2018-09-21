<template>
    <div :class="get_class(item)">
        <label v-if="dis_label"  class="layui-form-label">{{item['label']}}</label>
        <template v-if="item.type === 1 " >
            <v-input :type="text"
                :verify="get_verify(item)"
                :value="value"
                :placeholder="item.placeholder" :wrap_class="get_wrapper_class()"></v-input>
        </template>
        <template v-else-if="item.type === 2 " >
            <v-textarea  :verify="get_verify(item)"
                :value="value"
                :placeholder="item.placeholder" :wrap_class="get_wrapper_class()"></v-textarea>
        </template>
        <template v-else-if="item['type'] === 3 " >
            <v-radio  :ref="item.key" :wrap_class="get_wrapper_class()"
                   :items="item.ext.dict" :value="value">
            </v-radio>
        </template>
        <template v-else-if="item['type'] === 4 " >
            <v-checkbox  :wrap_class="get_wrapper_class()"
                    :items="item.ext.dict" :value="value"></v-checkbox>
        </template>
        <template v-else-if="item['type'] === 5 " >
            <v-switch  :items="item.ext.dict" :wrap_class="get_wrapper_class()"
                    :value="value" ></v-switch>
        </template>
        <template v-else-if="item['type'] === 6 " >
            <v-select  :items="item.ext.dict" :wrap_class="get_wrapper_class()"
                :value="value" :placeholder="item.placeholder"></v-select>
        </template>
        <template v-else-if="item['type'] === 10 " >
            <v-icon-selector
                :validate="get_verify(item)"
                :value="value" type="input"></v-icon-selector>
        </template>
        <template v-else-if="item['type'] === 12 " >
            <v-date  :type="get_date_type_val(item,'type')" :wrap_class="get_wrapper_class()"
                 :verify="get_verify(item)"
                 :format="get_ext_val(item,'format')"
                 :range="get_ext_val(item,'range')"
                 :value="value"
                 :placeholder="item.placeholder" ></v-date>
        </template>
    </div>
</template>

<script>

import VInput       from '../form/input.vue'
import VDate        from '../date/date.vue'
import VCheckBox    from './check-box.vue'
import VSwitch      from './switch.vue'
import VRadio       from './radio.vue'
import IconSelector  from '../ui-element/button/icon-selector-btn.vue'

export default {
    name: 'v-base-form-item',
    props: {
        item:{type:Object} ,
        inline:{type:Boolean} ,
        value:{type:Object} ,
        dis_label: {type :Boolean ,default:true}
    } ,
    methods :{
        get_class : function(item){
            var cls = item.hidden?'hidden':''
            if(this.inline)
                return cls + ' layui-inline'
            else
                return cls + ' layui-col-lg'+item.span
        } ,
        set_value(val){
            this.$children[0].set_value(val)
        } ,
        get_value(){
            return this.$children[0].get_value()
        } ,
        reset() {
            this.$children[0].reset()
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
        get_wrapper_class:function () {
            return this.inline?'layui-input-inline':'layui-input-block'
            //return 'layui-input-block'
        }
    },
    components: {
          'v-input': VInput,
          'v-icon-selector': IconSelector,
          'v-checkbox': VCheckBox,
          'v-switch': VSwitch,
          'v-date': VDate,
          'v-radio' : VRadio
    }
}
</script>