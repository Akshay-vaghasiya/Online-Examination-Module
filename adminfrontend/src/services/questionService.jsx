import axios from "axios"; // Import axios for making HTTP requests

// Base URL for all question-related API endpoints, dynamically retrieved from environment variables
const API_URL = import.meta.env.VITE_API_URL + '/api/questions';

// Function to add a new MCQ question via a POST request
const addMcqQuestion = async (mcqQuestion, headers) => {
    const response = await axios.post(`${API_URL}/add-mcq`, mcqQuestion, { headers }); 
    return response?.data; // Return the response data
}

// Function to add a new coding question via a POST request
const addCodingQuestion = async (codingQuestion, headers) => {
    const response = await axios.post(`${API_URL}/add-coding`, codingQuestion, { headers });
    return response?.data; // Return the response data
}

// Function to fetch all MCQ questions via a GET request
const getMcqQuestions = async (headers) => {
    const response = await axios.get(`${API_URL}/get-mcqs`, { headers }); 
    return response?.data; // Return the list of MCQ questions
};

// Function to fetch all coding questions via a GET request
const getCodingQuestions = async (headers) => { 
    const response = await axios.get(`${API_URL}/get-codes`, { headers }); 
    return response?.data; // Return the list of coding questions
}

// Function to delete an MCQ question via a DELETE request
const deleteMcqQuestion = async (questionId, headers) => {
    const response = await axios.delete(`${API_URL}/delete-mcq/${questionId}`, { headers });
    return response?.data; // Return the response data
}

// Function to delete a coding question via a DELETE request
const deleteCodingQuestion = async (questionId, headers) => {
    const response = await axios.delete(`${API_URL}/delete-coding/${questionId}`, { headers });
    return response?.data; // Return the response data
}

// Function to update an existing coding question via a PUT request
const updateCodingQuestion = async (questionId, codingQuestion, headers) => {
    const response = await axios.put(`${API_URL}/update-coding/${questionId}`, codingQuestion, { headers }); 
    return response?.data; // Return the updated coding question
}

// Function to update an existing MCQ question via a PUT request
const updateMcqQuestion = async (questionId, mcqQuestion, headers) => {
    const response = await axios.put(`${API_URL}/update-mcq/${questionId}`, mcqQuestion, { headers }); 
    return response?.data; // Return the updated MCQ question
}

// Export all the functions as a single object for easy import and usage
export default { 
    addMcqQuestion, 
    addCodingQuestion, 
    deleteMcqQuestion, 
    deleteCodingQuestion, 
    getMcqQuestions, 
    getCodingQuestions, 
    updateCodingQuestion, 
    updateMcqQuestion 
};
