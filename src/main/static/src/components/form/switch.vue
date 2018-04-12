<template>
    <div :class="wrapClasses">
        <input type="checkbox"
            :name="name"
            value="true"
            :checked="isOpen"
            :lay-text="text"
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
        text : {type:String ,default:'开|关'} ,
        isOpen:Boolean
    },
    methods:{
        buildText:function() {
            if(this.items && this.items.length >= 2 )  {
                return this.items[0].label + '|' + this.items[1].label
            }
            return this.text
        } ,
        get_value:function() {
            return $('input[name=' + this.name + ']:checked').length //0 or 1
        }
    }
}
</script>
