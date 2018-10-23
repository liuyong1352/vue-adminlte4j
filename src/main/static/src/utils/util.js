/**
 * 将数组装换成属性结构， 数据放在data域中
 * @param elements     入参数组 id - pid 来表示父子关系
 * @param treeInstance 组件实例
 * @param callback     fn(e) ,e为返回的数组e
 * @returns {Array}
 */
export function buildTree(elements ,treeInstance , callback) {
    var tree = [] , callback= callback||function(){}
    var map = {}
    var arr = []
    for(var i in elements ) {
        var d = elements[i]
        var e = {id:d.id ,
            pid  : d.pid ,
            data : d ,
            expand: 0 ,
            show : 0 ,
            tree : treeInstance ,
            children:[]
        }
        arr[i] = e
        map[e.id] = e
    }

    for(var i in arr) {
        var e = arr[i]
        var pid = e.pid
        if(pid  &&  map[pid]) {
            var parent = map[pid]
            parent.children.push(e)
            e.parent = parent
        } else {
            e.show = 1
            tree.push(e)
        }
        callback(e)
    }
    return tree
}

/**
 * 查找祖先节点
 * @param e
 * @returns {*}
 */
export function findAncestorId(e) {
    if(e.parent) {
        return findAncestorId(e.parent)
    }
    return e
}

/**
 * 遍历树
 * @param tree
 * @param callback fn(e ,index)  , 如果有返回值 停止遍历返回此结果 如果没有返回继续遍历
 */
export function traverseTree(tree , callback) {
    if(tree && tree.length)
        for(var i=0 ; i < tree.length ;i++ ){
            var ret = callback(tree[i])
            if(ret)
                return ret
            traverseTree(tree[i].children , callback)
        }

}
