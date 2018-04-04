<template>
    <button v-if="type == 'btn'" type="button" @click="popIconWin" class="btn btn-primary" data-btn-type="selectIcon">
            <i class="fa fa-hand-pointer-o">&nbsp;选择图标</i>
    </button>

    <div v-else-if="type == 'input'" class="layui-inline">
        <i data-bv-icon-for="icon" :id="id + '_i'" :class="'form-control-feedback ' + value" @click="popIconWin"></i>
        <input type="text" :id="id" name="name"  class="layui-input" :value="value" placeholder="点击选图标" @click="popIconWin">
    </div>

</template>

<script>
    export default {
        name: 'v-icon-selector',
        props: {
            'id':{type:String},
            'value':{type:String},
            'name': {type:String},
            type: {type:String, default:'btn'}
        },
        methods : {

            popIconWin : function() {
               var icon_el=this.$attrs.icon_el
               if(this.type=='input')
                    icon_el = '#' + this.id + ' #' + this.id + '_i'
               if(!icon_el)
                    icon_el='#icon_i #icon'
               modals.openWin({
                    winId:"iconWin",
                    title:'图标选择器（双击选择）',
                    _hidden_data: icon_el,
                    width:'1000px',
                    url:"/admin/config/icon_selector.html"
               })
            }
        }

    }
</script>