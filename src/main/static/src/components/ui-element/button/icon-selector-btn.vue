<template>
    <button v-if="type == 'btn'" type="button" @click="popIconWin" class="btn btn-primary" data-btn-type="selectIcon">
            <i class="fa fa-hand-pointer-o">&nbsp;选择图标</i>
    </button>
    <div v-else-if="type == 'input'" class="layui-inline">
        <i data-bv-icon-for="icon" :id="dynName + '_i'" :class="'form-control-feedback ' + value" @click="popIconWin"></i>
        <input type="text" :id="dynName"
            :name="dynName" class="layui-input"
            :value="value"
            ref="input"
            placeholder="点击选图标"
            :lay-verify="verify"
            @click="popIconWin">
    </div>
</template>
<script>
import {baseInput}  from '../../baseInput'
export default {
    mixins: [baseInput],
    name: 'v-icon-selector',
    props: {
        type: {type:String, default:'input'}
    },
    methods : {
        popIconWin : function() {
           var icon_el=this.$attrs.icon_el
           if(this.type=='input')
                icon_el = '#' + this.dynName + ' #' + this.dynName + '_i'
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