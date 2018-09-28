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
            tree : treeInstance
        }
        arr[i] = e
        map[e.id] = e
    }

    for(var i in arr) {
        var e = arr[i]
        var pid = e.pid
        if(pid  &&  map[pid]) {
            var parent = map[pid]
            if(!parent.children)
                parent.children = []
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
