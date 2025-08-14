import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
// 移除原来的 index.css 引入
// import './index.css'
// 引入 global.scss
import './styles/global.scss'
import App from './App.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <App />
  </StrictMode>,
)
