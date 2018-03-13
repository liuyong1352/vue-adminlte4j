<template>
    <div class="col-lg-3 col-xs-6">
        <!-- small box -->
        <div :class="getColor">
            <div class="inner">
                <h3>{{header}}</h3>
                <p>{{footer}}</p>
            </div>
            <div class="icon">
                <i class="ion ion-bag"></i>
            </div>
            <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
        </div>
    </div>



</template>

<script>
    export default {
        name: "v-tags",
        props:{
            header: {type:String,default:"header"},
            footer: {type:String,default:"footer"},
            tag:    {type:String,default:"person"},
            color:  {type:String,default:"aqua"}
        },
        methods: {

        },
        computed:{
            getTagName:function() {
                return{
                    'ion ion-bag': this.tag === 'bag',
                    'ion ion-stats-bars': this.tag === 'stats',
                    'ion ion-pie-graph': this.tag === 'graph',
                    'ion ion-person-add': this.tag === 'person'
                }

            },
            getColor:function() {
                return{
                    'small-box bg-aqua': this.color === 'aqua',
                    'small-box bg-green': this.color === 'green',
                    'small-box bg-yellow': this.color === 'yellow',
                    'small-box bg-red': this.color === 'red'
                }

            },

        }
    }
</script>

<style scoped>

</style>