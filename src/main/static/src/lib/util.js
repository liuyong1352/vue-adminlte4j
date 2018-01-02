export default {

    is_empty : function (s) {
        if(s === undefined || s === null || s === "")
            return true
        return false
    } ,
    get_val : function (s , dft) {
        return this.is_empty(s)? dft : s
    } ,

    get : function (name) {
        if (typeof (Storage) !== 'undefined') {
            return localStorage.getItem(name)
        } else {
            window.alert('Please use a modern browser to properly view this template!')
        }
    } ,

    store : function(name , val) {
        if (typeof (Storage) !== 'undefined') {
            localStorage.setItem(name, val)
        } else {
            window.alert('Please use a modern browser to properly view this template!')
        }
    } ,

    removeItem : function (name) {
        if (typeof (Storage) !== 'undefined') {
            return localStorage.removeItem(name)
        } else {
            window.alert('Please use a modern browser to properly view this template!')
        }
    }

}






