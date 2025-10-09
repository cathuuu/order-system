<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useToast } from 'primevue/usetoast'

// PrimeVue Components
import InputText from 'primevue/inputtext'
import Password from 'primevue/password'
import Button from 'primevue/button'
import Message from 'primevue/message'

// API
import { login } from '@/api/authApi.ts'

// Validation
import { z } from 'zod'

const router = useRouter()
const toast = useToast()

// 🎯 Schema validate
const schema = z.object({
  username: z.string().min(3, 'Username phải có ít nhất 3 ký tự'),
  password: z.string().min(6, 'Mật khẩu phải có ít nhất 6 ký tự'),
})

const form = ref({
  username: '',
  password: ''
})
const errors = ref<{ username?: string; password?: string }>({})

const onFormSubmit = async () => {
  const result = schema.safeParse(form.value)

  if (!result.success) {
    const fieldErrors = result.error.flatten().fieldErrors
    errors.value = {
      username: fieldErrors.username?.[0],
      password: fieldErrors.password?.[0]
    }
    return
  }

  errors.value = {}

  try {
    const res = await login(form.value)
    localStorage.setItem('access_token', res.data.accessToken)

    toast.add({
      severity: 'success',
      summary: 'Đăng nhập thành công',
      detail: `Xin chào ${form.value.username}!`,
      life: 2000
    })

    router.push('/dashboard')
  } catch (err: any) {
    toast.add({
      severity: 'error',
      summary: 'Đăng nhập thất bại',
      detail: err.response?.data?.message || 'Sai tài khoản hoặc mật khẩu',
      life: 3000
    })
  }
}
</script>

<template>
  <div class="login-page">
    <form @submit.prevent="onFormSubmit" class="login-form">
      <h2 class="login-title">Đăng nhập</h2>

      <!-- Username -->
      <div class="input-group">
        <InputText v-model="form.username" placeholder="Tên đăng nhập" fluid />
        <Message v-if="errors.username" severity="error" size="small" variant="simple">
          {{ errors.username }}
        </Message>
      </div>

      <!-- Password -->
      <div class="input-group">
        <Password v-model="form.password" placeholder="Mật khẩu" :feedback="false" toggleMask fluid />
        <Message v-if="errors.password" severity="error" size="small" variant="simple">
          {{ errors.password }}
        </Message>
      </div>

      <Button type="submit" label="Đăng nhập" class="btn-submit" />
      <div>
        Don't have a account ?
        <RouterLink to="/register" class="text-blue-500 hover:underline">
             Sign up
        </RouterLink>
      </div>
    </form>
  </div>
</template>

<style scoped>
/* Reset toàn bộ margin, padding và kích thước của html, body, #app */
:global(html, body, #app) {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
  min-height: 100vh;
  overflow: hidden;
  box-sizing: border-box;
}

/* Trang đăng nhập full màn hình */
.login-page {
  position: fixed; /* giúp phủ toàn bộ viewport */
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;

  display: flex;
  justify-content: center;
  align-items: center;

  background: linear-gradient(135deg, #9089ac 0%, #69ccd5 100%);
  font-family: 'Inter', sans-serif;
}

/* Form đăng nhập */
.login-form {
  background-color: white;
  padding: 2rem;
  border-radius: 1rem;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.08);
  width: 100%;
  max-width: 400px;
  display: flex;
  flex-direction: column;
  gap: 1.25rem;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.login-form:hover {
  transform: translateY(-3px);
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
}

/* Tiêu đề */
.login-title {
  font-size: 1.6rem;
  font-weight: 700;
  color: #333;
  text-align: center;
  margin-bottom: 0.5rem;
}

/* Nhóm input */
.input-group {
  display: flex;
  flex-direction: column;
  gap: 0.3rem;
}

/* ✅ Ghi đè icon toggle password */
:deep(.p-password-toggle-mask-icon) {
  right: 16px !important; /* kéo icon sang phải */
  top: 50% !important; /* canh giữa theo chiều dọc */
  transform: translateY(-50%); /* giúp icon ở đúng giữa */
  color: #9ca3af !important; /* đổi màu nếu muốn */
}

/* Input & Password */
:deep(.p-inputtext),
:deep(.p-password-input) {
  width: 100%;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  padding: 0.5rem 0.75rem;
  font-size: 0.95rem;
  color: #111827;
  transition: all 0.2s ease-in-out;
}

:deep(.p-inputtext:focus),
:deep(.p-password-input:focus) {
  outline: none;
  border-color: #ec4899;
  box-shadow: 0 0 0 2px rgba(236, 72, 153, 0.25);
}

/* Nút submit */
.btn-submit {
  background: linear-gradient(to right, #ec4899, #8b5cf6);
  color: white;
  font-weight: 500;
  border: none;
  border-radius: 8px;
  padding: 0.6rem 1rem;
  font-size: 1rem;
  cursor: pointer;
  transition: all 0.2s ease-in-out;
}

.btn-submit:hover {
  filter: brightness(1.05);
  transform: translateY(-2px);
}

/* Thông báo lỗi */
:deep(.p-message) {
  font-size: 0.85rem;
  color: #ef4444;
  margin-top: 0.25rem;
}
</style>
