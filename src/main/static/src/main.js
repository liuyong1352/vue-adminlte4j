const requireComponent = require.context(
    '.', true, /\.vue$/
    //找到components文件夹下以.vue命名的文件
)
requireComponent.keys().forEach(filePath => {
    var componentConfig = requireComponent(filePath)
    Vue.component(componentConfig.default.name, componentConfig.default || componentConfig)
})

/*import VDashboard    from './components/dashboard.vue'
 Vue.component('v-app'               , VDashboard)*/