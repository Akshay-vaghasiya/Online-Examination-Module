// Import axios for HTTP requests and AuthHeader for setting headers
import axios from 'axios';
import AuthHeader from '../Helper/AuthHeader';

// Base URL for authentication-related endpoints
const API_URL = import.meta.env.VITE_API_URL + '/api/auth';

// Send login request for admin with provided login data
const login = async (data) => {
  const response = await axios.post(`${API_URL}/login-admin`, data);
  return response?.data; // Return the response data if available
};

// Register a new student with provided data and authorization headers
const registerStudent = async (data, headers) => {
  const response = await axios.post(`${API_URL}/register-student`, data, { headers });
  return response?.data; // Return the response data if available
};

// Register a new admin with provided data and authorization headers
const registerAdmin = async (data, headers) => {
  const response = await axios.post(`${API_URL}/register-admin`, data, { headers });
  return response?.data; // Return the response data if available
};

// Send forgot password request with provided email
const forgotPassword = async (data) => {
  const response = await axios.post(`${API_URL}/forgot-password?email=${data.email}`);
  return response?.data;
};

// Send reset password request with provided data
const resetPassword = async (data) => {
  const response = await axios.post(`${API_URL}/reset-password`, data);
  return response?.data;
};

// Export all functions for use in other parts of the application
export default { login, registerStudent, registerAdmin, forgotPassword, resetPassword };
