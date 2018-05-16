<template>
    <div class="row table-responsive no-padding">
        <div v-if="$slots.toolbar"class="col-sm-12 pull-right" style="padding-bottom: 5px;">
            <slot name="toolbar"></slot>
        </div>
        <div class="col-sm-12 layui-form">
            <table id="v-table1" class="table table-bordered table-striped table-hover">
                <thead style="background: #78d5d69e;">
                    <tr>
                        <th v-for="col in columns" v-bind:class="{ hidden: col.hidden }" v-html="col.label"></th>
                        <th v-if="operations">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(row , index) in dataItems" @click="row_selected(row , index)" v-bind:class="{info: index === sr_index}">
                        <td  v-for="col in columns" v-bind:class="{ hidden: col.hidden }" >
                            <v-base-form-item v-if="editable&&(sr_index == index)"  inline="true" :ref="col.key"
                                :item="col"  :value="row[col.key]"
                                :dis_label="false"></v-base-form-item>
                            <span v-else v-html="build_val(row , col)"></span>
                        </td>
                        <td v-if="operations">
                            <a :class="get_op_class(operation.class)"  v-for="operation in operations"
                                href="javascript:void(0)"
                                @click="proxy_method(operation , row , index)" style="padding-right: 6px;margin-right:4px" >
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
import {baseValidate}   from '../baseValidate'
export default {
    mixins: [baseValidate],
    name: 'v-table',
    props: {
        data : Object ,
        query: {type : Object ,
            default: {}
        },
        ajax_url: String ,
        operations: Array,
        render:Object ,
        editable:{type:Boolean ,default:false}
    } ,
    data() {
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
    created() {
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
    updated() {
        this.$nextTick(function () {
            layui.use('form' , function(){
                var form = layui.form
                form.render()
            })
        })

    },

    methods : {
        getParam: function() {
            var s = ''
            for(var key in this.query)
                if(this.query[key] && this.query[key].length>0)
                    s += ('&' + key + '=' + this.query[key])
            return s
        } ,
        refresh : function(queryObj) {
            if(queryObj)
                this.query = queryObj._isVue?queryObj.get_value() : queryObj
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

            $.get(url).then(function (data){
                self.columns   = data.tableData.formItems
                self.dataItems = data.tableData.dataItems
                self.isPage    = data.tableData.isPage
                self.pageSize  = data.tableData.pageSize
                self.totalSize = data.tableData.totalSize
                if(self.isPage)
                    self.computer_total_page()
            })
        } ,
        proxy_method: function(operation , param , index) {
            operation.method.call(this , param , index)
        } ,
        change_page: function(p) {
            this.current_page = p
            this.fetchData()
        } ,
        computer_total_page : function () {
            this.total_page = Math.floor((this.totalSize / this.page_size) + ((this.totalSize % this.page_size) == 0? 0 : 1))
        } ,
        should_page : function() {
            return this.isPage && this.totalSize > 0
        },
        get_op_class: function (cls) {
            cls = cls || 'btn-default'
            return 'btn btn-xs ' + cls

        } ,
        change_row:function(row){
            for(var k in row) {
                row[k] = this.$refs[k][0].get_value()
            }
        },
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