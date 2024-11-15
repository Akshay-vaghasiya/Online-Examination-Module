import axios from 'axios';

// Set the base API URL for university-related endpoints
const API_URL = import.meta.env.VITE_API_URL + '/api/university';

// Fetch all universities from the server using provided headers
const getAllUniversities = async (headers) => {
  const response = await axios.get(`${API_URL}/getall-university`, { headers });
  return response?.data; // Return the data from the server response
};

// Register a new university by sending university data with authorization headers
const registerUniversity = async (data, headers) => {
  const response = await axios.post(`${API_URL}/register-university`, data, { headers });
  return response?.data; // Return the data from the server response
};

// Delete a university by ID, sending the request with authorization headers
const deleteUniversity = async (id, headers) => {
  const response = await axios.delete(`${API_URL}/delete-university/${id}`, {headers});
  return response.data; // Return the data from the server response
}

// Update a university's information by ID, sending the data and authorization headers
const updateUniversity = async (id, data, headers) => {
  const response = await axios.put(`${API_URL}/update-university/${id}`, data, {headers});
  return response.data; // Return the data from the server response
}

// Export both functions to be used elsewhere in the application
export default { getAllUniversities, registerUniversity, deleteUniversity, updateUniversity };
