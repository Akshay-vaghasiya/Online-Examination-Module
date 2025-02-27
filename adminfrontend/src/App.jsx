import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ProtectedRoute from './components/ProtectedRoute';
import DashboardLayout from './components/DashboardLayout';
import LoginPage from './pages/LoginPage';
import RegisterStudent from './pages/RegisterStudent';
import RegisterAdmin from './pages/RegisterAdmin';
import ForgotPassword from './pages/ForgotPassword';
import ResetPassword from './pages/ResetPassword';
import UserManagement from './pages/UserManagement';
import UniversityManagement from './pages/UniversityManagement';
import QuestionManagement from './pages/QuestionManagement';
import ExamManagement from './pages/ExamManagement';
import ExamResult from './pages/ExamResult';

const App = () => {
  return (
    <Router> {/* Wrapping the application in the Router for routing functionality */}
      <Routes> {/* Defining the routes of the application */}
        {/* Route for the login page, forgot password page, and reset password page */}
        <Route path="/" element={<LoginPage />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/reset-password" element={<ResetPassword />} />    
        
        {/* Protected route for admin dashboard and its nested routes */}
        <Route element={<ProtectedRoute />}>
          <Route path='/admin' element={<DashboardLayout />}>
            <Route path="register-student" element={<RegisterStudent />} />
            <Route path='register-admin' element={<RegisterAdmin />} />
            <Route path='user-management' element={<UserManagement />} />
            <Route path='university-management' element={<UniversityManagement />} />
            <Route path='questions' element={<QuestionManagement />} />
            <Route path='exams' element={<ExamManagement />} />
            <Route path="results/:examId" element={<ExamResult />} />
          </Route>
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
