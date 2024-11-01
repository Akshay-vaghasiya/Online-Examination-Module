import axios from 'axios';

// Set the base URL for the authentication API from environment variables
const API_URL = import.meta.env.VITE_API_URL + '/api/auth';

// Define an asynchronous login function
const login = async (data) => {
  // Make a POST request to the login endpoint with the provided data and get the response
  const response = await axios.post(`${API_URL}/login-admin`, data);

  // Return the response data, which should contain token or user info
  return response.data;
};

// Export the login function to use in other parts of the application
export default { login };
