<template>
    <div class="layui-form">
        <slot></slot>
        <template v-if="form_inline">
            <template v-for="(row , index) in row_items">
                        <div class="layui-form-item">
                            <template v-for="item in row">
                               <div class="layui-inline">
                                   <label  class="layui-form-label">{{item['label']}}</label>
                                   <v-base-form-item :inline="true" :item="item" :ref="item.key" :data="data"></v-base-form-item>
                               </div>
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
                           <div :class="get_class(item)">
                               <label  class="layui-form-label">{{item['label']}}</label>
                               <v-base-form-item :inline="false" :item="item" :ref="item.key" :data="data"></v-base-form-item>
                           </div>
                        </template>
                        </div>
                    </template>
                    <div v-if="submit_url" class="col-sm-12 text-center" style="border-top: solid 1px #d2d6de;padding-top: 8px;">
                        <button class="layui-btn" @click="internal_submit">
                            <li class="fa fa-save">&nbsp;提交</li>
                        </button>
                    </div>
                    <template v-if="$slots.footer">
                         <slot name="footer"></slot>
                    </template>
        </template>
    </div>
</template>

<script>
import {baseForm}  from './baseForm'

export default {
  mixins: [baseForm],
  name: 'v-form',
}
</script>