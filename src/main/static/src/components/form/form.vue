<template>
    <form class="form-horizontal">
        <slot></slot>
        <template v-for="item in items">
            <template v-if="!item.ignore">
                <div :class="item.hidden?'col-md-12 hidden':'col-md-12'">
                    <div v-if="item['type'] === 0 " class="form-group">
                        <label  class="col-sm-2 control-label">{{item['label']}}</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" :id="item.key" :value="buildVal(item)" :placeholder="item.placeholder">
                        </div>
                    </div>
                    <div v-if="item['type'] === 10 " class="form-group">
                        <label class="col-sm-2 control-label">{{item.label}}:</label>
                        <div class="col-sm-9">
                            <div class="row">
                                <div class="col-sm-8">
                                    <i data-bv-icon-for="icon" :id="item['key'] + '_i'" :class="'form-control-feedback ' + buildVal(item)" style="right: 15px;"></i>
                                    <input type="text" :id="item.key" name="item['key']"  class="form-control" :value="buildVal(item)" :placeholder="item.placeholder">
                                </div>
                                <div class="col-sm-2">
                                    <v-icon-selector :icon_el="'#' + item.key + '_i #' + item.key " ></v-icon-selector>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </template>
        </template>
    </form>
</template>

<script>
import IconSelector  from '../ui-element/button/icon-selector-btn.vue'
export default {
  name: 'v-form',
  props: {
    ajax_url : String
  } ,
  data : function() {
    return {
      items:[] ,
      data : {}
    }
  } ,
  methods: {
    refresh: function(data) {
        if(this.ajax_url) {
            var self=this
            axios.get(this.ajax_url , {params:data}).then(function (response) {
                var formJson = response.data.FormModel.formItems
                self.items = formJson
                self.data = response.data.data
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

    formData: function() {
        var jsonData = {}
        this.items.forEach(function(item){
            jsonData[item.key] = $("#" + item.key).val()
        })
        return jsonData
    } ,
    submit: function(url ,callback) {
        if(!this.validate())
            return

        axios.post(url,this.formData()).then(function(response){
            callback(response)
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
    this.refresh()
  } ,
  components: {
        'v-icon-selector': IconSelector
  }
}
</script>