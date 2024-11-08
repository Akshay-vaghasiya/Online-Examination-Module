import axios from 'axios';

// Define the base URL for the API, using environment variables for flexibility
const API_URL = import.meta.env.VITE_API_URL + '/api/auth';

// Function to handle student login
const loginStudent = async (data) => {
  const response = await axios.post(`${API_URL}/login-student`, data);
  return response?.data;
};

// Function to handle forgot password requests
const forgotPassword = async (data) => {
  const response = await axios.post(`${API_URL}/forgot-password?email=${data.email}`);
  return response?.data;
};

// Function to handle password reset requests
const resetPassword = async (data) => {
  const response = await axios.post(`${API_URL}/reset-password`, data);
  return response?.data;
};

export default { loginStudent, forgotPassword, resetPassword };
