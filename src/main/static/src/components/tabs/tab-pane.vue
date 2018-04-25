<template>
    <div class="tab-pane" :id="tab_id">
        <slot></slot>
    </div>
</template>
<script>
export default {
    props:{
        label : String ,
        active: {type:Boolean, default:false}
    } ,
    computed: {
        tab_id(){
            return "tab_" + unique_id()
        }
    },
    mounted: function () {
        this.$parent.addPane(this)
    }
}
</script>