<template>
    <div :id="id"></div>
</template>
<script>
export default {
  name: 'v-tree-view',
  props: {
    id:{type:String , default:'tree'} ,
    ajax_url:{type:String} ,
    events : {type:Object}
  } ,
  methods: {

    refresh : function(data) {
        var self = this
        if(data) {
            refresh_data(response.data.treeData)
            return
        }
        axios.get(this.ajax_url).then(function(response){
            self.refresh_data(response.data.treeData)
        })
    },
    refresh_data: function(_data) {
        var self = this
        $("#" + this.id).treeview({
            data : _data,
            showBorder : true,
            expandIcon : "glyphicon glyphicon-stop",
            collapseIcon : "glyphicon glyphicon-unchecked",
            levels : 1,
            onNodeSelected : function(event, data) {
                //self.events.onNodeSelected()
                self.$emit("on-node-selected", data)
            }

        })

         //if(response.data.treeData.length==0)
                                   // return;
                    //默认选中第一个节点
                    //selectNodeId=selectNodeId||0;
                    //$("#tree").data('treeview').selectNode(selectNodeId)
                    //$("#tree").data('treeview').expandNode(selectNodeId)
                    //$("#tree").data('treeview').revealNode(selectNodeId)
    } ,
    onNodeSelected : function(callback) {
       if(callback)
            $('#' + this.id).on('nodeSelected', callback);
    }

  } ,
  mounted : function() {
    this.refresh()
  }
}
</script>
