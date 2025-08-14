import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import path from 'path'; // 引入 path 模块

export default defineConfig({
  plugins: [react()],
  css: {
    preprocessorOptions: {
      scss: {}
    }
  },
  base: '/', // 确保与部署环境一致
  publicDir: 'public', // 默认公共目录，存放无需处理的静态资源
  server: {
    port: 5173, // 确认端口是否被占用
    strictPort: true, // 若端口占用是否自动切换
    // 处理SPA前端路由
    historyApiFallback: {
      rewrites: [{ from: /\/.*/, to: '/index.html' }]
    }
  },
  resolve: {
    alias: {
      '@utils': path.resolve(__dirname, 'src/utils'),
      '@': path.resolve(__dirname, 'src')
    }
  }
});