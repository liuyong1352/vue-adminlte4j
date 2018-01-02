<template>
    <textarea
      class="from-control"
    :rows="rowCount"
    :placeholder="placeholder"
    :disabled="isDisabled"></textarea>
</template>

<script>
    export default {
        name: "v-textarea",
        props:{
            placeholder:{
                type:String,
                default:'2222SADASD'
            },
            rowCount:{
                type:Number,
                default:3
            },
            isDisabled:{
                type:Boolean,
                default:false
            }
        },
        created(){

        },
        mounted(){
            console.log("textarea mounted")
        }
    }
</script>

<style scoped>

</style>