export const base = {
    methods:{
        get_value() {
            return this.$el.getElementsByTagName('input')[0].value
        }
    }
}