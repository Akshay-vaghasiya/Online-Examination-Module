import './App.css'
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import LoginPage from './pages/LoginPage';
import ForgotPassword from './pages/ForgotPassword';
import ResetPassword from './pages/ResetPassword';
import ProtectedRoute from './components/ProtectedRoute';
import Layout from './components/Layout';
import ExamPage from './pages/ExamPage';
import Exams from './pages/Exams';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/reset-password" element={<ResetPassword />} />    


        <Route element={<ProtectedRoute />}>
          <Route path='/student' element={<Layout />}>
            <Route path="exams" element={<Exams />} />
          </Route>
          <Route path="/exam/:examId" element={<ExamPage />} />
        </Route>
      </Routes>
    </Router>
  )
}

export default App
