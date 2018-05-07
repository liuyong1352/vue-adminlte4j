<template>
<footer class="main-footer">
        <!-- To the right -->
        <div class="pull-right hidden-xs" v-text="version"></div>
        <!-- Default to the left -->
        <strong v-html="copyright"></strong> All rights reserved.
</footer>
</template>
<script>
export default {
    name: 'v-footer',
    props: {
        version:{type:String , default:''} ,
        copyright:{type:String,default:''}
    }
}
</script>