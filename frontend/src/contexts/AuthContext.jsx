import React, { createContext, useContext, useState } from 'react';

// Create a new context for authentication
const AuthContext = createContext();

// Custom hook to provide easy access to AuthContext
export const useAuth = () => useContext(AuthContext);

// AuthProvider component to wrap the app and manage auth state
export const AuthProvider = ({ children }) => {
  // State to check if user is authenticated, initially set by checking local storage
  const [isAuthenticated, setIsAuthenticated] = useState(
    !!localStorage.getItem('token')
  );

  // Login function to store token in local storage and set authentication state to true
  const login = (token, username, email) => {
    localStorage.setItem('token', token);
    localStorage.setItem('username', username);
    localStorage.setItem('email', email);
    setIsAuthenticated(true);
  };

  // Logout function to remove token from local storage and reset authentication state to false
  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    localStorage.removeItem('email');
    setIsAuthenticated(false);
  };

  // Provide auth state and functions to all children components
  return (
    <AuthContext.Provider value={{ isAuthenticated, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
