<template>
    <!-- Main content -->
    <section class="content">
        <div class="row">
            <div :class="rowCount">
                <a href="#" class="btn btn-primary btn-block margin-bottom">Compose</a>
                <div class="box box-solid">
                    <div class="box-header with-border">
                        <h3 class="box-title">Folders</h3>

                        <div class="box-tools">
                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                            </button>
                        </div>
                    </div>
                    <div class="box-body no-padding">
                        <ul class="nav nav-pills nav-stacked">
                            <li class="active"><a :href="inbox_url"><i class="fa fa-inbox"></i> Inbox
                                <span class="label label-primary pull-right">{{lableNumber}}</span></a></li>
                            <li><a :href="send_url"><i class="fa fa-envelope-o"></i> Sent</a></li>
                            <li><a :href="draft_url"><i class="fa fa-file-text-o"></i> Drafts</a></li>
                            <li><a :href="junk_url"><i class="fa fa-filter"></i> Junk <span class="label label-warning pull-right">65</span></a>
                            </li>
                            <li><a :href="tarsh_url"><i class="fa fa-trash-o"></i> Trash</a></li>
                        </ul>
                    </div>
                    <!-- /.box-body -->
                </div>
                <!-- /. box -->

            </div>
        </div>
        <!-- /.row -->
    </section>
    <!-- /.content -->
</template>

<script>
    export default {
        name: "v-folder",
        props:{
            rowCount:    { type: Number,   default:3 },
            lableNumber: { type: Number,   default:10 },
            inbox_url:   { type: String,   default:'#'},
            send_url:    { type: String,   default:'#'},
            draft_url:   { type: String,   default:'#'},
            junk_url:    { type: String,   default:'#'},
            tarsh_url:   { type: String,   default:'#'}

        },
        computed:{
            column(){
                return "col-md-"+this.rowCount
            }
        }
    }
</script>

<style scoped>

</style>