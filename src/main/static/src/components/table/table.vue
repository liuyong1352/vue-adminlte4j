<template>
    <div class="row table-responsive no-padding">
        <div v-if="$slots.toolbar"class="col-sm-12 pull-right" style="padding-bottom: 5px;">
            <slot name="toolbar"></slot>
        </div>
        <div class="col-sm-12 layui-form">
            <table id="v-table1" class="table table-bordered table-striped table-hover">
                <thead style="background: #78d5d69e;">
                    <tr>
                        <th v-for="col in columns"  v-html="col.label"></th>
                        <th v-if="operations">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(item , index) in dataItems" @click="row_selected(item , index)" v-bind:class="{info: index === sr_index}">
                        <td v-for="_col in columns" >
                            <span v-html="build_val(item, _col)"></span>
                        </td>
                        <td v-if="operations">
                            <a :class="get_op_class(operation.class)"  v-for="(operation,  index)  in operations"
                                href="javascript:void(0)"
                                @click="proxy_method(operation , item)" style="padding-right: 6px;margin-right:4px" >
                                <i v-if="operation.icon" :class="operation.icon"></i><span>{{operation['name']}}</span>
                            </a>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
         <template v-if="should_page()">

                    <div class="col-sm-5">
                        <span class="pull-left">共{{totalSize}}条</span>
                    </div>
                    <div class="col-sm-7">
                        <ul class="pagination pagination-sm no-margin pull-right">
                           <li v-if="current_page > 1"><a href="#" @click="change_page(pre_page)">«</a></li>
                           <template v-for="item in page_arr">
                                <li><a href="#" @click="change_page(item)">{{item}}</a></li>
                           </template>
                           <li v-if="current_page < total_page"><a href="#" @click="change_page(next_page)" >»</a></li>
                        </ul>
                    </div>

            </template>
    </div>

</template>
<script>
import BaseFormItem   from '../form/base-form-item.vue'
export default {
  name: 'dataTable',
  props: {
    data : Object ,
    query: {type : Object ,
        default: {}
    },
    ajax_url: String ,
    send_req : {type : Number , default : 0 } ,
    operations: Array,
    render:Object
  } ,
  data: function () {
    return {
      columns: [],
      current_page : 1 ,
      sr_index:-1 ,
      totalSize : 0 ,
      page_size : 10 ,
      isPage : true ,
      total_page : 0 ,
      dataItems: []
    }
  },
  watch: {
    ajax_url : function(val , oldVal) {
        this.fetchData()
    } ,
    send_req : function(val , oldVal) {
        this.fetchData()
    }
  } ,
  created : function() {
    if(this.ajax_url != undefined && this.ajax_url != "") {
        this.fetchData()
    } else if( this.data != undefined )  {
        this.columns    = this.data.columns
        this.dataItems  = this.data.dataItems
    }
    this.computer_total_page()
  } ,
  computed: {
    page_arr : function() {
        var p_arr = []
        var c = this.current_page

        while( c * this.page_size <= this.totalSize ) {
            p_arr.push(c++)
            if(p_arr.length >= 5)
                break
        }
        return p_arr
    } ,
    pre_page : function() {
        return this.current_page - 1
    } ,
    next_page : function() {
        return this.current_page + 1
    },

  } ,

  methods : {
    getParam: function() {
        var s = ''
        for(var key in this.query)
            if(this.query[key] && this.query[key].length>0)
                s += ('&' + key + '=' + this.query[key])
        return s
    } ,
    refresh : function(queryObj) {
        if(queryObj&&queryObj._isVue)
            this.query = queryObj.get_value()
        this.fetchData()
    } ,
    row_selected: function(row,index) {
        console.log(row)
        this.sr_index=index
    } ,
    fetchData: function () {
        var self = this

        var url = this.ajax_url
        var i = this.ajax_url.indexOf('?')
        var c = 'currentPage=' + this.current_page
        if(this.ajax_url.indexOf('?') < 0 ) {
            url += '?'
        } else {
            url += '&'
        }
        url += c + this.getParam()

        axios.get(url).then(function (response){
            self.columns   = response.data.tableData.columns
            self.dataItems = response.data.tableData.dataItems
            self.totalSize = response.data.tableData.totalSize
            self.computer_total_page()
        })
    } ,
    proxy_method: function(operation , param) {
        operation.method(param , this)
    } ,
    change_page: function(p) {
        this.current_page = p
        this.fetchData()
    } ,
    computer_total_page : function () {
        this.total_page = this.totalSize / this.page_size + ((this.totalSize % this.page_size) == 0? 0 : 1)
    } ,
    should_page : function() {
        return this.isPage && this.totalSize > 0
    },
    get_op_class: function (cls) {
        cls = cls || 'btn-default'
        return 'btn btn-xs ' + cls

    } ,
    build_val(item , col) {
        if(this.render && this.render[col.key]) {
            return this.render[col.key](item , this)
        }
        if(col.type >= 3 && col.type <= 6  ){
            var r = []
            var isString = (typeof  item[col.key]  == 'string')
            var codes = []
            if(isString){
                codes =item[col.key].split(',')
            } else {
                codes.push(item[col.key])
            }
            for(var i in codes) {
                var c=codes[i]
                for(var j in col.ext.dict){
                    if(col.ext.dict[j].code==c)
                        r.push(col.ext.dict[j].label)
                }
            }
            return r.join(" ")
        } else if(col.type== 10) {
            return '<i class="' + item[col.key] + '"></i>'
        }
        return item[col.key]
    }
  },
  components : {
          'v-base-form-item': BaseFormItem,
  }
}
</script>