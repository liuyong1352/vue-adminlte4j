export const baseDict = {
    props : {
        items : []
    } ,
    methods : {
        get_value:function() {
            var arr = []
            for(var i in this.$refs.input){
                if(this.$refs.input[i].checked)
                    arr.push(this.$refs.input[i].value)
            }
            return arr.join(',')
        }
    }
}