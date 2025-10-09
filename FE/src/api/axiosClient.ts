// src/api/axiosClient.ts
import axios from 'axios'

const axiosClient = axios.create({
    baseURL: 'http://localhost:8080/api/v1', // thay bằng gateway hoặc service thật
    headers: {
        'Content-Type': 'application/json'
    }
})

// Thêm interceptor để tự động gắn token (nếu có)
axiosClient.interceptors.request.use((config) => {
    const token = localStorage.getItem('access_token')
    if (token) {
        config.headers.Authorization = `Bearer ${token}`
    }
    return config
})

export default axiosClient
