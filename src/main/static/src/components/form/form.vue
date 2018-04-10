<template>
    <form :class="form_inline?'form-inline layui-form':'layui-form'">
        <slot></slot>
        <template v-for="row in row_items">
            <div class="layui-col-space10 layui-form-item">
            <template v-for="item in row">
               <div :class="get_class(item)">
                   <label  class="layui-form-label">{{item['label']}}</label>
                   <v-base-form-item :inline="row.length != 1" :item="item" :data="data"></v-base-form-item>
               </div>
            </template>
            </div>
        </template>
        <div v-if="submit_url" class="col-sm-12 text-center" style="border-top: solid 1px #d2d6de;padding-top: 10px;">
            <button class="layui-btn" @click="internal_submit">
                <li class="fa fa-save">&nbsp;提交</li>
            </button>
        </div>
    </form>
</template>

<script>
import {baseForm}     from './baseForm'

export default {
  mixins: [baseForm],
  name: 'v-form',
}
</script>