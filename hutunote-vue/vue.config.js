/*
 * @Description: 配置文件
 * @Autor: Sophy
 * @Date: 2021-11-26 17:42:10
 * @LastEditors: Sophy
 * @LastEditTime: 2022-04-18 08:56:43
 */
const path = require('path')

function resolve(dir) {
  // 此处使用path.resolve 或path.join 可自行调整
  return path.join(__dirname, dir)
}
module.exports = {
  outputDir: process.env.outputDir,
  // 如果你不需要生产环境的 source map，可以将其设置为 false 以加速生产环境构建。
  productionSourceMap: false,
  publicPath: './',
  devServer: {
    port: 8080, // 端口号
    host: '0.0.0.0',
    https: false, // https:{type:Boolean}
    open: true, // 配置自动启动浏览器
    proxy: {
      '/api': {
        target: 'https://5h88334g81.oicp.vip/hutunote',
        changeOrigin: true,
        pathRewrite: {
          '^/api': '/' // 重写接口
        }
      },
      '/hutunote': {
        target: 'https://5h88334g81.oicp.vip/hutunote',
        changeOrigin: true,
        pathRewrite: {
          '^/hutunote': '/' // 重写接口
        }
      }
    }, // 配置多个代理
    disableHostCheck: true
  },
  lintOnSave: false, // 关闭eslint代码检查
  configureWebpack: {
    externals: {
      // 'AMap': 'AMap' // 高德地图配置
    }
  },
  chainWebpack: config => {
    // 修改文件引入自定义路径
    config.resolve.alias.set('@public', resolve('public'))
    if (process.env.use_analyzer) {
      config
        .plugin('webpack-bundle-analyzer')
        .use(require('webpack-bundle-analyzer').BundleAnalyzerPlugin)
    }
  }
}
