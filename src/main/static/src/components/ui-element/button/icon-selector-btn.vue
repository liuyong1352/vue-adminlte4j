<template>
     <button type="button" @click="popIconWin" class="btn btn-primary" data-btn-type="selectIcon">
        <i class="fa fa-hand-pointer-o">&nbsp;选择图标</i>
     </button>
</template>

<script>
    export default {
        name: 'v-icon-selector',
        props: {
            size: {
                type: String,
                default: 'lg'
            }

        },
        methods : {

            popIconWin : function() {
               var iconTarget = this.$attrs.icon_el
               modals.openWin({
                    winId:"iconWin",
                    title:'图标选择器（双击选择）',
                    _hidden_data: iconTarget,
                    width:'1000px',
                    url:"/admin/config/icon.html?iconName="
               })
            }
        }

    }
</script>