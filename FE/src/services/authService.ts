import axios from 'axios'

const API = 'http://localhost:8081/api/auth' // auth-service port

export const authService = {
    async login(email: string, password: string) {
        const res = await axios.post(`${API}/login`, { email, password })
        localStorage.setItem('token', res.data.token)
        return res.data
    },

    async register(email: string, password: string) {
        const res = await axios.post(`${API}/register`, { email, password })
        return res.data
    }
}
