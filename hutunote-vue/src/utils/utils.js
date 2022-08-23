export default {
  treeFindPath(tree, func, path = []) {
    // 获取当前路由的树的全路径
    if (!tree) return []
    for (const data of tree) {
      // 这里按照你的需求来存放最后返回的内容吧
      path.push(data)
      if (func(data)) return path
      if (data.children) {
        const findChildren = this.treeFindPath(data.children, func, path)
        if (findChildren.length) return findChildren
      }
      path.pop()
    }
    return []
  },
  filterTreeNullChildren(data) {
    // 过滤树 children 为空的字段
    Array.isArray(data) && data.forEach(res => {
      if (!res.children || (res.children && res.children.length === 0)) {
        delete res.children
      } else {
        this.filterTreeNullChildren(res.children)
      }
    })
    return data
  },
  generateUUID() {
    /* 生成唯一标识UUID */
    let d = new Date().getTime()
    if (window.performance && typeof window.performance.now === 'function') {
      d += performance.now() // use high-precision timer if available
    }
    const uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
      const r = (d + Math.random() * 16) % 16 | 0
      d = Math.floor(d / 16)
      return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16)
    })
    return uuid.replaceAll('-', '')
  },
  recursiveTreeData(data, cb) { // data数据，cb回调
    // 递归树操作-forEach
    Array.isArray(data) && data.forEach(res => {
      cb && cb(res)
      if (res.children && res.children.length > 0) {
        this.recursiveTreeData(res.children, cb)
      }
    })
  },
  findTreeData(data, cb) { // data数据，cb回调
    // 递归树操作-find
    Array.isArray(data) && data.find(res => {
      cb && cb(res)
      if (res.children && res.children.length > 0) {
        this.recursiveTreeData(res.children, cb)
      }
    })
  },
  randomFn(min, max) { // 随机生成min-max的整数
    return Math.floor(Math.random() * (max - min)) + min
  },
  jointUrlQuery(query) { // 拼接URL参数
    let str = ''
    Object.keys(query).forEach((p, i) => {
      str += `${p}=${query[p]}`
      if (i !== Object.keys(query).length - 1) {
        str += '&'
      }
    })
    return str
  },
  translateTime: function (p, sym, time) { // 时间戳，分隔符,time(不传则不显示，1 显示时分， 2 显示时分秒)
    // 将时间戳转为特定符号分隔的日期
    const symbol = sym || '-'
    if (p) {
      const val = new Date(p)
      const year = val.getFullYear() // getFullYear getYear
      let month = val.getMonth() + 1
      let result = ''
      if (month < 10) {
        month = '0' + month
      }
      let day = val.getDate()
      if (day < 10) {
        day = '0' + day
      }
      let hour = val.getHours()
      if (hour < 10) {
        hour = '0' + hour
      }
      let minu = val.getMinutes()
      if (minu < 10) {
        minu = '0' + minu
      }
      let sec = val.getSeconds()
      if (sec < 10) {
        sec = '0' + sec
      }
      if (time === 1) {
        result = year + symbol + month + symbol + day + ' ' + hour + ':' + minu // 2019-02-01 12:30
      } else if (time === 2) {
        result = year + symbol + month + symbol + day + ' ' + hour + ':' + minu + ':' + sec // 2019-02-01 12:30:20
      } else {
        result = year + symbol + month + symbol + day // 2019-02-01
      }
      return result
    } else {
      return ''
    }
  },
  validateForms: function (formRefs) { // 多表单校验
    let objectList = []
    let results = formRefs.map(formRef =>
      new Promise((resolve, reject) => {
        formRef.validate((valid, object) => {
          if (valid) {
            resolve()
          } else {
            objectList.push(object)
            reject()
          }
        })
      })
    )
    return Promise.all(results).catch(() => {
      return Promise.reject(objectList)
    })
  },
  /*
 *数组对象去重
 */
  removeDup: function (arr, key) { // arr 需要去重的数组对象，score根据这个属性进行去重removeDup(arr, "score");
    if (!Array.isArray(arr)) {
      return arr;
    }
    if (arr.length == 0) {
      return [];
    }
    let obj = {};
    let uniqueArr = arr.reduce(function (total, item) {
      obj[item[key]] ? '' : (obj[item[key]] = true && total.push(item));
      return total;
    }, []);
    return uniqueArr;
  },
  /**
   * @description: 深拷贝
   * @Date: 2022-08-22 14:48:21
   * @author: liaojb
   */
  deepClone: (source) => {
    if (!source && typeof source !== 'object') {
      throw new Error('error arguments', 'deepClone')
    }
    const targetObj = source.constructor === Array ? [] : {}
    Object.keys(source).forEach(keys => {
      if (source[keys] && typeof source[keys] === 'object') {
        targetObj[keys] = deepClone(source[keys])
      } else {
        targetObj[keys] = source[keys]
      }
    })
    return targetObj
  }
}
