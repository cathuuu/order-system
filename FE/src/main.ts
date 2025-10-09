import { createApp } from 'vue'
import App from './App.vue'
import router from './routers'

import PrimeVue from 'primevue/config'
import ToastService from 'primevue/toastservice'
import Aura from '@primeuix/themes/aura';
import 'primeicons/primeicons.css'

const app = createApp(App)
app.use(router)
app.use(PrimeVue)
app.use(ToastService)
app.use(PrimeVue, {
    theme: {
        preset: Aura
    }
})
app.mount('#app')
