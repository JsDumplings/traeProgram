import axios from 'axios';

const service = axios.create({
  baseURL: 'http://localhost:9999', // 替换为实际的本地后端端口号
  timeout: 5000
});

// 请求拦截器
service.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  error => {
    console.log(error);
    return Promise.reject(error);
  }
);

// 响应拦截器
service.interceptors.response.use(
  response => response.data,
  error => {
    console.log('err' + error);
    return Promise.reject(error);
  }
);

export default service;