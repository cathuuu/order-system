// src/api/authApi.ts
import axiosClient from './axiosClient'

export const login = (data: { username: string; password: string }) => {
    return axiosClient.post('/auth/login', data)
}
