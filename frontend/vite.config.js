import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: {
      // Orice cerere către /api/vehicles merge la fleet-service
      '/api/vehicles': {
        target: 'http://localhost:8081',
        changeOrigin: true,
      },
      // Orice cerere către /api/ai merge la ai-service
      '/api/ai': {
        target: 'http://localhost:8082',
        changeOrigin: true,
      }
    }
  }
})