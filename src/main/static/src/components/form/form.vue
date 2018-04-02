<template>
    <form :class="form_inline?'form-inline layui-form':'form-horizontal layui-form'">
        <slot></slot>
        <template v-for="item in items">
            <template v-if="!item.ignore">
                <div :class="get_class(item)" >
                    <div class="form-group">
                        <label  class="col-sm-3 control-label">{{item['label']}}</label>
                        <div class="col-sm-7">
                        <template v-if="item['type'] === 0 " >
                            <input type="text" class="form-control"
                                :id="item.key" :value="buildVal(item)"
                                :placeholder="item.placeholder">
                        </template>
                        <template v-else-if="item['type'] === 12 " >
                            <v-date  :id="item.key" :type="get_date_type_val(item,'type')"
                                 class="form-control"
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
                            <div class="row">
                                <div class="col-sm-8">
                                    <i data-bv-icon-for="icon" :id="item['key'] + '_i'" :class="'form-control-feedback ' + buildVal(item)" style="right: 15px;"></i>
                                    <input type="text" :id="item.key" name="item['key']"  class="form-control" :value="buildVal(item)" :placeholder="item.placeholder">
                                </div>
                                <div class="col-sm-2">
                                    <v-icon-selector :icon_el="'#' + item.key + '_i #' + item.key " ></v-icon-selector>
                                </div>
                            </div>
                        </template>
                        </div>
                    </div>
                </div>
            </template>
        </template>
        <div v-if="submit_url" class="col-sm-12 text-center" style="border-top: solid 1px #d2d6de;padding: 10px;">
            <button type="button" class="btn btn-primary" @click="internal_submit">
                <li class="fa fa-save">&nbsp;保存</li>
            </button>
        </div>
    </form>
</template>

<script>
import VInput       from '../form/input.vue'
import VDate        from '../date/date.vue'
import VCheckBox    from './check-box.vue'
import VSwitch    from './switch.vue'
import IconSelector  from '../ui-element/button/icon-selector-btn.vue'
export default {
  name: 'v-form',
  props: {
    ajax_url : String,
    submit_url:{type :String }
  } ,
  data : function() {
    return {
      items:[] ,
      data : {},
      form_inline: false
    }
  } ,
  computed : {

  } ,
  methods: {
    refresh: function(data) {
        if(this.ajax_url) {
            var self=this
            axios.get(this.ajax_url , {params:data}).then(function (response) {
                var formJson = response.data.FormModel.formItems
                self.items = formJson
                self.data = response.data.data||{}
                self.form_inline=response.data.FormModel.inline
            })
        }
    } ,
    buildVal: function(item) {
        var val = this.data[item.key]
        if(0 == val) {
            return val
        }
        return val || item.defVal
    } ,
    get_ext_val:function(item , key, defVal){
        if(item.ext)
            return (item.ext)[key]
        return defVal
    } ,
    get_date_type_val:function(item , key) {
       if(item.ext) {
            var t=(item.ext)[key]
            if(t==1)
                return 'date'
            if(t==2)
                return 'time'
            if(t==3)
                return 'year'
            if(t==4)
                return 'month'
       }
       return 'datetime'
    },
    get_class : function(item){
        if(item.hidden)
            return 'hidden col-md-12'
        else
            return item.span?('col-md-'+item.span):''
    } ,
    formData: function() {
        var jsonData = {}
        for(var i in this.items){
            var item = this.items[i]
            if(item.type == 4 || item.type == 5) {
                jsonData[item.key]=this.$refs[item.key][0].get_values()
            } else {
               jsonData[item.key]=$("#" + item.key).val()
            }
        }
        return jsonData
    } ,
    get_values:function() {
        return this.formData()
    } ,
    submit: function(url ,callback) {
        if(!this.validate())
            return

        axios.post(url,this.formData()).then(function(response){
            callback(response)
        })
    } ,
    internal_submit: function(){
        this.submit(this.submit_url , function(response){
            $.alert(response.data)
        })
    } ,
    validate: function() {
        var result = true
        for(var i=0 ; i<this.items.length;i++){
            if(this.items[i].validate)
                result = this.validate_item(this.items[i]) && result
        }
        return result
    } ,
    validate_item:function(item) {
        return $.validate(item)
    }
  } ,
  mounted : function() {
    //layui.use(['form'] , function(){})
    this.refresh()
  } ,
  components: {
        'v-icon-selector': IconSelector,
        'v-input': VInput,
        'v-checkbox': VCheckBox,
        'v-switch': VSwitch,
        'v-date': VDate
  }
}
</script>