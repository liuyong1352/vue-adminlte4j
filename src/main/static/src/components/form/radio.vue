<template>
    <div :class="wrapClasses">
        <input type="radio" :lay-filter="dynName" v-for="item in items"
            :name="dynName"
            ref="input"
            :value="item.code"
            :title="item.label"
            :checked="isChecked(item)">
    </div>
</template>

<script>
import {baseInput}  from '../baseInput'
import {baseDict}  from '../baseDict'
export default {
    mixins: [baseInput ,baseDict],
    name: 'v-radio'
}
</script>
