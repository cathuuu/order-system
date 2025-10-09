import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'


// Import các trang
import Login from '@/views/Login.vue'
import Register from '@/views/Register.vue'
// import Dashboard from '@/views/Dashboard.vue'

const routes: RouteRecordRaw[] = [
    {
        path: '/',
        redirect: '/login',
    },
    {
        path: '/login',
        name: 'Login',
        component: Login,
    },
    {
        path: '/register',
        name: 'Register',
        component: Register,
    },
    // {
    //     path: '/dashboard',
    //     name: 'Dashboard',
    //     component: Dashboard,
    //     meta: { requiresAuth: true }, // Có thể dùng middleware check login
    // },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

// Middleware kiểm tra đăng nhập
router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('access_token')

    if (to.meta.requiresAuth && !token) {
        next('/login')
    } else {
        next()
    }
})

export default router
