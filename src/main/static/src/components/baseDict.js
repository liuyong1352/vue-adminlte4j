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
        } ,
        isChecked : function(item) {
            if(this.value != undefined)  {
                if(typeof this.value != 'string')
                    return this.value == item.code
                var arr = this.value.split(',')
                for(var i in arr ){
                    if(arr[i] == item.code)
                        return true
                }
            }
            return item.checked
        }
    }
}