import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { ToastContainer } from 'react-toastify'
import { AuthProvider } from './contexts/AuthContext.jsx'
import { ExamProvider } from './contexts/ExamContext.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode> 
    <AuthProvider>
      <ExamProvider>
        <App />    
      </ExamProvider>
    </AuthProvider>
    <ToastContainer />
  </StrictMode>,
)
