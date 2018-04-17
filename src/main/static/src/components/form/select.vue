<template>
    <div :class="wrapClasses">
        <select :name="dynName" lay-verify="" lay-search>
             <option value="" selected v-text="this.placeholder"></option>
             <template v-for="item in items">
                <option v-if="isChecked(item)" :value="item.code" v-text="item.label" selected></option>
                <option v-else :value="item.code" v-text="item.label"></option>
             </template>

        </select>
    </div>
</template>

<script>
import {baseInput}  from '../baseInput'
import {baseDict}  from '../baseDict'
export default {
    mixins: [baseInput ,baseDict],
    name: 'v-select' ,
    props:{
        placeholder: {type:String ,default:'Please Select '},
    } ,
    methods:{
        get_value:function() {
            return $(this.$el).find('select').val()
        }
    }
}
</script>
