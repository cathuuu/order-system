<script setup lang="ts">
import Button from "primevue/button"

defineProps<{ label: string; loading?: boolean }>()
</script>

<template>
  <Button
      class="w-full mt-2"
      :label="label"
      :loading="loading"
      :disabled="loading"
      type="submit"
  />
</template>

<style scoped>

</style>