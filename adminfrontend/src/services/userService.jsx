import axios from 'axios';

// Set the base API URL for user-related endpoints
const API_URL = import.meta.env.VITE_API_URL + '/api/user';

// Fetch all users from the server using provided headers
const getAllUsers = async (headers) => {
  const response = await axios.get(`${API_URL}/alluser`, { headers });  
  return response?.data; // Return the data from the server response
};

// Delete a user by ID, sending the request with authorization headers
const deleteuser = async (id, headers) => {
  const response = await axios.delete(`${API_URL}/delete-user/${id}`, { headers });
  return response?.data; // Return the data from the server response
};

// Update a user's information by ID, sending the data and authorization headers
const updateuser = async (id, data, headers) => {
  const response = await axios.put(`${API_URL}/update-user/${id}`, data, { headers });
  return response?.data; // Return the data from the server response
};

// Export all user-related functions to be used elsewhere in the application
export default { getAllUsers, deleteuser, updateuser };