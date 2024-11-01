import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';

const ProtectedRoute = () => {
    const auth = useAuth(); // Accessing authentication context
  
    // Check if the user is not authenticated
    if (!auth.isAuthenticated) {
      // Redirect to the login page if the user is not authenticated
      return <Navigate to="/" replace />;
    }
    
    // Render the child routes if the user is authenticated
    return <Outlet />;
  };
  
  export default ProtectedRoute;