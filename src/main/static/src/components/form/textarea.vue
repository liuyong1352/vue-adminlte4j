<template>
    <div :class="wrapClasses">
        <textarea class="layui-textarea"
            :name="dynName"
            :lay-verify="verify"
            :placeholder="placeholder"
            :value="v"
            v-on:input="handleInput">
        </textarea>
    </div>
</template>

<script>
import {baseInput}  from '../baseInput'
export default {
    mixins: [baseInput],
    name: 'v-textarea',
    methods: {
    }
}
</script>