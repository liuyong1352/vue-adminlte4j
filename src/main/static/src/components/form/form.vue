<template>
    <form class="form-horizontal">
        <slot></slot>
        <template v-for="item in items">
            <div v-if="item" class="col-md-12">
                    <div class="form-group">
                      <label  class="col-sm-2 control-label">{{item['key']}}</label>
                      <div class="col-sm-10">
                          <input type="text" class="form-control" placeholder="Enter ..." >
                      </div>
                    </div>
                </div>
        </template>
    </form>
</template>

<script>

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
  mounted : function() {

    if(this.ajax_url) {
        var self=this
        axios.get(this.ajax_url).then(function (response) {
            var formJson = response.data.FormModel
            self.items = formJson
        })
    }

  }
}
</script>