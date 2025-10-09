<script setup lang="ts">
import AuthLayout from '@/components/auth/AuthLayout.vue'
import AuthForm from '@/components/auth/AuthForm.vue'
</script>

<template>
  <AuthLayout title="Register">
    <!-- 🧾 Form đăng ký -->
    <AuthForm mode="register" />

    <!-- 🔗 Footer -->
    <template #footer>
      <p class="text-sm text-gray-600 text-center mt-4">
        Already have an account?
        <RouterLink
            to="/login"
            class="text-blue-500 font-medium hover:underline transition-colors"
        >
          Login
        </RouterLink>
      </p>
    </template>
  </AuthLayout>
</template>

<style scoped>
/* Giữ style nhẹ, tập trung vào layout chính */
:global(html, body, #app) {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
}

/* Đảm bảo layout auth tràn toàn màn hình */
:deep(.auth-layout) {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #a8edea 0%, #fed6e3 100%);
  font-family: 'Inter', sans-serif;
  padding: 2rem;
  box-sizing: border-box;
}

/* Footer link tinh tế hơn */
:deep(.auth-footer) {
  margin-top: 1rem;
  text-align: center;
  font-size: 0.9rem;
  color: #6b7280;
}

:deep(.auth-footer a) {
  color: #3b82f6;
  font-weight: 500;
  transition: color 0.2s;
}

:deep(.auth-footer a:hover) {
  color: #2563eb;
  text-decoration: underline;
}
</style>
