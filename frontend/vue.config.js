// 跨域配置
module.exports = {
    devServer: {    // 记住，别写错了devServer
        // todo 此处地址为自定义，后期调整
        port: 8080, // 设置本地默认端口  选填
        proxy: {    // 设置代理，必须填
            '/api': {   // 设置拦截器  拦截器格式   斜杠+拦截器名字，名字可以自己定
                // todo 此处地址为自定义，后期调整
                target: 'http://localhost:9090',    // 代理的目标地址
                changeOrigin: true, // 是否设置同源，输入是的
                pathRewrite: {  // 路径重写
                    '/api': ''  // 选择忽略拦截器里面的单词
                }
            }
        }
    }
}