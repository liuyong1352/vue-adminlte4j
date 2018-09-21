<template>
<dl class="dl-horizontal">
    <div v-if="title" class="detail">
        <h4 v-html="title"></h4>
    </div>
    <template  v-for="item in formItems">
        <dt>{{item.label}}：</dt>
        <dd v-html="build_val(data , item)"></dd>
    </template>
</dl>
</template>

<script>
import {formModelValue}  from '../base/formModelValue'
export default {
    mixins: [formModelValue],
    name: "v-detail",
    props:{
        title:String
    } ,
    data() {
        return {

        }
    } ,
    mounted () {
        this.refresh()
    }
}
</script>

<style scoped>

</style>