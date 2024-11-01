import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ProtectedRoute from './components/ProtectedRoute';
import DashboardLayout from './components/DashboardLayout';
import LoginPage from './pages/LoginPage';

const App = () => {
  return (
    <Router> {/* Wrapping the application in the Router for routing functionality */}
      <Routes> {/* Defining the routes of the application */}
        {/* Route for the login page */}
        <Route path="/" element={<LoginPage />} />
        
        {/* Protected route for admin dashboard and its nested routes */}
        <Route element={<ProtectedRoute />}>
          <Route path='/admin' element={<DashboardLayout />}>
            {/* Nested routes for admin dashboard */}
          </Route>
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
