<template>
    <div :class="wrapClasses">
        <input type="checkbox"
            :name="name"
            :value="cvalue"
            ref="input"
            :checked="is_open"
            :lay-text="ctext"
            lay-skin="switch">
    </div>
</template>

<script>
import {baseInput}  from '../baseInput'
import {baseDict}  from '../baseDict'
export default {
    mixins: [baseInput ,baseDict],
    name: 'v-switch',
    props: {
        text : {type:String ,default:'开|关'}
    },
    methods:{
        get_value:function() {
            if(this.$refs.input.checked)
                return this.cvalue.split('|')[0]
            else
                return this.cvalue.split('|')[1]
        }
    } ,
    computed: {
        ctext(){
            if(this.items && this.items.length >= 2 )
                return this.items[0].label + '|' + this.items[1].label
            return this.text
        },
        cvalue() {
            if(this.items && this.items.length >= 2 )
                return this.items[0].code + '|' + this.items[1].code
            return 'true|false'
        } ,
        is_open() {
            return this.value == this.cvalue.split('|')[0]
        }
    }

}
</script>
