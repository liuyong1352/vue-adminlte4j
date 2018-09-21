<template>
    <div class="row table-responsive no-padding">
        <div v-if="$slots.toolbar"class="col-sm-12 pull-right" style="padding-bottom: 5px;">
            <slot name="toolbar"></slot>
        </div>
        <div class="col-sm-12 layui-form">
            <table id="v-table1" class="table table-bordered table-striped table-hover">
                <thead style="background: #78d5d69e;">
                    <tr>
                        <th v-for="col in formItems" v-bind:class="{ hidden: col.hidden }" v-html="col.label"></th>
                        <th v-if="operations || render_operation">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(row , index) in dataItems" @click="row_selected(row , index)" v-bind:class="{info: index === sr_index}">
                        <td  v-for="col in formItems" v-bind:class="{ hidden: col.hidden }" >
                            <v-base-form-item v-if="editable&&(ed_index == index)&&edit_filter(col.key,row)"  inline="true" :ref="col.key"
                                :item="col"  :value="row[col.key]"
                                :dis_label="false"></v-base-form-item>
                            <span v-else v-html="build_val(row , col)"></span>
                        </td>
                        <td v-if="operations || render_operation">
                            <template v-if="operations">
                                <a v-if="show_operation(operation , row , index)" v-for="operation in operations"
                                    :class="get_op_class(operation.class)"
                                    href="javascript:void(0)"
                                    @click="proxy_method(operation , row , index)" style="padding-right: 6px;margin-right:4px" >
                                    <i v-if="operation.icon" :class="operation.icon"></i><span>{{operation['name']}}</span>
                                </a>
                            </template>
                            <template v-if="render_operation">
                                <div v-html="render_operation(row, index)"></div>
                            </template>
                        </td>
                    </tr>
                    <tr v-if="dataItems.length == 0">
                        <td :colspan="100" style="text-align: center;">暂无相关数据</td>
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
                   <li v-if="current_page > 1"><a href="#" @click="change_page(pre_page , $event)">«</a></li>
                   <template v-for="item in page_arr">
                        <li><a href="#" @click="change_page(item ,$event)">{{item}}</a></li>
                   </template>
                   <li v-if="current_page < total_page"><a href="#" @click="change_page(next_page ,$event)" >»</a></li>
                </ul>
            </div>

         </template>
    </div>

</template>
<script>
import BaseFormItem   from '../form/base-form-item.vue'
import {baseValidate}   from '../baseValidate'
import {formModelValue}  from '../base/formModelValue'
export default {
    mixins: [baseValidate , formModelValue],
    name: 'v-table',
    props: {
        data : Object ,
        query: {type : Object ,default: {}},
        operations: Array,
        render_operation: Function ,
        render:Object ,
        editable:{type:Boolean ,default:false} ,
        edit_filter:{type:Function, default:function(){return true}}
    } ,
    data() {
        return {
          formItems: [],
          current_page : 1 ,
          sr_index:-1 ,
          ed_index:-1 ,
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
        }
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
        refresh : function(queryObj) {
            this.current_page=1
            if(queryObj)
                this.query = queryObj._isVue?queryObj.get_value() : queryObj
            this.ed_index = -1
            this.fetchData()
        } ,
        row_selected: function(row,index) {
            this.sr_index=index
            if(this.$listeners['on-row-selected'])
                this.$emit("on-row-selected", index , row)
        } ,
        add_row: function(row) {
            var new_row = row || {}
            for(var index in  this.columns) {
                var col=this.columns[index]
                new_row[col.key]= new_row[col.key] || null
            }

            this.dataItems.push(new_row)
        } ,
        delete_row:function(index) {
            this.dataItems.splice(index,1)
        } ,
        edit_row:function(index) {
            if(this.ed_index == index)
                return
            var row = this.get_row(this.ed_index)
            if(row)
                this.change_row(row)
            this.ed_index = index
        } ,
        get_row:function(index) {
            if(index >= 0 && index < this.dataItems.length)
                return this.dataItems[index]
        } ,
        get_selected_row() {
            return this.get_row(this.sr_index)
        } ,
        get_value:function() {
            var row ;
            if(this.editable && (row = this.get_row(this.ed_index)) )
                this.change_row(row)
            return this.dataItems
        },
        fetchData: function () {
            var params = {}
            var page = this.isPage ? {currentPage:this.current_page}:{}
            $.extend(true , params , this.query, page )
            this.ajaxGet(this.ajax_url , params , function(data){
                this.table_data(data)
            })
        } ,
        set_value(dataItems) {
            this.dataItems = dataItems || []
        } ,
        table_data:function(data) {
            var tableData = data.tableData
            this.set_formItems(tableData.formItems)
            this.set_value(tableData.dataItems)
            this.isPage    = (1 == tableData.pager)
            this.page_size  = tableData.pageSize
            this.totalSize = tableData.totalSize
            this.ed_index = -1
            if(this.isPage)
                this.computer_total_page()
        } ,
        proxy_method: function(operation , param , index) {
            operation.method.call(this , param , index)
        } ,
        show_operation: function(operation , param , index ) {
            if(operation.show)
                return operation.show.call(this , param , index)
            return true
        },
        change_page: function(p , e) {
            this.current_page = p
            this.fetchData()
            e.preventDefault()
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
            if(!row) {
                console.error(" the param of function 'change_row' ,  'row' is undefined ")
                return
            }
            for(var k in this.$refs){
                if(this.$refs[k]&&this.$refs[k][0])
                    row[k] = this.$refs[k][0].get_value()
            }
        }
    },
    components : {
            'v-base-form-item': BaseFormItem,
    }
}
</script>