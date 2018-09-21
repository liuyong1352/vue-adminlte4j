<template>
    <div class="layui-form" :lay-filter="dynName">
        <slot></slot>
        <template v-if="form_inline">
            <template v-for="(row , index) in row_items">
                        <div class="layui-form-item">
                            <template v-for="item in row">
                               <v-base-form-item :inline="true" :item="item" :ref="item.key" :value="buildVal(item)"></v-base-form-item>
                            </template>
                            <div v-if="index== (row_items.length-1)" class="layui-inline">
                                <slot name="inline"></slot>
                            </div>
                        </div>
            </template>
        </template>
        <template v-else>
            <template v-for="(row , index) in row_items">
                <div class="layui-col-space10 layui-form-item">
                    <template v-for="item in row">
                       <v-base-form-item :inline="false" :item="item" :ref="item.key" :value="buildVal(item)"></v-base-form-item>
                    </template>
                </div>
            </template>
        </template>
        <div v-if="submit_url" class="layui-col-space10 layui-form-item text-center"
            style="border-top: solid 1px #d2d6de;padding-top: 8px; margin-bottom:2px">
            <button class="layui-btn" @click="internal_submit">
                <li class="fa fa-save">&nbsp;提交</li>
            </button>
            <button v-if="has_reset =='true'" class="layui-btn layui-btn-primary" @click="reset">
                           <li class="fa fa-reply">&nbsp;重置</li>
            </button>
        </div>
        <template v-if="$slots.footer">
            <slot name="footer"></slot>
        </template>
    </div>
</template>

<script>
import BaseFormItem   from './base-form-item.vue'
import {baseForm}  from './baseForm'
export default {
    mixins: [baseForm ],
    name: 'v-form',
    components : {
        'v-base-form-item': BaseFormItem,
    }
}
</script>