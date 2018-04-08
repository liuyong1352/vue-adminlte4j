<template>
    <form :class="form_inline?'form-inline layui-form':'layui-form'">
        <slot></slot>
        <template v-for="item in items">
            <template v-if="!item.ignore">
                <div :class="get_class(item)" >
                     <div class="layui-form-item">
                        <label  class="layui-form-label">{{item['label']}}</label>
                        <div :class="get_wrapper_class(item)">
                        <template v-if="item['type'] === 0 " >
                            <input type="text" class="layui-input"
                                :id="item.key" :value="buildVal(item)"
                                :placeholder="item.placeholder">
                        </template>
                        <template v-else-if="item['type'] === 12 " >
                            <v-date  :id="item.key" :type="get_date_type_val(item,'type')"
                                 class="layui-input"
                                 :format="get_ext_val(item,'format')"
                                 :value="buildVal(item)"
                                 :placeholder="item.placeholder" ></v-date>
                        </template>
                        <template v-else-if="item['type'] === 4 " >
                            <v-checkbox :name="item.key" :ref="item.key"
                                    :items="item.ext.dict" :checkedValues="buildVal(item)"></v-checkbox>
                        </template>
                        <template v-else-if="item['type'] === 5 " >
                            <v-switch :name="item.key" :ref="item.key"
                                    :isOpen="buildVal(item) === 1"></v-switch>
                        </template>
                        <template v-else-if="item['type'] === 10 " >
                            <v-icon-selector :id="item.key"
                                :name="item['key']"
                                :value="buildVal(item)" type="input"></v-icon-selector>
                        </template>
                        </div>
                    </div>
                </div>
            </template>
        </template>
        <div v-if="submit_url" class="col-sm-12 text-center" style="border-top: solid 1px #d2d6de;padding-top: 10px;">
            <button type="button" class="btn btn-primary" @click="internal_submit">
                <li class="fa fa-save">&nbsp;保存</li>
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