<template>
    <div  :class="wrapClasses" :id="dynName" >
        <div  v-for="item in dict" @click="change_state(item)" :data-val="v"
              :class="item.state?'layui-form-checkbox layui-form-checked':'layui-form-checkbox'"
              lay-skin="primary">
            <span>{{item.label}}</span>
            <i :class="item.state?'layui-icon layui-icon-ok': 'layui-icon'"></i>
        </div>
    </div>
</template>
<script>
    import {baseDict}  from '../baseDict'
    export default {
        mixins: [baseDict],
        name: 'v-checkbox',
        methods : {

        }
    }
</script>
