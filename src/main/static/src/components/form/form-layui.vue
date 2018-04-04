<template>
    <form class='layui-form'>
        <template v-for="item in items">
            <template v-if="!item.ignore">
                <div :class="get_class(item)" >
                    <div class="layui-form-item">
                        <label class="layui-form-label">{{item['label']}}</label>
                        <div class="layui-input-block">
                            <input type="text" lay-verify="title" :id="item.key" :value="buildVal(item)"
                                    :placeholder="item.placeholder" class="layui-input">
                        </div>
                    </div>
                </div>
            </template>
        </template>
    </form>
</template>

<script>
import {baseForm}     from './baseForm'
export default {
  mixins: [baseForm],
  name: 'v-form',
}
</script>