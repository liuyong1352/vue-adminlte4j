<template>
<div>
    <div class="row">
        <div class="col-sm-12">
            <table id="v-table1" class="table table-bordered table-striped v-table">
                <thead>
                    <tr>
                        <th v-for="col in columns">
                            {{col['label']}}
                        </th>
                        <th v-if="operations">
                            操作
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="item in dataItems">
                        <td v-for="_col in columns">{{item[_col['key']]}}</td>
                        <td v-if="operations">
                            <a  :class="getOpertionScheme(operation.scheme)"  v-for="operation in operations" href="javascript:void(0)" @click="proxy_method(operation , item)" style="padding-right: 6px;" ><i
                                    :class="getOpertionIcon(operation.icon)"></i><span>{{operation['name']}}</span></a>

                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    <template v-if="should_page()">

        <div class="row">
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
        </div>
    </template>

</div>
</template>
<script>

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


  } ,
  data: function () {
    return {
      columns: [],
      current_page : 1 ,
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
  mounted : function() {
    console.log(" table mounted!!")
  } ,
  methods : {
    getParam: function() {
        var s = ''
        for(var key in this.query)
            s += ('&' + key + '=' + this.query[key])
        return s
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
      getOpertionScheme: function (data) {
          return{
              'btn btn-info btn-sm waves-effect waves-light m-b-5':data === 'blue',
              'btn btn-danger btn-sm waves-effect waves-light m-b-5': data === 'red',
              'btn btn-warning btn-sm waves-effect waves-light m-b-5':data=== 'yellow',
              'btn btn-success btn-sm waves-effect waves-light m-b-5':data=== 'green',

          }
      },
      getOpertionIcon: function (data) {
          return{
              'fa fa-edit': data === 'edit',
              'fa fa-trash-o': data === 'trash',
              'fa fa-rocket': data=== 'rocket',

          }
      }
  }
}
</script>