const requireComponent = require.context(
    '.', true, /\.vue$/
    //找到components文件夹下以.vue命名的文件
)
requireComponent.keys().forEach(filePath => {
    var componentConfig = requireComponent(filePath)
    Vue.component(componentConfig.default.name, componentConfig.default || componentConfig)
})

import Dialog from './components/ui-element/dialog.vue'

const install = function(Vue, opts = {}) {
    var vDialog = Vue.component(Dialog.name , Dialog)
    var dlg = new vDialog().$mount()
    Vue.prototype.$message= function(msg) {
        document.getElementById('app').appendChild(dlg.$el);
        dlg.show()
    }
}

install(Vue)







