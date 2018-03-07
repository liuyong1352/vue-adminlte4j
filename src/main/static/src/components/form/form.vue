<template>
    <form class="form-horizontal">
        <slot></slot>
        <template v-for="item in items">
            <div v-if="item['type'] === 0 " class="col-md-12">
                <div class="form-group">
                  <label  class="col-sm-2 control-label">{{item['label']}}</label>
                  <div class="col-sm-10">
                      <input type="text" class="form-control"  :value="item.defVal" :placeholder="item.placeholder">
                  </div>
                </div>
            </div>
            <div v-if="item['type'] === 10 " class="col-md-12">
                <div class="form-group">
                    <label class="col-sm-2 control-label">{{item.label}}:</label>
                    <div class="col-sm-10">
                        <div class="row">
                            <div class="col-sm-8">
                                <i data-bv-icon-for="icon" :id="item['key'] + '_i'" :class="'form-control-feedback ' + item.defValue" style="right: 15px;"></i>
                                <input type="text" :id="item.key" name="item['key']" placeholder="图标" class="form-control" :value="setValue(item)">
                            </div>
                            <div class="col-sm-2">
                                <v-icon-selector :icon_el="'#' + item.key + '_i #' + item.key " ></v-icon-selector>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </template>

    </form>
</template>

<script>
import IconSelector  from '../ui-element/button/icon-selector-btn.vue'
export default {
  name: 'v-form',
  props: {
    data: String ,
    ajax_url : String
  } ,
  data : function() {
    return {
      items:[]
    }
  } ,
  methods: {
    refresh: function(data) {
        if(this.ajax_url) {
            var self=this
            axios.get(this.ajax_url , {params:data}).then(function (response) {
                var formJson = response.data.FormModel
                self.items = formJson
            })
        }
    } ,
    setValue: function(item) {
        return item.defValue
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