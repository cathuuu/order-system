<script setup lang="ts">
import { Message } from 'primevue'
import InputField from './InputField.vue'
import SubmitButton from './SubmitButton.vue'
import { useAuthForm } from '../../composables/useAuthForm.ts'

const props = defineProps<{ mode: 'login' | 'register' }>()

const { form, loading, error, onSubmit } = useAuthForm(props.mode)
</script>

<template>
  <form @submit.prevent="onSubmit">
    <InputField id="email" label="Email" v-model="form.email" />
    <InputField id="password" label="Password" v-model="form.password" type="password" />

    <InputField
        v-if="mode === 'register'"
        id="confirmPassword"
        label="Confirm Password"
        v-model="form.confirmPassword"
        type="password"
    />

    <SubmitButton :label="mode === 'login' ? 'Login' : 'Register'" :loading="loading" />

    <Message
        v-if="error"
        severity="error"
        class="mt-3"
        :text="error"
    />
  </form>
</template>

<style scoped>

</style>