<script setup lang="ts">
import { InputText} from "primevue";

defineProps<{
  id: string
  label: string
  type?: string
  placeholder?: string
}>()

const model = defineModel<string>()
</script>

<template>
  <div class="mb-4">
    <label :for="id" class="block text-sm font-medium mb-1">{{ label }}</label>
    <InputText
        :id="id"
        v-model="model"
        :type="type"
        class="w-full"
        :placeholder="placeholder"
    />
  </div>
</template>

<style scoped>

</style>