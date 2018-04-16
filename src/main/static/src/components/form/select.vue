<template>
    <div :class="wrapClasses">
        <select :name="dynName" lay-verify="" lay-search>
             <option value="" selected v-text="this.placeholder?this.placeholder:'请选择'"></option>
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
    methods:{
        get_value:function() {
            return $(this.$el).find('select').val()
        }
    }
}
</script>
