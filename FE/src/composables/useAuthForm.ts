import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { authService } from '@/services/authService'

export function useAuthForm(mode: 'login' | 'register') {
    const router = useRouter()
    const loading = ref(false)
    const error = ref('')
    const form = ref({
        email: '',
        password: '',
        confirmPassword: ''
    })

    const onSubmit = async () => {
        try {
            loading.value = true
            error.value = ''

            if (mode === 'register' && form.value.password !== form.value.confirmPassword) {
                error.value = 'Passwords do not match'
                return
            }

            if (mode === 'login') {
                await authService.login(form.value.email, form.value.password)
                router.push('/')
            } else {
                await authService.register(form.value.email, form.value.password)
                router.push('/login')
            }
        } catch (err: any) {
            error.value = err.message || 'Something went wrong'
        } finally {
            loading.value = false
        }
    }

    return { form, loading, error, onSubmit }
}