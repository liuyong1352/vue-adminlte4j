<template>
    <div :class="wrapClasses">
        <input :type="type" class="layui-input"
            :name="dynName"
            :lay-verify="verify"
            :placeholder="placeholder"
            :value="v"
            :disabled="disabled"
            v-on:input="handleInput">
    </div>
</template>

<script>
import {baseInput}  from '../baseInput'
export default {
    mixins: [baseInput],
    name: 'v-input',
    props: {
        type: {
                type: String,
                default: 'text'
        }
    }
}
</script>