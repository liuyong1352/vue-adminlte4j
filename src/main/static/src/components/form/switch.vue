<template>
    <div :class="wrapClasses">
        <div :class="['layui-form-switch' , v == on_v ?'layui-form-onswitch':'']"  @click="switch_state" :data-v="v">
            <em v-html=" v == on_v?dict[0].label : dict[1].label"></em><i></i>
        </div>
    </div>
</template>

<script>
import {baseInput}  from '../baseInput'
export default {
    mixins: [baseInput],
    name: 'v-switch',
    props : {
            items : Array
    } ,
    data() {
        return {
            on_v:1 ,
            dict: []
        }
    } ,
    created() {
        if(this.items){
            this.dict = this.items
        } else {
            this.dict = [{code:1 ,  label:'ON' },
                         {code:0 ,  label:'OFF' }]
        }
        this.on_v=this.dict[0].code
    } ,
    methods: {
        switch_state() {
            var idx = (this.on_v == this.v)?1:0
            var v = this.dict[idx].code
            this.set_value(v)
        }
    }
}
</script>
