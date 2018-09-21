<template>
    <div :class="wrapClasses" :data-val="v">
        <div v-for="item in dict" :class="item.state?'layui-form-radio layui-form-radioed': 'layui-form-radio'"
            @click="change_state(item)">
            <i class="layui-anim layui-icon"></i>
            <div>{{item.label}}</div>
        </div>
    </div>
</template>

<script>
import {baseDict}  from '../baseDict'
export default {
    mixins: [baseDict],
    name: 'v-radio' ,
    methods: {
        change_state(item) {
            this.reset_state()
            item.state = 1
            this.set_v(item.code)
        }
    }
}
</script>
