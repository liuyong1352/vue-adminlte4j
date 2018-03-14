<template>
    <div :id="id"></div>
</template>
<script>
export default {
  name: 'v-tree-view',
  props: {
    id:{type:String , default:'tree'} ,
    ajax_url:{type:String} ,
    expand_icon : {type:String ,default :"glyphicon glyphicon-stop"} ,
    collapse_icon : {type:String ,default :"glyphicon glyphicon-unchecked"}
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
        var tree = $("#" + this.id).data('treeview')
        var expanded= (tree && tree.getExpanded()) || []

        $("#" + this.id).treeview({
            data : _data,
            showBorder : true,
            expandIcon : self.expand_icon,
            collapseIcon : self.collapse_icon,
            levels : 1,
            onNodeSelected : function(event, data) {
                if(self.$listeners['on-node-selected'])
                    self.$emit("on-node-selected", data)
            }

        })

        for(var i = 0 ; i<expanded.length ;i++){
            var eid = expanded[i].nodeId
            $("#" + this.id).data('treeview').expandNode(eid)
            $("#" + this.id).data('treeview').revealNode(eid)
        }
                    //selectNodeId=selectNodeId||0;
                    //$("#tree").data('treeview').selectNode(selectNodeId)
                    //$("#tree").data('treeview').expandNode(selectNodeId)
                    //$("#tree").data('treeview').revealNode(selectNodeId)
                    //$('#' + this.id).on('nodeSelected', callback);
    }
  } ,
  mounted : function() {
    this.refresh()
  }
}
</script>
