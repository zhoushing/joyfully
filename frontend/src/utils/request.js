import axios from 'axios'
import router from "../router";

const request = axios.create({
    /* 自动拼接一个/api */
    baseURL: "/api",
    timeout: 5000
})

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';

    // config.headers['token'] = user.token;  // 设置请求头

    // 取出sessionStorage里面缓存的用户信息
    let userJson = sessionStorage.getItem("user");
    let adminJson = sessionStorage.getItem("admin");

    if (config.url !== '/register' && !userJson && !adminJson) {
        router.push("/login");
    }

    /* 拦截非超级管理员 进入 管理员操作界面 */
    if (!adminJson && config.url === '/admin/find') {
        let adminData = adminJson.split(':')[1].split(',');
        let adminId = Number(adminData[0]);
        request.get("/admin/checkPower",{params:{"id": adminId}}).then(res => {
            if (!res.data) {
                router.push("/userManage");
            }
        });
    }

    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;
        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        return res;
    },
    error => {
        console.log('err' + error) // for debug
        return Promise.reject(error)
    }
)


export default request
